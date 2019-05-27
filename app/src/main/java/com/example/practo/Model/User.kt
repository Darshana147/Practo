package com.example.practo.Model

 class User{
    var userId = 1
    var userName = "abc"
    lateinit var medicineCart: MedicineCart
    lateinit var medicineWishList: ArrayList<Medicine>
    lateinit var medicineOrders:ArrayList<MedicineOrder>

}

object WishListSupplier{
    val medicineWishList = arrayListOf<Medicine>()
}

