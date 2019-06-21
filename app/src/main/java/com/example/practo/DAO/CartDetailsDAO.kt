package com.example.practo.DAO

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.practo.Database.DbSqliteOpenHelper

class CartDetailsDAO(val context: Context) {
    private val tableName:String
    private val column_cart_Id:String
    private val column_user_Id:String
    private val column_cart_quantity:String
    private val column_cart_total_price:String
    private val createTableQuery:String
    private val dropTableQuery:String

    init {
        tableName = "medicineCartDetails"
        column_cart_Id = "cartId"
        column_user_Id = "userId"
        column_cart_quantity = "cartQuantity"
        column_cart_total_price = "cartTotalPrice"

        createTableQuery = "CREATE TABLE $tableName($column_cart_Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$column_user_Id INTEGER,$column_cart_quantity INTEGER,$column_cart_total_price REAL," +
                "FOREIGN KEY($column_user_Id) REFERENCES USER($column_user_Id))"

        dropTableQuery  = "DROP TABLE IF EXISTS $tableName"



    }

    fun updateCartTotalQuantity(cartId:Int,totalQuantity:Int){
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val writableObject = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(column_cart_quantity,totalQuantity)
        writableObject.update(tableName,values,"$column_cart_Id = $cartId",null)
        writableObject.close()
        dbHelper.close()
    }

    fun updateCartTotalPrice(cartId:Int,totalPrice:Double){
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val writableObject = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(column_cart_total_price,totalPrice)
        writableObject.update(tableName,values,"$column_cart_Id = $cartId",null)
        writableObject.close()
        dbHelper.close()
    }

    fun getCartTotalQuantity(cartId: Int):Int?{
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val readableObject = dbHelper.readableDatabase
        val cursor:Cursor = readableObject.rawQuery("SELECT $column_cart_quantity FROM $tableName WHERE $column_cart_Id = $cartId",null)
        if(cursor.moveToFirst()){
            val cartTotalQuantity = cursor.getInt(cursor.getColumnIndex(column_cart_quantity))
            cursor.close()
            dbHelper.close()
            return cartTotalQuantity
        } else {
            cursor.close()
            dbHelper.close()
            return null
        }

    }

    fun getCartTotalPrice(cartId: Int):Double?{
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val readableObject = dbHelper.readableDatabase
        val cursor:Cursor = readableObject.rawQuery("SELECT $column_cart_total_price FROM $tableName WHERE $column_cart_Id = $cartId",null)
        if(cursor.moveToFirst()){
            val cartTotalPrice = cursor.getDouble(cursor.getColumnIndex(column_cart_total_price))
            cursor.close()
            dbHelper.close()
            return cartTotalPrice
        } else {
            cursor.close()
            dbHelper.close()
            return null
        }
    }


}