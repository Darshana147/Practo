package com.example.practo.InterfaceListeners

import com.example.practo.Model.Doctor

interface DoctorListAdapterListener {
    fun onDoctorSelectedFromList(doctor: Doctor)
}