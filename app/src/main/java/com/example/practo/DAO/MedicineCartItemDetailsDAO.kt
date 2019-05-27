package com.example.practo.DAO

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.practo.Database.DbSqliteOpenHelper
import com.example.practo.Model.Medicine
import com.example.practo.Model.MedicineCartItem

class MedicineCartItemDetailsDAO(context: Context) {
    private val tableName:String
    private val column_cart_id:String
    private val column_medicine_id:String
    private val column_medicine_quantity:String

    private val createTableQuery:String
    private val dropTableQuery:String
    private val dbHelper:DbSqliteOpenHelper

    init {
        tableName = "medicineCartItemDetails"
        column_cart_id = "cartId"
        column_medicine_id = "medicineId"
        column_medicine_quantity = "medicineQuantity"

        createTableQuery = "CREATE TABLE $tableName($column_cart_id INTEGER," +
                "$column_medicine_id INTEGER,$column_medicine_quantity INTEGER)"

        dropTableQuery = "DROP TABLE IF EXISTS $tableName"

        dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
    }

    fun insertMedicineItemIntoCart(medicineCartItem:MedicineCartItem){
        val writableObject = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(column_cart_id,1)
        values.put(column_medicine_id,medicineCartItem.medicine.medicineId)
        values.put(column_medicine_quantity,medicineCartItem.medicineQuantity)

        writableObject.insert(tableName,null,values)
        writableObject.close()
    }

    fun getAllCartItems():ArrayList<MedicineCartItem>{
        val readableObject=dbHelper.readableDatabase
        var sqlSelect:Array<String> = arrayOf(column_cart_id,column_medicine_id,column_medicine_quantity)
        var result:ArrayList<MedicineCartItem> = ArrayList()
        var cursor: Cursor = readableObject.query(tableName,sqlSelect,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do{
                var cartId = cursor.getInt(cursor.getColumnIndex(column_cart_id))
                var medId:Int = cursor.getInt(cursor.getColumnIndex(column_medicine_id))
                var medQuantity:Int = cursor.getInt(cursor.getColumnIndex(column_medicine_quantity))

                result.add(MedicineCartItem(Medicine(medId),medQuantity))

            } while(cursor.moveToNext())
        }
        readableObject.close()
        return result
    }

    fun changeMedicineQuantityByMedicineId(medicineId:Int,medicineQuantity:Int){
        var writableObject = dbHelper.writableDatabase
        var values = ContentValues()
        values.put(column_medicine_quantity,medicineQuantity)
        writableObject.update(tableName,values,"$column_medicine_id = $medicineId",null)
        writableObject.close()
    }



     fun removeMedicineItemFromCart(medicineId:Int){
         var writableObject = dbHelper.writableDatabase
         writableObject.delete(tableName,"$column_medicine_id = $medicineId",null)
         writableObject.close()
     }
}