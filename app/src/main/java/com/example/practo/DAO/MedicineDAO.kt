package com.example.practo.DAO

import android.content.Context
import android.database.Cursor
import com.example.practo.Database.DbSqliteOpenHelper
import com.example.practo.Model.Dosage
import com.example.practo.Model.Medicine
import com.example.practo.Model.MedicineDescription

class MedicineDAO(context: Context){
    private val tableName: String
    private val column_medicine_id:String
    private val column_medicine_name:String
    private val column_medicine_description:String
    private val column_medicine_price:String
    private val column_medicine_type:String
    private val column_medicine_contains:String
    private  val column_medicine_manufacturer:String
    private val column_medicine_detailed_description:String
    private val column_medicine_side_effects:String
    private val column_medicine_prescribed_for:String
    private val column_medicine_missed_dose_details:String
    private val column_medicine_over_dose_details:String
    private val column_medicine_general_instructions:String
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
        column_medicine_contains = "medicineContains"
        column_medicine_manufacturer = "medicineManufacturer"
        column_medicine_detailed_description = "medicineDetailedDescription"
        column_medicine_side_effects = "medicineSideEffects"
        column_medicine_prescribed_for = "medicinePrescribedFor"
        column_medicine_missed_dose_details = "medicineMissedDoseDetails"
        column_medicine_over_dose_details = "medicineOverDoseDetails"
        column_medicine_general_instructions = "medicineGeneralInstructions"
        createTableQuery = "CREATE TABLE $tableName($column_medicine_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$column_medicine_name TEXT,$column_medicine_description TEXT,$column_medicine_price REAL," +
                "$column_medicine_type TEXT, $column_medicine_contains TEXT, $column_medicine_manufacturer TEXT, $column_medicine_detailed_description TEXT, " +
                "$column_medicine_side_effects TEXT, $column_medicine_prescribed_for TEXT, $column_medicine_missed_dose_details TEXT, " +
                "$column_medicine_over_dose_details TEXT, $column_medicine_general_instructions TEXT)"
        dropTableQuery = "DROP TABLE IF EXISTS $tableName"

        dbHelper = DbSqliteOpenHelper(context,createTableQuery,dropTableQuery)
    }


    fun getAllMedicines():ArrayList<Medicine>{
        val readableObject = dbHelper.getReadableDatabaseObject()
        val sqlSelect:Array<String> = arrayOf(column_medicine_id,column_medicine_name,column_medicine_description,column_medicine_price,column_medicine_type,column_medicine_contains,column_medicine_manufacturer,column_medicine_detailed_description,column_medicine_side_effects,column_medicine_prescribed_for,column_medicine_missed_dose_details,column_medicine_over_dose_details,column_medicine_general_instructions)
        val result:ArrayList<Medicine> = ArrayList()
        val cursor:Cursor = readableObject.query(tableName,sqlSelect,null,null,null,null,column_medicine_name)
        if(cursor.moveToFirst()){
            do{
                val medId:Int = cursor.getInt(cursor.getColumnIndex(column_medicine_id))
                val medName:String= cursor.getString(cursor.getColumnIndex(column_medicine_name))
                val medDescription:String = cursor.getString(cursor.getColumnIndex(column_medicine_description))
                val medPrice:Double = cursor.getDouble(cursor.getColumnIndex(column_medicine_price))
                val medType:String = cursor.getString(cursor.getColumnIndex(column_medicine_type))
                val medContains:String = cursor.getString(cursor.getColumnIndex(column_medicine_contains))
                val medManufacturer:String = cursor.getString(cursor.getColumnIndex(column_medicine_manufacturer))
                val medDetailedDescription:String = cursor.getString(cursor.getColumnIndex(column_medicine_detailed_description))
                val medSideEffects:String = cursor.getString(cursor.getColumnIndex(column_medicine_side_effects))
                val medPrescribedFor:String = cursor.getString(cursor.getColumnIndex(column_medicine_prescribed_for))
                val medMissedDoseDetails:String? = cursor.getString(cursor.getColumnIndex(column_medicine_missed_dose_details))
                val medOverDoseDetails:String? = cursor.getString(cursor.getColumnIndex(column_medicine_over_dose_details))
                val medGeneralInstructions:String = cursor.getString(cursor.getColumnIndex(column_medicine_general_instructions))

                var dosage:Dosage? = null

                if(medMissedDoseDetails!=null&&medOverDoseDetails!= null){
                    dosage = Dosage(medMissedDoseDetails,medOverDoseDetails)
                }

                val medicine:Medicine = Medicine(medId,medName,medDescription,medPrice,medType, MedicineDescription(medManufacturer,medContains,medDetailedDescription,medSideEffects,medPrescribedFor,
                    dosage,medGeneralInstructions))

                result.add(medicine)

            }while(cursor.moveToNext())
        }
        cursor.close()
        readableObject.close()
        return result
    }


    fun getMedicineById(medicineId:Int):Medicine{
        val readableObject = dbHelper.getReadableDatabaseObject()
        var sqlSelect:Array<String> = arrayOf(column_medicine_id,column_medicine_name,column_medicine_description,column_medicine_price,column_medicine_description)
        var result:Medicine?=null
        val cursor:Cursor = readableObject.rawQuery("SELECT * FROM $tableName WHERE $column_medicine_id = $medicineId",null)
        if(cursor.moveToFirst()){
            val medId:Int = cursor.getInt(cursor.getColumnIndex(column_medicine_id))
            val medName:String= cursor.getString(cursor.getColumnIndex(column_medicine_name))
            val medDescription:String = cursor.getString(cursor.getColumnIndex(column_medicine_description))
            val medPrice:Double = cursor.getDouble(cursor.getColumnIndex(column_medicine_price))
            val medType:String = cursor.getString(cursor.getColumnIndex(column_medicine_type))
            val medContains:String = cursor.getString(cursor.getColumnIndex(column_medicine_contains))
            val medManufacturer:String = cursor.getString(cursor.getColumnIndex(column_medicine_manufacturer))
            val medDetailedDescription = cursor.getString(cursor.getColumnIndex(column_medicine_detailed_description))
            val medSideEffects:String = cursor.getString(cursor.getColumnIndex(column_medicine_side_effects))
            val medPrescribedFor:String = cursor.getString(cursor.getColumnIndex(column_medicine_prescribed_for))
            val medMissedDoseDetails:String? = cursor.getString(cursor.getColumnIndex(column_medicine_missed_dose_details))
            val medOverDoseDetails:String? = cursor.getString(cursor.getColumnIndex(column_medicine_over_dose_details))
            val medGeneralInstructions:String = cursor.getString(cursor.getColumnIndex(column_medicine_general_instructions))
            var dosage:Dosage? = null

            if(medMissedDoseDetails!=null&&medOverDoseDetails!= null){
                dosage = Dosage(medMissedDoseDetails,medOverDoseDetails)
            }

            result = Medicine(medId,medName,medDescription,medPrice,medType,MedicineDescription(medManufacturer,medContains,medDetailedDescription,medSideEffects,medPrescribedFor,
                dosage,medGeneralInstructions))
        }
        cursor.close()
        return result!!
    }



}