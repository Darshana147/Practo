package com.example.practo.Adapters

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import com.example.practo.InterfaceListeners.OnSearchMedicinesFragmentListener
import com.example.practo.Model.Dosage
import com.example.practo.Model.Medicine
import com.example.practo.Model.MedicineDescription
import com.example.practo.R
import com.example.practo.UseCases.FavoriteMedicineUseCases
import com.example.practo.UseCases.MedicineCartUseCases
import com.example.practo.Utils.toast
import kotlinx.android.synthetic.main.search_medicine_card_layout.view.*


class SearchMedicineRecyclerAdaptor(
    var context: Context,
    var medList: ArrayList<Medicine>,
    val listener: OnSearchMedicinesFragmentListener,
    val favoriteMedicineUseCases: FavoriteMedicineUseCases,
    val medicineCartUseCases: MedicineCartUseCases
) : RecyclerView.Adapter<SearchMedicineRecyclerAdaptor.MyViewHolder>() {
    var medicineList: ArrayList<Medicine> = sortMedicineListAsPerFavorites()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(com.example.practo.R.layout.search_medicine_card_layout, p0, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return medicineList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.itemView.favorite_medicine.setColorFilter(null)
        p0.itemView.added_to_cart_status_txv.visibility = View.INVISIBLE
        val medicine = medicineList.get(p1)
        p0.setData(medicine, p1)

        p0.itemView.favorite_medicine.setOnClickListener {

            if (favoriteMedicineUseCases.isPresentInFavoriteList(medicineList.get(p1).medicineId)) {
                p0.itemView.favorite_medicine.setColorFilter(null)
                favoriteMedicineUseCases.removeMedicineFromFavoriteList(
                    1,
                    medicineList.get(p1).medicineId
                )

//                val tempMed = medicineList.removeAt(p1)
//                medicineList.add(medicineList.lastIndex, tempMed)
                medicineList = sortMedicineListAsPerFavorites()
                notifyDataSetChanged()

            } else {
                p0.itemView.favorite_medicine.setColorFilter(
                    ContextCompat.getColor(
                        context,
                        R.color.favorite_ic_color
                    )
                )
//                p0.itemView.favorite_medicine.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_item))
                context.toast("Item added to Favorite List")
                favoriteMedicineUseCases.addMedicineToFavoriteList(1, medicineList.get(p1).medicineId)
//                val tempMed = medicineList.removeAt(p1)
//                medicineList.add(0, tempMed)
                medicineList = sortMedicineListAsPerFavorites()
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
                Medicine(medId, medName, medDescription, medPrice, medType,medDetailedDescription),
                p0.itemView.added_to_cart_status_txv
            )
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(medicine: Medicine, position: Int) {
            itemView.tag = medicine.medicineId
            itemView.medicine_name_txv.text = medicine.medicineName.toString()
            itemView.medicine_description_txv.text = medicine.medicineDescription.toString()
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
            if (favoriteMedicineUseCases.isPresentInFavoriteList(medicineList.get(position).medicineId)) {
                itemView.favorite_medicine.setColorFilter(ContextCompat.getColor(context, R.color.favorite_ic_color))
            }
            if (medicineCartUseCases.isPresentInMedicineCart(medicine.medicineId)) {
                itemView.added_to_cart_status_txv.visibility = View.VISIBLE
            }

        }

    }

    fun filterList(filteredList: ArrayList<Medicine>) {
        medicineList = sortMedicineListAsPerFavorites(filteredList)
        notifyDataSetChanged()
    }

    fun dataSetChanged() {
        medicineList = sortMedicineListAsPerFavorites()
        notifyDataSetChanged()
    }

    fun sortMedicineListAsPerFavorites(medList: ArrayList<Medicine> = this.medList): ArrayList<Medicine> {
//        var medicineList = arrayListOf<Medicine>()
        val medicineList = getFavoriteMedicinesFromList(medList)
        for (medicine in medList) {
            if (!(favoriteMedicineUseCases.isPresentInFavoriteList(medicine.medicineId))) {
                medicineList.add(medicine)
            }
        }
        return medicineList
    }

    fun getFavoriteMedicinesFromList(medList: ArrayList<Medicine>): ArrayList<Medicine> {
        val medicineList = arrayListOf<Medicine>()
        for (medicine in medList) {
            if (favoriteMedicineUseCases.isPresentInFavoriteList(medicine.medicineId)) {
                medicineList.add(medicine)
            }
        }
        return medicineList
    }
}