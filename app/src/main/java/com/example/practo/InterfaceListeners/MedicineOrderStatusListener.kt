package com.example.practo.InterfaceListeners

import com.example.practo.Model.MedicineCart
import com.example.practo.Model.MedicineCartItem

interface MedicineOrderStatusListener {
    fun onPageRefreshed()

    fun onMedicineOrderCanceled()

    fun onMedicineReorderClicked(medCartItems:ArrayList<MedicineCartItem>)
}