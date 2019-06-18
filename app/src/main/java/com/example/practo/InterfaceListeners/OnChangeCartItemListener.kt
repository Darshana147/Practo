package com.example.practo.InterfaceListeners

import com.example.practo.Model.Medicine

interface OnChangeCartItemListener {
    fun onChangeQuantityClicked(medicineQty: Int,medicineId: Int)

    fun onCartItemRemoved(medicineId:Int)

    fun onCartItemClicked(medicineId: Int)
}