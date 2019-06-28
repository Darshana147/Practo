package com.example.practo.InterfaceListeners

import com.example.practo.Model.Doctor
import java.io.Serializable

interface DoctorListFragmentListener {
    fun onDoctorSelectedFromList(doctor: Doctor)
}