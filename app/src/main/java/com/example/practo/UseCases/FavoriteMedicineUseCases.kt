package com.example.practo.UseCases

import android.content.Context
import com.example.practo.DAO.FavoriteMedicineListDAO
import com.example.practo.DAO.MedicineDAO
import com.example.practo.Model.Medicine

class FavoriteMedicineUseCases(context: Context){
    private var favoriteMedicineDAO: FavoriteMedicineListDAO
    private var medicineDAO: MedicineDAO

    init{
        favoriteMedicineDAO = FavoriteMedicineListDAO(context)
        medicineDAO = MedicineDAO(context)
    }

    fun getMedicinesFromFavoriteMedicineList(userId:Int):ArrayList<Medicine>{
        var favoriteMedicines:ArrayList<Medicine> = ArrayList()
        for(medicineId in favoriteMedicineDAO.getAllFavoriteMedicines(1)){
            var medicine = medicineDAO.getMedicineById(medicineId)
            favoriteMedicines.add(medicine)
        }
        return favoriteMedicines
    }

    fun addMedicineToFavoriteList(userId:Int,medicineId:Int){
        if(!isPresentInFavoriteList(medicineId)){
            favoriteMedicineDAO.insertMedicineItemIntoFavoriteList(1,medicineId)
        }
    }

    fun isPresentInFavoriteList(medicineId: Int):Boolean{
        for(medicine in getMedicinesFromFavoriteMedicineList(1)){
            if(medicine.medicineId==medicineId){
                return true
            }
        }
        return false
    }

    fun removeMedicineFromFavoriteList(userId:Int,medicineId:Int){
        if(isPresentInFavoriteList(medicineId)){
            favoriteMedicineDAO.removeFavoriteMedicineFromFavoritesList(1,medicineId)
        }
    }
}