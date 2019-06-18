package com.example.practo.Model

class UserMedicineDeliveryAddressDetails(var userName:String,var userMobileNumber:String,var userPinCode:String,
                                              var userAddress:String,var userLocality:String,var userCity:String,var userState:String,var userCountry:String,var typeOfAddress:String){

    var userAddressId:Int = 0

    constructor( userAddressId:Int, userName:String, userMobileNumber:String,userPinCode:String,
                userAddress:String,userLocality:String,userCity:String,userState:String,userCountry:String,typeOfAddress:String):this(userName,userMobileNumber,userPinCode,userAddress,userLocality,userCity,userState,userCountry,typeOfAddress){
        this.userAddressId = userAddressId
    }
}
