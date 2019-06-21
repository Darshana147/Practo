package com.example.practo.InterfaceListeners

interface OnMedicineOrderListener {
    fun onOrderMedicinesButtonClicked()

    fun onMedicineOrderItemClicked(orderNum:Int)

    fun onMedicineOrderListPageRefreshed()
}