package com.example.practo.UseCases

import android.content.Context
import com.example.practo.Model.Doctor
import com.example.practo.Model.DoctorDetailsSupplier

class DoctorDetailsUsecases(context: Context){

    fun getDoctorDetailsBySpecialization(specialization:String):ArrayList<Doctor>{
        val doctorDetails = arrayListOf<Doctor>()
        for(detail in DoctorDetailsSupplier.doctorDetailsList){
            if(detail.specialization.toLowerCase().trim().equals(specialization.toLowerCase().trim())){
                doctorDetails.add(detail)
            }
        }

        return doctorDetails
    }
}