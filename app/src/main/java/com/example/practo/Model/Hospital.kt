package com.example.practo.Model

import java.io.Serializable

data class Hospital(var hospitalName:String, var hospitalAddress:HospitalAddress, var hospitalContactNumber:String)

data class HospitalAddress(var address:String, var city:String, var state:String, var country:String, var pincode:String)