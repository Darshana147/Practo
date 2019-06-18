package com.example.practo.InterfaceListeners

import android.widget.TextView
import com.example.practo.Model.Medicine

interface FavoriteMedicineListListener {
    fun onRemoveMedicineFromFavoriteListListener(medicineId:Int)

    fun onAddToCartClicked(medicineId:Int,view: TextView)

    fun notifyChangesInMedicineFavoriteList()

    fun onFavoriteMedicineClicked(medicineId: Int)
}