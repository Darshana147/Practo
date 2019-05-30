package com.example.practo.Adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.practo.InterfaceListeners.OnAddToCartSelectedListener
import com.example.practo.Model.Medicine
import com.example.practo.R
import com.example.practo.UseCases.FavoriteMedicineUseCases
import kotlinx.android.synthetic.main.search_medicine_card_layout.view.*


class SearchMedicineRecyclerAdaptor(
    var context: Context,
    var medList: ArrayList<Medicine>,
    val listener: OnAddToCartSelectedListener,
    val favoriteMedicineUseCases: FavoriteMedicineUseCases
) : RecyclerView.Adapter<SearchMedicineRecyclerAdaptor.MyViewHolder>() {
    var medicineList: ArrayList<Medicine> = medList
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
        var medicine = medicineList.get(p1)
        p0.setData(medicine, p1)
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
                itemView.medicine_imv.setImageResource(R.drawable.capsule)
            }


            if (favoriteMedicineUseCases.isPresentInFavoriteList(medicineList.get(position).medicineId)) {
                itemView.favorite_medicine.setColorFilter(ContextCompat.getColor(context, R.color.favorite_ic_color))
            }

        }


        init {
            itemView.add_to_cart_txv.setOnClickListener {
                var medId = medicineList.get(adapterPosition).medicineId
                var medName = medicineList.get(adapterPosition).medicineName
                var medDescription = medicineList.get(adapterPosition).medicineDescription
                var medPrice = medicineList.get(adapterPosition).medicinePrice
                var medType = medicineList.get(adapterPosition).medicineType
                var medicine = Medicine(medId, medName, medDescription, medPrice, medType)

                listener.onAddToCartClicked(medicine)
            }

            itemView.favorite_medicine.setOnClickListener {


                if (favoriteMedicineUseCases.isPresentInFavoriteList(medicineList.get(adapterPosition).medicineId)) {
                    itemView.favorite_medicine.setColorFilter(null)
                    favoriteMedicineUseCases.removeMedicineFromFavoriteList(
                        1,
                        medicineList.get(adapterPosition).medicineId
                    )
                } else {
                    itemView.favorite_medicine.setColorFilter(
                        ContextCompat.getColor(
                            context,
                            R.color.favorite_ic_color
                        )
                    )
                    itemView.favorite_medicine.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_item))
                    Toast.makeText(context,"Item added to Favorite List", Toast.LENGTH_SHORT).show()
                    favoriteMedicineUseCases.addMedicineToFavoriteList(1, medicineList.get(adapterPosition).medicineId)
                }
            }


        }

    }

    fun filterList(filteredList: ArrayList<Medicine>) {
        medicineList = filteredList
        notifyDataSetChanged()
    }
}