package com.example.practo.InterfaceListeners

import com.example.practo.Model.MedicineCart

interface OnChangeCartItemQtyListener {
    fun onChangeQuantityClicked(medicineId:Int)

    fun onCartItemRemoved(medicineId:Int)
}