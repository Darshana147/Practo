package com.example.practo.InterfaceListeners

interface FavoriteMedicineListListener {
    fun onRemoveMedicineFromFavoriteListListener(medicineId:Int)

    fun onAddToCartClicked(medicineId:Int)
}