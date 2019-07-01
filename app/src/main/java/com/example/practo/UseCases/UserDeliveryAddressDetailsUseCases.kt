package com.example.practo.UseCases

import android.content.Context
import com.example.practo.DAO.UserDeliveryAddressDAO
import com.example.practo.Model.UserMedicineDeliveryAddressDetails

class UserDeliveryAddressDetailsUseCases(context:Context) {
    private var userDeliveryAddressDAO:UserDeliveryAddressDAO

    init {
        userDeliveryAddressDAO = UserDeliveryAddressDAO(context)
    }

    fun insertUserAddress(userMedicineDeliveryAddressDetails:UserMedicineDeliveryAddressDetails){
        userDeliveryAddressDAO.insertUserDeliveryAddress(1,userMedicineDeliveryAddressDetails)
    }

    fun getAllAddresses():ArrayList<UserMedicineDeliveryAddressDetails>{
        return userDeliveryAddressDAO.getAllAddresses(1)
    }

//    fun getUserAddressById(userAddressId:Int):UserMedicineDeliveryAddressDetails?{
//        return userDeliveryAddressDAO.getAddressByAddressId(userAddressId)
//    }

    fun updateUserDeliveryAdress(userMedicineDeliveryAddressDetails: UserMedicineDeliveryAddressDetails){
        userDeliveryAddressDAO.updateUserDeliveryAddressDetails(1,userMedicineDeliveryAddressDetails)
    }

    fun deteleUserDeliveryAddressRecord(addressId:Int){
        userDeliveryAddressDAO.deleteDeliveryAddressRecord(1,addressId)
    }
}