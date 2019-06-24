package com.example.practo.Adapters

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import com.example.practo.InterfaceListeners.FavoriteMedicineListListener
import com.example.practo.Model.Medicine
import com.example.practo.R
import com.example.practo.UseCases.MedicineCartUseCases
import com.example.practo.Utils.toast
import kotlinx.android.synthetic.main.medicine_favorite_list_card_layout.view.*
import kotlinx.android.synthetic.main.search_medicine_card_layout.view.*


class MedicineFavoriteListRecyclerAdapter(var context: Context, var medWishList:ArrayList<Medicine>,var listener:FavoriteMedicineListListener,var medicineCartUseCases: MedicineCartUseCases):RecyclerView.Adapter<MedicineFavoriteListRecyclerAdapter.MyViewHolder>() {
    var medicineWishList = medWishList
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view:View = LayoutInflater.from(context).inflate(com.example.practo.R.layout.medicine_favorite_list_card_layout,p0,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return medicineWishList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val medicine = medicineWishList.get(p1)
        p0.itemView.remove_item_from_favoriteList.setColorFilter(
            ContextCompat.getColor(
                context,
                R.color.favorite_ic_color
            ))
        //p0.itemView.added_to_cart_from_fav_status_txv.visibility = View.INVISIBLE
        p0.itemView.favoriteList_add_to_cart_txv.text = "ADD TO CART"
        p0.setData(medicine,p1)

        p0.itemView.favorite_medicine_linear_layout.setOnClickListener {
            listener.onFavoriteMedicineClicked(medicineWishList.get(p1).medicineId)
        }

        p0.itemView.remove_item_from_favoriteList.setOnClickListener {
            listener.onRemoveMedicineFromFavoriteListListener(medicineWishList.get(p1).medicineId)
        }
        p0.itemView.favoriteList_add_to_cart_txv.setOnClickListener {
            listener.onAddToCartClicked(medicineWishList.get(p1).medicineId,p0.itemView.favoriteList_add_to_cart_txv)
        }
    }

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun setData(medicine:Medicine,position:Int){
            itemView.favoriteList_medicine_name_txv.text = medicine.medicineName
            itemView.favoriteList_medicine_description_txv.text = medicine.medicineDescription
            itemView.favoriteList_medicine_price_txv.text = medicine.medicinePrice.toString()
            if(medicine.medicineType.equals("tablet")){
                itemView.favoriteList_medicine_imv.setImageResource(R.drawable.tablet)
            } else if(medicine.medicineType.equals("injection")){
                itemView.favoriteList_medicine_imv.setImageResource(R.drawable.injection)
            } else if(medicine.medicineType.equals("cream")){
                itemView.favoriteList_medicine_imv.setImageResource(R.drawable.cream)
            } else{
                itemView.favoriteList_medicine_imv.setImageResource(R.drawable.liquid_medicine)
            }
            if(medicineCartUseCases.isPresentInMedicineCart(medicine.medicineId)){
                //itemView.added_to_cart_from_fav_status_txv.visibility = View.VISIBLE
                itemView.favoriteList_add_to_cart_txv.text = "VIEW CART"
            }
        }

    }

    fun setChangedFavoriteList(changedFavList:ArrayList<Medicine>){
        medicineWishList=changedFavList
        notifyDataSetChanged()
    }

    fun filterList(filteredList: ArrayList<Medicine>) {
        medicineWishList = filteredList
        notifyDataSetChanged()
    }

}