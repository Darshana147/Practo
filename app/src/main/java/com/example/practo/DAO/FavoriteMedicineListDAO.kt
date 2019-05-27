package com.example.practo.DAO

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.practo.Database.DbSqliteOpenHelper

class FavoriteMedicineListDAO(context: Context){
    private val tableName:String
    private val column_user_Id:String
    private val column_medicine_Id:String
    private val createTableQuery:String
    private val dropTableQuery:String
    private val dbHelper: DbSqliteOpenHelper

    init{
        tableName = "favoriteMedicineList"
        column_user_Id = "userId"
        column_medicine_Id = "medicineId"

        createTableQuery = "CREATE TABLE $tableName($column_user_Id INTEGER," +
                "$column_medicine_Id INTEGER,FOREIGN KEY($column_user_Id) REFERENCES user($column_user_Id)," +
                "FOREIGN KEY($column_medicine_Id) REFERENCES medicineDetails($column_medicine_Id))"

        dropTableQuery = "DROP TABLE IF EXISTS $tableName"

        dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
    }

    fun insertMedicineItemIntoFavoriteList(userId:Int,medicineId:Int){
        var writableObject = dbHelper.writableDatabase
        var values = ContentValues()
        values.put(column_user_Id,userId)
        values.put(column_medicine_Id,medicineId)
        writableObject.insert(tableName,null,values)
        writableObject.close()
    }

    fun removeFavoriteMedicineFromFavoritesList(userId:Int,medicineId: Int){
        var writableObject = dbHelper.writableDatabase
        writableObject.delete(tableName,"$column_medicine_Id == $medicineId AND $column_user_Id == $userId",null)
        writableObject.close()
    }

    fun getAllFavoriteMedicines(userId:Int):ArrayList<Int>{
        var readableObject = dbHelper.readableDatabase
        var result:ArrayList<Int> = ArrayList()
        var cursor: Cursor = readableObject.rawQuery("SELECT $column_medicine_Id FROM $tableName WHERE $column_user_Id==$userId",null)
        if(cursor.moveToFirst()){
            do{
                var medicineId = cursor.getInt(cursor.getColumnIndex(column_medicine_Id))
                result.add(medicineId)
            }while(cursor.moveToNext())
        }
        return result
    }
}