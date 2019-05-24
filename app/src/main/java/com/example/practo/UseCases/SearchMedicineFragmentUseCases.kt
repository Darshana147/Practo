package com.example.practo.UseCases

import com.example.practo.Model.Medicine
import com.example.practo.Model.MedicineSupplier
import com.example.practo.Model.WishListSupplier

class SearchMedicineFragmentUseCases {

    fun isPresentInWishList(medicineId:Int):Boolean{
        for(medicine in WishListSupplier.medicineWishList){
            if(medicine.medicineId==medicineId){
                return true
            }
        }
        return false
    }

    fun getMedicineById(medicineId:Int): Medicine?{
        for(medicine in MedicineSupplier.medicineList){
            if(medicine.medicineId==medicineId){
                return medicine
            }
        }
        return null
    }

    fun addToWishList(medicineId:Int){
        if(!isPresentInWishList(medicineId)){
            WishListSupplier.medicineWishList.add(this.getMedicineById(medicineId)!!)
        }
    }

    fun removeFromWishList(medicineId:Int){
        if(isPresentInWishList(medicineId)){
           for(medicine in WishListSupplier.medicineWishList){
               if(medicine.medicineId==medicineId){
                   WishListSupplier.medicineWishList.remove(medicine)
                   break
               }
           }
        }
    }
}