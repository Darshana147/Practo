package com.example.practo.Model

data class UserMedicineDeliveryAddressDetails(var userName:String,var userMobileNumber:String,var userPinCode:String,var userAddress:String,var userLocality:String,var userCity:String,var userState:String,var userCountry:String,var typeOfAddress:String)

object UserDeliveryAddressStorage{
    val userDeliveryAddress = arrayListOf<UserMedicineDeliveryAddressDetails>(
        UserMedicineDeliveryAddressDetails("Darshana","9677258883","600017","Raghavaiah Road","T-nagar","Chennai","Tamil Nadu","India","home"),
        UserMedicineDeliveryAddressDetails("Abcd","9677250883","600007","Raghavaiah Road","Anna-nagar","Chennai","Tamil Nadu","India","home")
    )
}