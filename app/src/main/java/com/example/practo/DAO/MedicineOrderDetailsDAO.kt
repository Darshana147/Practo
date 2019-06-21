package com.example.practo.DAO

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.practo.Database.DbSqliteOpenHelper
import com.example.practo.Model.MedicineCart
import com.example.practo.Model.MedicineCartItem
import com.example.practo.Model.MedicineOrder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class MedicineOrderDetailsDAO(val context:Context){
    private val orderedMedicineItemsDAO:OrderedMedicineItemsDAO
    private val userDeliveryAddressDAO:UserDeliveryAddressDAO
    private val tableName:String
    private val column_order_id:String
    private val column_user_id:String
    private val column_user_address_id:String
    private val column_delivery_date:String
    private val column_ordered_date:String
    private val column_total_price:String
    private val column_total_quantity:String
    private val column_is_delivered:String

    private val createTableQuery:String
    private val dropTableQuery:String

    init{
        orderedMedicineItemsDAO = OrderedMedicineItemsDAO(context)
        userDeliveryAddressDAO = UserDeliveryAddressDAO(context)
        tableName = "medicineOrderDetails"
        column_order_id = "orderId"
        column_user_id = "userId"
        column_user_address_id = "useraddressId"
        column_delivery_date = "deliveryDate"
        column_ordered_date = "orderedDate"
        column_total_price = "totalPrice"
        column_total_quantity = "totalQuantity"
        column_is_delivered = "isDelivered"

        createTableQuery = "CREATE TABLE $tableName( $column_order_id INTEGER PRIMARY KEY AUTOINCREMENT, $column_user_id INTEGER, " +
                "$column_user_address_id INTEGER, $column_delivery_date TEXT, $column_ordered_date TEXT, " +
                "$column_total_price REAL, $column_total_quantity INTEGER, $column_is_delivered TEXT, " +
                "FOREIGN KEY($column_user_id) REFERENCES user($column_user_address_id), " +
                "FOREIGN KEY($column_user_address_id) REFERENCES userDeliveryAddress($column_user_address_id))"

        dropTableQuery = "DROP TABLE IF EXISTS $tableName"

    }

    fun addNewOrder(userId:Int,medicineOrder:MedicineOrder):Int{
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val writableObject = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(column_user_id,userId)
        values.put(column_user_address_id,medicineOrder.deliveryAddressDetails.userAddressId)
        values.put(column_delivery_date,medicineOrder.deliveryDate.toString())
        values.put(column_ordered_date,medicineOrder.orderedDate.toString())
        values.put(column_total_price,medicineOrder.medicineCart.totalPrice)
        values.put(column_total_quantity,medicineOrder.medicineCart.totalNumOfItems)
        values.put(column_is_delivered,medicineOrder.isDelivered)
        writableObject.insert(tableName,null,values)
        writableObject.close()
        dbHelper.close()
        val lastId = getLastId()
        for(cartItem in medicineOrder.medicineCart.medicineCartItems){
            orderedMedicineItemsDAO.addOrderedItem(userId,lastId,cartItem)
        }
        return lastId
    }

    fun getLastId():Int{
        var lastId = 0
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val readableObject = dbHelper.readableDatabase
        val cursor = readableObject.rawQuery("SELECT $column_order_id FROM $tableName",null)
        if(cursor.moveToFirst()){
           cursor.moveToLast()
            lastId = cursor.getInt(cursor.getColumnIndex(column_order_id))
        }
        cursor.close()
        dbHelper.close()
        return lastId
    }

    fun getAllUserOrders(userId:Int): ArrayList<MedicineOrder>{
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val readableObject = dbHelper.readableDatabase
        val sqlSelect:Array<String> = arrayOf(column_order_id,column_user_address_id,column_delivery_date,column_ordered_date,column_total_price,column_total_quantity,column_is_delivered)
        val result:ArrayList<MedicineOrder> = arrayListOf()
        val cursor = readableObject.query(tableName,sqlSelect,"$column_user_id = $userId",null,null,null,null)
        if(cursor.moveToFirst()){
            do {
                val orderId = cursor.getInt(cursor.getColumnIndex(column_order_id))
                val userAddressId = cursor.getInt(cursor.getColumnIndex(column_user_address_id))
                val userDeliveryDate = cursor.getString(cursor.getColumnIndex(column_delivery_date))  //need to cast to date
                val orderedDate = cursor.getString(cursor.getColumnIndex(column_ordered_date))
                val totalPrice = cursor.getDouble(cursor.getColumnIndex(column_total_price))
                val totalQuantity = cursor.getInt(cursor.getColumnIndex(column_total_quantity))
                val isDelivered = cursor.getString(cursor.getColumnIndex(column_is_delivered))
                var delivered:Boolean = false
                if(isDelivered.toLowerCase().equals("yes")){
                    delivered = true
                }

                val medCartItems = getOrderedItemsByOrderid(userId,orderId)
                val userAddress = userDeliveryAddressDAO.getAddressByAddressId(userAddressId)
                val medCart = MedicineCart(medCartItems,totalQuantity,totalPrice)
                val order = MedicineOrder(orderId,medCart,userAddress!!,userDeliveryDate,orderedDate,delivered)

                result.add(order)


            } while (cursor.moveToNext())
        }
        readableObject.close()
        cursor.close()
        dbHelper.close()
        return result
    }


    fun getOrderByOrderId(userId: Int,orderId:Int):MedicineOrder?{
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val readableObject = dbHelper.readableDatabase
        val sqlSelect:Array<String> = arrayOf(column_user_address_id,column_delivery_date,column_ordered_date,column_total_price,column_total_quantity,column_is_delivered)
        var result:MedicineOrder? = null
        val cursor = readableObject.query(tableName,sqlSelect,"$column_user_id = $userId AND $column_order_id = $orderId",null,null,null,null)
        if(cursor.moveToFirst()){
            val userAddressId = cursor.getInt(cursor.getColumnIndex(column_user_address_id))
            val userDeliveryDate = cursor.getString(cursor.getColumnIndex(column_delivery_date))  //need to cast to date
            val orderedDate = cursor.getString(cursor.getColumnIndex(column_ordered_date))
            val totalPrice = cursor.getDouble(cursor.getColumnIndex(column_total_price))
            val totalQuantity = cursor.getInt(cursor.getColumnIndex(column_total_quantity))
            val isDelivered = cursor.getString(cursor.getColumnIndex(column_is_delivered))
            var delivered:Boolean = false
            if(isDelivered.toLowerCase().equals("yes")){
                delivered = true
            }

            val medCartItems = getOrderedItemsByOrderid(userId,orderId)
            val userAddress = userDeliveryAddressDAO.getAddressByAddressId(userAddressId)
            val medCart = MedicineCart(medCartItems,totalQuantity,totalPrice)
            result = MedicineOrder(medCart,userAddress!!,userDeliveryDate,orderedDate,delivered)

        }
        readableObject.close()
        cursor.close()
        dbHelper.close()
        return result
    }


    fun getOrderedItemsByOrderid(userId:Int,orderId:Int):ArrayList<MedicineCartItem>{
        return orderedMedicineItemsDAO.getAllOrderedItems(userId,orderId)
    }

    fun removeOrder(orderId:Int){ //cancel order
        val count = orderedMedicineItemsDAO.deleteOrder(orderId)
        Log.d("abcd","$count == $orderId")
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val writableObject = dbHelper.writableDatabase
        writableObject.delete(tableName,"$column_order_id == $orderId",null)
        writableObject.close()
        dbHelper.close()
    }


    fun updateOrder(orderId:Int,isDelivered:String){ //isDeliveredOrNot
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val writableObject = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(column_is_delivered,isDelivered)
        writableObject.update(tableName,values,"$column_order_id = $orderId",null)
        writableObject.close()
        dbHelper.close()
    }


}