package com.example.practo.InterfaceListeners

import android.view.View
import com.example.practo.Model.Medicine

interface OnSearchMedicinesFragmentListener {
    fun onAddToCartClicked(medicine:Medicine,itemView:View)

    fun onAddToFavoriteListClicked()

    fun onMedicineClicked(medicine:Medicine)

    fun onNotifyDataSetChanged(medicineId:Int,str:String)
}