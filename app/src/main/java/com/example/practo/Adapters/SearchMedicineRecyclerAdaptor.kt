package com.example.practo.Adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.InterfaceListeners.OnSearchMedicinesFragmentListener
import com.example.practo.Model.Medicine
import com.example.practo.R
import com.example.practo.UseCases.FavoriteMedicineUseCases
import com.example.practo.Utils.toast
import kotlinx.android.synthetic.main.search_medicine_card_layout.view.*


class SearchMedicineRecyclerAdaptor(
    var context: Context,
    var medList: ArrayList<Medicine>,
    var favMedicinesHashSet: HashSet<Int>,
    var cartMedicinesHashSet: HashSet<Int>,
    val listener: OnSearchMedicinesFragmentListener,
    val favoriteMedicineUseCases: FavoriteMedicineUseCases
) : RecyclerView.Adapter<SearchMedicineRecyclerAdaptor.MyViewHolder>() {
    var medicineList = sortMedAsPerFavorites(medList)
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.search_medicine_card_layout, p0, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return medicineList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.itemView.favorite_medicine.setColorFilter(null)
        p0.itemView.add_to_cart_txv.text = "ADD TO CART"
        val medicine = medicineList.get(p1)
        p0.setData(medicine, p1)

        p0.itemView.favorite_medicine.setOnClickListener {
            if (favMedicinesHashSet.contains(medicineList.get(p1).medicineId)) {
                p0.itemView.favorite_medicine.setColorFilter(null)
                favoriteMedicineUseCases.removeMedicineFromFavoriteList(
                    1,
                    medicineList.get(p1).medicineId
                )
                favMedicinesHashSet.remove(medicineList.get(p1).medicineId)
                medicineList = sortMedAsPerFavorites(medicineList)
                notifyDataSetChanged()

            } else {
                p0.itemView.favorite_medicine.setColorFilter(
                    ContextCompat.getColor(
                        context,
                        R.color.favorite_ic_color
                    )
                )
                context.toast("Item added to Favorite List")
                favoriteMedicineUseCases.addMedicineToFavoriteList(1, medicineList.get(p1).medicineId)
                favMedicinesHashSet.add(medicineList.get(p1).medicineId)
                medicineList = sortMedAsPerFavorites(medicineList)
                notifyDataSetChanged()
            }
            listener.onAddToFavoriteListClicked()

        }


        p0.itemView.search_medicine_linear_layout.setOnClickListener {
            listener.onMedicineClicked(medicineList.get(p1))
        }

        p0.itemView.add_to_cart_txv.setOnClickListener {
            val medId = medicineList.get(p1).medicineId
            val medName = medicineList.get(p1).medicineName
            val medDescription = medicineList.get(p1).medicineDescription
            val medPrice = medicineList.get(p1).medicinePrice
            val medType = medicineList.get(p1).medicineType

            val medDetailedDescription = medicineList.get(p1).medicineDetailedDescription

            listener.onAddToCartClicked(
                Medicine(medId, medName, medDescription, medPrice, medType, medDetailedDescription),
                p0.itemView.add_to_cart_txv
            )
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(medicine: Medicine, position: Int) {
            itemView.tag = medicine.medicineId
            itemView.medicine_name_txv.text = medicine.medicineName
            itemView.medicine_description_txv.text = medicine.medicineDescription
            itemView.medicine_price_txv.text = medicine.medicinePrice.toString()
            if (medicine.medicineType.equals("tablet")) {
                itemView.medicine_imv.setImageResource(R.drawable.tablet)
            } else if (medicine.medicineType.equals("injection")) {
                itemView.medicine_imv.setImageResource(R.drawable.injection)
            } else if (medicine.medicineType.equals("cream")) {
                itemView.medicine_imv.setImageResource(R.drawable.cream)
            } else {
                itemView.medicine_imv.setImageResource(R.drawable.liquid_medicine)
            }
            if (favMedicinesHashSet.contains(medicineList.get(position).medicineId)) {
                itemView.favorite_medicine.setColorFilter(ContextCompat.getColor(context, R.color.favorite_ic_color))
            }
            if (cartMedicinesHashSet.contains(medicine.medicineId)) {
                itemView.add_to_cart_txv.text = "VIEW CART"
            }

        }

    }

    fun filterList(filteredList: ArrayList<Medicine>) {
        medicineList = sortMedAsPerFavorites(filteredList)
        notifyDataSetChanged()
    }

    fun notifyItemRemovedFromFavList(medicineId: Int) {
        favMedicinesHashSet.remove(medicineId)
        medicineList = sortMedAsPerFavorites()
        notifyDataSetChanged()
    }

    fun notifyItemAddedToCart(medicineId: Int) {
        cartMedicinesHashSet.add(medicineId)
        medicineList = sortMedAsPerFavorites()
        notifyDataSetChanged()
    }


    fun getFavoriteMedicineFromFavList(medList: ArrayList<Medicine>): ArrayList<Medicine> {
        val medicineList = arrayListOf<Medicine>()
        for (medicine in medList) {
            if (favMedicinesHashSet.contains(medicine.medicineId)) {
                medicineList.add(medicine)
            }
        }
        return medicineList
    }

    fun sortMedAsPerFavorites(allMedicines: ArrayList<Medicine> = medList): ArrayList<Medicine> {
        val favMedicineList = ArrayList(getFavoriteMedicineFromFavList(allMedicines).sortedWith(compareBy { it.medicineName }))
        var otherMedicines = arrayListOf<Medicine>()
        Log.d("abcd","fav Medicines size ${favMedicineList.size}")
        for (medicine in allMedicines) {
            if (!favMedicinesHashSet.contains(medicine.medicineId)) {
//                favMedicineList.add(medicine)
                otherMedicines.add(medicine)
            }
        }
        otherMedicines = ArrayList(otherMedicines.sortedWith(compareBy { it.medicineName }))
        favMedicineList.addAll(otherMedicines)
        return favMedicineList
    }

}