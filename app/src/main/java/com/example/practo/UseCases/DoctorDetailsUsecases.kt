package com.example.practo.UseCases

import android.content.Context
import com.example.practo.DAO.BookmarkedDoctorsDAO
import com.example.practo.DAO.DoctorDetailsDAO
import com.example.practo.DAO.FavoriteMedicineListDAO
import com.example.practo.Model.Doctor
import com.example.practo.Model.DoctorDetailsSupplier
import com.example.practo.Model.Medicine

class DoctorDetailsUsecases(context: Context){

    private var doctorDetailsDAO: DoctorDetailsDAO
    private var bookmarkedDoctorsDAO:BookmarkedDoctorsDAO

    init{
        doctorDetailsDAO = DoctorDetailsDAO(context)
        bookmarkedDoctorsDAO = BookmarkedDoctorsDAO(context)
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

    fun getAllBookmarkedDoctors():ArrayList<Doctor>{
        val bookmarkedDoctors = arrayListOf<Doctor>()
        for(doctorId in bookmarkedDoctorsDAO.getAllBookmarkedDoctors(1)){
            val doctor = doctorDetailsDAO.getDoctorById(doctorId)
            bookmarkedDoctors.add(doctor)
        }
        return bookmarkedDoctors
    }

    fun insertDoctorToMyDoctors(doctorId:Int){
        bookmarkedDoctorsDAO.insertBookmarkedDoctorToBookmarkedList(1,doctorId)
    }

    fun removeDoctorFromMyDoctors(doctorId: Int){
        bookmarkedDoctorsDAO.removeDoctorFromBookmarkedList(1,doctorId)
    }

    fun removeSelectedListFromDb(selectedList:ArrayList<Doctor>){
        for(doctor in selectedList){
            bookmarkedDoctorsDAO.removeDoctorFromBookmarkedList(1,doctor.doctorId)
        }
    }

//    fun getMedicinesFromFavoriteMedicineList(userId:Int):ArrayList<Medicine>{
//        val favoriteMedicines:ArrayList<Medicine> = ArrayList()
//        for(medicineId in favoriteMedicineDAO.getAllFavoriteMedicines(1)){
//            val medicine = medicineDAO.getMedicineById(medicineId)
//            favoriteMedicines.add(medicine)
//        }
//        return ArrayList(favoriteMedicines.sortedBy {
//            it.medicineName
//        } )
//    }
}