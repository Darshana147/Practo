package com.example.practo.DAO

import android.content.Context
import android.database.Cursor
import com.example.practo.Database.DbSqliteOpenHelper
import com.example.practo.Model.Doctor
import com.example.practo.Model.Hospital
import com.example.practo.Model.HospitalAddress
import com.example.practo.Model.Medicine

class DoctorDetailsDAO(val context: Context) {
    private val tableName:String
    private val column_doctor_id:String
    private val column_doctor_name:String
    private val column_gender:String
    private val column_qualification:String
    private val column_specialization:String
    private val column_experience:String
    private val column_consultationFees:String
    private val column_hospitalName:String
    private val column_hospitalAddress:String
    private val column_hospitalCity:String
    private val column_hospitalState:String
    private val column_hospitalCountry:String
    private val column_hospitalPincode:String
    private val column_hospitalContactNumber:String
    private val column_doctorServices:String
    private val column_doctorPastExperiences:String

    private val createTableQuery:String
    private val dropTableQuery:String

    init{
        tableName = "doctorDetails"
        column_doctor_id = "doctorId"
        column_doctor_name = "doctorName"
        column_gender = "gender"
        column_qualification = "qualification"
        column_specialization = "specialization"
        column_experience = "experience"
        column_consultationFees = "consultationFees"
        column_hospitalName = "hospitalName"
        column_hospitalAddress = "hospitalAddress"
        column_hospitalCity = "hospitalCity"
        column_hospitalState = "hospitalState"
        column_hospitalCountry = "hospitalCountry"
        column_hospitalPincode = "pincode"
        column_hospitalContactNumber = "hospitalContactNumber"
        column_doctorServices = "doctorServices"
        column_doctorPastExperiences = "doctorPastExperiences"

        createTableQuery = "CREATE TABLE $tableName($column_doctor_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, $column_doctor_name TEXT, $column_gender TEXT, " +
                "$column_qualification TEXT, $column_specialization TEXT, $column_experience TEXT, $column_consultationFees REAL, $column_hospitalName TEXT, " +
                "$column_hospitalAddress TEXT, $column_hospitalCity TEXT, $column_hospitalState TEXT, $column_hospitalCountry TEXT, $column_hospitalPincode TEXT, " +
                "$column_hospitalContactNumber TEXT, $column_doctorServices TEXT, $column_doctorPastExperiences TEXT)"

        dropTableQuery = "DROP TABLE IF EXISTS $tableName"
    }

    fun getAllDoctorDetails():ArrayList<Doctor>{
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val readableObject = dbHelper.readableDatabase
        val sqlSelect:Array<String> = arrayOf(column_doctor_id,column_doctor_name,column_gender,column_qualification,column_specialization,column_experience,column_consultationFees,
            column_hospitalName,column_hospitalAddress,column_hospitalCity,column_hospitalState,column_hospitalCountry,column_hospitalPincode,column_hospitalContactNumber,column_doctorServices,column_doctorPastExperiences)
        val result:ArrayList<Doctor> = ArrayList()
        val cursor: Cursor = readableObject.query(tableName,sqlSelect,null,null,null,null,column_doctor_name)
        if(cursor.moveToFirst()){
            do{
                val doctorId = cursor.getInt(cursor.getColumnIndex(column_doctor_id))
                val doctorName = cursor.getString(cursor.getColumnIndex(column_doctor_name))
                val gender = cursor.getString(cursor.getColumnIndex(column_gender))
                val qualification = cursor.getString(cursor.getColumnIndex(column_qualification))
                val specialization = cursor.getString(cursor.getColumnIndex(column_specialization))
                val experience = cursor.getString(cursor.getColumnIndex(column_experience))
                val consultationFees = cursor.getDouble(cursor.getColumnIndex(column_consultationFees))
                val hospitalName = cursor.getString(cursor.getColumnIndex(column_hospitalName))
                val hospitalAddress = cursor.getString(cursor.getColumnIndex(column_hospitalAddress))
                val hospitalCity = cursor.getString(cursor.getColumnIndex(column_hospitalCity))
                val hospitalState = cursor.getString(cursor.getColumnIndex(column_hospitalState))
                val hospitalCountry = cursor.getString(cursor.getColumnIndex(column_hospitalCountry))
                val hospitalPincode = cursor.getString(cursor.getColumnIndex(column_hospitalPincode))
                val hospitalContactNumber = cursor.getString(cursor.getColumnIndex(column_hospitalContactNumber))
                val doctorServices = cursor.getString(cursor.getColumnIndex(column_doctorServices))
                val doctorPastExperiences = cursor.getString(cursor.getColumnIndex(column_doctorPastExperiences))

                result.add(Doctor(doctorId,doctorName,gender,qualification,specialization,experience,consultationFees,
                    Hospital(hospitalName, HospitalAddress(hospitalAddress,hospitalCity,hospitalState,hospitalCountry,hospitalPincode),hospitalContactNumber),
                    doctorServices,doctorPastExperiences
                ))

            }while(cursor.moveToNext())
        }
        cursor.close()
        readableObject.close()
        dbHelper.close()
        return result
    }

    fun getDoctorById(doctorId:Int):Doctor{
        val dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
        val readableObject = dbHelper.readableDatabase
        var result:Doctor?=null
        val cursor:Cursor = readableObject.rawQuery("SELECT * FROM $tableName WHERE $column_doctor_id = $doctorId",null)

        if(cursor.moveToFirst()){
                val docId = cursor.getInt(cursor.getColumnIndex(column_doctor_id))
                val doctorName = cursor.getString(cursor.getColumnIndex(column_doctor_name))
                val gender = cursor.getString(cursor.getColumnIndex(column_gender))
                val qualification = cursor.getString(cursor.getColumnIndex(column_qualification))
                val specialization = cursor.getString(cursor.getColumnIndex(column_specialization))
                val experience = cursor.getString(cursor.getColumnIndex(column_experience))
                val consultationFees = cursor.getDouble(cursor.getColumnIndex(column_consultationFees))
                val hospitalName = cursor.getString(cursor.getColumnIndex(column_hospitalName))
                val hospitalAddress = cursor.getString(cursor.getColumnIndex(column_hospitalAddress))
                val hospitalCity = cursor.getString(cursor.getColumnIndex(column_hospitalCity))
                val hospitalState = cursor.getString(cursor.getColumnIndex(column_hospitalState))
                val hospitalCountry = cursor.getString(cursor.getColumnIndex(column_hospitalCountry))
                val hospitalPincode = cursor.getString(cursor.getColumnIndex(column_hospitalPincode))
                val hospitalContactNumber = cursor.getString(cursor.getColumnIndex(column_hospitalContactNumber))
                val doctorServices = cursor.getString(cursor.getColumnIndex(column_doctorServices))
                val doctorPastExperiences = cursor.getString(cursor.getColumnIndex(column_doctorPastExperiences))

                result = Doctor(doctorId,doctorName,gender,qualification,specialization,experience,consultationFees,
                    Hospital(hospitalName, HospitalAddress(hospitalAddress,hospitalCity,hospitalState,hospitalCountry,hospitalPincode),hospitalContactNumber),
                    doctorServices,doctorPastExperiences
                )
        }
        cursor.close()
        readableObject.close()
        dbHelper.close()
        return result!!
    }
}