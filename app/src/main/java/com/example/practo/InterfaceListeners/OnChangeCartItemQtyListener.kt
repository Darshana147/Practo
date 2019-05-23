package com.example.practo.InterfaceListeners

interface OnChangeCartItemQtyListener {
    fun onChangeQuantityClicked(medicineId:Int)

    fun onCartItemRemoved(medicineId:Int)
}