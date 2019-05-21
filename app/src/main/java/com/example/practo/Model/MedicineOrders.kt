package com.example.practo.Model

data class MedicineOrder(var orderId:Int,var medicineQuantity:Int,var totalAmount:Double)

object MedicineOrderSupplier{
    val orders = listOf<MedicineOrder>(
        MedicineOrder(1,10,1000.0),
        MedicineOrder(2,20,500.0),
        MedicineOrder(3,13,1200.0),
        MedicineOrder(4,3,145.0),
        MedicineOrder(5,7,100.0),
        MedicineOrder(6,14,370.0)
    )
}

