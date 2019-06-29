package com.example.practo.DAO

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.practo.Database.DbSqliteOpenHelper

class BookmarkedDoctorsDAO(val context: Context){
    private val tableName:String
    private val column_user_Id:String
    private val column_doctor_id:String
    private val createTableQuery:String
    private val dropTableQuery:String

    init{
        tableName = "bookmarkedDoctorDetails"
        column_user_Id = "userId"
        column_doctor_id = "doctorId"
        createTableQuery = "CREATE TABLE $tableName($column_user_Id INTEGER, $column_doctor_id INTEGER," +
                "FOREIGN KEY($column_user_Id) REFERENCES user($column_user_Id)," +
                "FOREIGN KEY($column_doctor_id) REFERENCES doctorDetails($column_doctor_id))"

        dropTableQuery = "DROP TABLE IF EXISTS $tableName"
    }

    fun insertBookmarkedDoctorToBookmarkedList(userId:Int,doctorId:Int){
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val writableObject = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(column_user_Id,userId)
        values.put(column_doctor_id,doctorId)
        writableObject.insert(tableName,null,values)
        writableObject.close()
        dbHelper.close()
    }

    fun removeDoctorFromBookmarkedList(userId:Int,doctorId: Int){
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val writableObject = dbHelper.writableDatabase
        writableObject.delete(tableName,"$column_doctor_id == $doctorId AND $column_user_Id == $userId",null)
        writableObject.close()
        dbHelper.close()
    }

    fun getAllBookmarkedDoctors(userId:Int):ArrayList<Int>{
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val readableObject = dbHelper.readableDatabase
        val result:ArrayList<Int> = ArrayList()
        val cursor: Cursor = readableObject.rawQuery("SELECT ${column_doctor_id} FROM $tableName WHERE $column_user_Id==$userId",null)
        if(cursor.moveToFirst()){
            do{
                val doctorId = cursor.getInt(cursor.getColumnIndex(column_doctor_id))
                result.add(doctorId)
            }while(cursor.moveToNext())
        }
        cursor.close()
        dbHelper.close()
        return result
    }
}