package com.example.practo.DAO

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.practo.Database.DbSqliteOpenHelper
import com.example.practo.Model.UserMedicineDeliveryAddressDetails

class UserDeliveryAddressDAO(val context: Context) {
    private val tableName:String
    private val column_user_address_id:String
    private val column_user_id:String
    private val column_user_name:String
    private val column_user_mobile_number:String
    private val column_user_pin_code:String
    private val column_address:String
    private val column_locality:String
    private val column_city:String
    private val column_state:String
    private val column_country:String
    private val column_type_of_address:String

    private val createTableQuery:String
    private val dropTableQuery:String

    init {
        tableName = "userDeliveryAddress"
        column_user_address_id = "useraddressId"
        column_user_id = "userId"
        column_user_name = "userName"
        column_user_mobile_number = "userMobileNumber"
        column_user_pin_code = "userPinCode"
        column_address = "userAddress"
        column_locality = "userLocality"
        column_city = "userCity"
        column_state = "userState"
        column_country = "userCountry"
        column_type_of_address = "userTypeOfAddress"

        createTableQuery = "CREATE TABLE $tableName($column_user_id INTEGER, $column_user_name TEXT," +
                "$column_user_mobile_number TEXT, $column_user_pin_code TEXT, $column_address TEXT, " +
                "$column_locality TEXT, $column_city TEXT, $column_state TEXT, $column_country TEXT," +
                "$column_type_of_address TEXT, FOREIGN KEY($column_user_id) REFERENCES user($column_user_id))"

        dropTableQuery = "DROP TABLE IF EXISTS $tableName"
    }


    fun getAllAddresses(userId:Int):ArrayList<UserMedicineDeliveryAddressDetails>{
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val readableObject = dbHelper.readableDatabase
        val sqlSelect:Array<String> = arrayOf(column_user_address_id,column_user_name,column_user_mobile_number,column_user_pin_code,column_address,column_locality,column_city,column_state,column_country,column_type_of_address)
        val result:ArrayList<UserMedicineDeliveryAddressDetails> = ArrayList()
        val cursor: Cursor = readableObject.query(tableName,sqlSelect,"$column_user_id = $userId",null,null,null,null)
        if(cursor.moveToFirst()){
            do{
                val userAddressId = cursor.getInt(cursor.getColumnIndex(column_user_address_id))
                val userName = cursor.getString(cursor.getColumnIndex(column_user_name))
                val userMobileNumber = cursor.getString(cursor.getColumnIndex(column_user_mobile_number))
                val userPinCode = cursor.getString(cursor.getColumnIndex(column_user_pin_code))
                val userAddress = cursor.getString(cursor.getColumnIndex(column_address))
                val userLocality = cursor.getString(cursor.getColumnIndex(column_locality))
                val userCity = cursor.getString(cursor.getColumnIndex(column_city))
                val userState = cursor.getString(cursor.getColumnIndex(column_state))
                val userCountry = cursor.getString(cursor.getColumnIndex(column_country))
                val userTypeOfAddress = cursor.getString(cursor.getColumnIndex(column_type_of_address))
                result.add(UserMedicineDeliveryAddressDetails(userAddressId,userName,userMobileNumber,userPinCode,userAddress,userLocality,userCity,userState,userCountry,userTypeOfAddress))
            }while(cursor.moveToNext())
        }
        readableObject.close()
        cursor.close()
        dbHelper.close()
        return result
    }

    fun getAddressByAddressId(userAddressId:Int):UserMedicineDeliveryAddressDetails?{
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val readableObject = dbHelper.readableDatabase
        val sqlSelect:Array<String> = arrayOf(column_user_name,column_user_mobile_number,column_user_pin_code,column_address,column_locality,column_city,column_state,column_country,column_type_of_address)
        var result:UserMedicineDeliveryAddressDetails? = null
        val cursor: Cursor = readableObject.query(tableName,sqlSelect,"$column_user_address_id = $userAddressId",null,null,null,null)
        if(cursor.moveToFirst()){
            val userName = cursor.getString(cursor.getColumnIndex(column_user_name))
            val userMobileNumber = cursor.getString(cursor.getColumnIndex(column_user_mobile_number))
            val userPinCode = cursor.getString(cursor.getColumnIndex(column_user_pin_code))
            val userAddress = cursor.getString(cursor.getColumnIndex(column_address))
            val userLocality = cursor.getString(cursor.getColumnIndex(column_locality))
            val userCity = cursor.getString(cursor.getColumnIndex(column_city))
            val userState = cursor.getString(cursor.getColumnIndex(column_state))
            val userCountry = cursor.getString(cursor.getColumnIndex(column_country))
            val userTypeOfAddress = cursor.getString(cursor.getColumnIndex(column_type_of_address))

            result = UserMedicineDeliveryAddressDetails(userName,userMobileNumber,userPinCode,userAddress,userLocality,userCity,userState,userCountry,userTypeOfAddress)
        }
        readableObject.close()
        cursor.close()
        dbHelper.close()
        return result
    }

    fun insertUserDeliveryAddress(userId: Int,userMedicineDeliveryAddressDetails: UserMedicineDeliveryAddressDetails){
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val writableObject = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(column_user_id,userId)
        values.put(column_user_name,userMedicineDeliveryAddressDetails.userName)
        values.put(column_user_mobile_number,userMedicineDeliveryAddressDetails.userMobileNumber)
        values.put(column_user_pin_code,userMedicineDeliveryAddressDetails.userPinCode)
        values.put(column_address,userMedicineDeliveryAddressDetails.userAddress)
        values.put(column_locality,userMedicineDeliveryAddressDetails.userLocality)
        values.put(column_city,userMedicineDeliveryAddressDetails.userCity)
        values.put(column_state,userMedicineDeliveryAddressDetails.userState)
        values.put(column_country,userMedicineDeliveryAddressDetails.userCountry)
        values.put(column_type_of_address,userMedicineDeliveryAddressDetails.typeOfAddress)

        writableObject.insert(tableName,null,values)
        writableObject.close()
        dbHelper.close()
    }


    fun updateUserDeliveryAddressDetails(userId:Int,userMedicineDeliveryAddressDetails: UserMedicineDeliveryAddressDetails){
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val writableObject = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(column_user_name,userMedicineDeliveryAddressDetails.userName)
        values.put(column_user_mobile_number,userMedicineDeliveryAddressDetails.userMobileNumber)
        values.put(column_user_pin_code,userMedicineDeliveryAddressDetails.userPinCode)
        values.put(column_address,userMedicineDeliveryAddressDetails.userAddress)
        values.put(column_locality,userMedicineDeliveryAddressDetails.userLocality)
        values.put(column_city,userMedicineDeliveryAddressDetails.userCity)
        values.put(column_state,userMedicineDeliveryAddressDetails.userState)
        values.put(column_country,userMedicineDeliveryAddressDetails.userCountry)
        values.put(column_type_of_address,userMedicineDeliveryAddressDetails.typeOfAddress)
        writableObject.update(tableName,values,"$column_user_id = $userId",null)
        writableObject.close()
        dbHelper.close()
    }

    fun deleteDeliveryAddressRecord(userId:Int,addressId:Int){
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val writableObject = dbHelper.writableDatabase
        writableObject.delete(tableName,"$column_user_id == $userId AND $column_user_address_id == $addressId",null)
        writableObject.close()
        dbHelper.close()
    }

}