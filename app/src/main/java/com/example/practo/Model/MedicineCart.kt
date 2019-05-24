package com.example.practo.Model

data class MedicineCart(var medicineCartItems:ArrayList<MedicineCartItem>,var totalNumOfItems:Int,var totalPrice:Double)

object MedicineCartSupplier{
    private var medicineCartItems:ArrayList<MedicineCartItem> = arrayListOf()
    private var medicineCartItemTotalQuantity:Int=0
    private var medicineCartTotalPrice:Double=0.0
    var medicineCart = MedicineCart(medicineCartItems, medicineCartItemTotalQuantity, medicineCartTotalPrice)
}