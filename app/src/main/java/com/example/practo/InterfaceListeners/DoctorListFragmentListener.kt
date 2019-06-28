package com.example.practo.InterfaceListeners

import com.example.practo.Model.Doctor
import java.io.Serializable

interface DoctorListFragmentListener : Serializable{
    fun onDoctorSelectedFromList(doctor: Doctor)
}