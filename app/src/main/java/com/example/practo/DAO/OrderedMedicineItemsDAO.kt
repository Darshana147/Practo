package com.example.practo.DAO

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.practo.Database.DbSqliteOpenHelper
import com.example.practo.Model.Medicine
import com.example.practo.Model.MedicineCartItem

class OrderedMedicineItemsDAO(var context: Context) {
    private val tableName:String
    private val column_order_id:String
    private val column_user_id:String
    private val column_med_id:String
    private val column_med_qty:String

    private val createTableQuery:String
    private val dropTableQuery:String

    init {
        tableName = "orderedMedicineItems"
        column_order_id = "orderId"
        column_user_id = "userId"
        column_med_id = "medId"
        column_med_qty = "medQty"

        createTableQuery = "CREATE TABLE $tableName($column_order_id INTEGER, $column_user_id INTEGER, $column_med_id INTEGER, $column_med_qty INTEGER)"

        dropTableQuery = "DROP TABLE IF EXISTS $tableName"
    }

    fun addOrderedItem(userId:Int,orderId:Int,cartItem:MedicineCartItem){
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val writableObject = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(column_user_id,userId)
        values.put(column_order_id,orderId)
        values.put(column_med_id,cartItem.medicine.medicineId)
        values.put(column_med_qty,cartItem.medicineQuantity)
        writableObject.insert(tableName,null,values)
        writableObject.close()
        dbHelper.close()
    }

    fun getAllOrderedItems(userId:Int,orderId:Int):ArrayList<MedicineCartItem>{
        val medDAO = MedicineDAO(context)
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val readableObject = dbHelper.readableDatabase
        val result:ArrayList<MedicineCartItem> = ArrayList()
        val cursor: Cursor = readableObject.rawQuery("SELECT $column_med_id, $column_med_qty FROM $tableName WHERE $column_user_id == $userId AND $column_order_id == $orderId",null)
        if(cursor.moveToFirst()){
            do{
                val medicine = medDAO.getMedicineById(cursor.getInt(cursor.getColumnIndex(column_med_id)))
                val medQty = cursor.getInt(cursor.getColumnIndex(column_med_qty))
                val cartItem = MedicineCartItem(medicine,medQty)
                result.add(cartItem)
            }while(cursor.moveToNext())
        }
        readableObject.close()
        cursor.close()
        dbHelper.close()
        return result
    }

    fun deleteOrder(orderId: Int):Int{
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val writableObject = dbHelper.writableDatabase
        val cursor =writableObject.rawQuery("DELETE FROM $tableName WHERE $column_order_id = $orderId",null)
        val count = cursor.count
        cursor.close()
        writableObject.close()
        dbHelper.close()
        return count
    }
}