package com.example.practo.DAO

import android.content.Context
import android.database.Cursor
import com.example.practo.Database.DbSqliteOpenHelper
import com.example.practo.Model.Medicine

class MedicineDAO(context: Context){
    private val tableName: String
    private val column_medicine_id:String
    private val column_medicine_name:String
    private val column_medicine_description:String
    private val column_medicine_price:String
    private val column_medicine_type:String
    private val createTableQuery:String
    private val dropTableQuery:String
    private val dbHelper:DbSqliteOpenHelper


    init{
        tableName = "medicineDetails"
        column_medicine_id = "medicineId"
        column_medicine_name = "medicineName"
        column_medicine_description = "medicineDescription"
        column_medicine_price = "medicinePrice"
        column_medicine_type="medicineType"
        createTableQuery = "CREATE TABLE $tableName($column_medicine_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$column_medicine_name TEXT,$column_medicine_description TEXT,$column_medicine_price REAL," +
                "$column_medicine_type TEXT)"
        dropTableQuery = "DROP TABLE IF EXISTS $tableName"

        dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
    }


    fun getAllMedicines():ArrayList<Medicine>{
        var readableObject = dbHelper.getReadableDatabaseObject()
        var sqlSelect:Array<String> = arrayOf(column_medicine_id,column_medicine_name,column_medicine_description,column_medicine_price,column_medicine_type)
        var result:ArrayList<Medicine> = ArrayList()
        var cursor:Cursor = readableObject.query(tableName,sqlSelect,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do{
                var medId:Int = cursor.getInt(cursor.getColumnIndex(column_medicine_id))
                var medName:String= cursor.getString(cursor.getColumnIndex(column_medicine_name))
                var medDescription:String = cursor.getString(cursor.getColumnIndex(column_medicine_description))
                var medPrice:Double = cursor.getDouble(cursor.getColumnIndex(column_medicine_price))
                var medType:String = cursor.getString(cursor.getColumnIndex(column_medicine_type))
                var medicine:Medicine = Medicine(medId,medName,medDescription,medPrice,medType)

                result.add(medicine)

            }while(cursor.moveToNext())
        }
        readableObject.close()
        return result
    }


    fun getMedicineById(medicineId:Int):Medicine{
        var readableObject = dbHelper.getReadableDatabaseObject()
        var sqlSelect:Array<String> = arrayOf(column_medicine_id,column_medicine_name,column_medicine_description,column_medicine_price,column_medicine_description)
        var result:Medicine?=null
        var cursor:Cursor = readableObject.rawQuery("SELECT * FROM $tableName WHERE $column_medicine_id = $medicineId",null)
        if(cursor.moveToFirst()){
            var medId:Int = cursor.getInt(cursor.getColumnIndex(column_medicine_id))
            var medName:String= cursor.getString(cursor.getColumnIndex(column_medicine_name))
            var medDescription:String = cursor.getString(cursor.getColumnIndex(column_medicine_description))
            var medPrice:Double = cursor.getDouble(cursor.getColumnIndex(column_medicine_price))
            var medType:String = cursor.getString(cursor.getColumnIndex(column_medicine_type))
            result = Medicine(medId,medName,medDescription,medPrice,medType)
        }
        return result!!
    }



}