package com.example.practo.InterfaceListeners

import com.example.practo.Model.Doctor

interface MyDoctorsListener {
    fun onSearchDoctorsClicked()

    fun onBookmarkedDoctorClicked(doctor: Doctor)
}