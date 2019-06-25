package com.example.practo.InterfaceListeners

import android.widget.TextView
import com.example.practo.Model.Medicine

interface FavoriteMedicineListListener {
    fun onRemoveMedicineFromFavoriteListListener(medicine: Medicine)

    fun onAddToCartClicked(medicineId:Int,view: TextView)

    fun notifyChangesInMedicineFavoriteList()

    fun notifyItemAddedToCart(medicineId: Int)

    fun onFavoriteMedicineClicked(medicineId: Int)
}