package com.example.practo.InterfaceListeners

import android.widget.CheckBox
import com.example.practo.Model.Doctor

interface BookmarkedDoctorRecyclerAdapterListener {
    fun onDoctorClicked(doctor:Doctor)
}