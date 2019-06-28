package com.example.practo.UseCases

import android.content.Context
import com.example.practo.DAO.DoctorDetailsDAO
import com.example.practo.DAO.FavoriteMedicineListDAO
import com.example.practo.Model.Doctor
import com.example.practo.Model.DoctorDetailsSupplier

class DoctorDetailsUsecases(context: Context){

    private var doctorDetailsDAO: DoctorDetailsDAO

    init{
        doctorDetailsDAO = DoctorDetailsDAO(context)
    }

    fun getAllDoctorDetails(){
        val doctorDetails = doctorDetailsDAO.getAllDoctorDetails()
        DoctorDetailsSupplier.doctorDetailsList.addAll(doctorDetails)
    }

    fun getDoctorDetailsBySpecialization(specialization:String):ArrayList<Doctor>{
        val doctorDetails = arrayListOf<Doctor>()
        for(detail in DoctorDetailsSupplier.doctorDetailsList){
            if(detail.specialization.toLowerCase().trim().equals(specialization.toLowerCase().trim())){
                doctorDetails.add(detail)
            }
        }

        return doctorDetails
    }

    fun getDoctorById(doctorId:Int):Doctor?{
        var doctorDetails:Doctor?=null
        for(doctor in DoctorDetailsSupplier.doctorDetailsList){
            if(doctorId == doctor.doctorId){
                doctorDetails = doctor
                break
            }
        }
        return doctorDetails
    }
}