package com.example.practo.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.practo.InterfaceListeners.FavoriteMedicineListListener
import com.example.practo.Model.Medicine
import com.example.practo.R
import kotlinx.android.synthetic.main.medicine_favorite_list_card_layout.view.*
import kotlinx.android.synthetic.main.search_medicine_card_layout.view.*


class MedicineFavoriteListRecyclerAdapter(var context: Context, var medWishList:ArrayList<Medicine>,var listener:FavoriteMedicineListListener):RecyclerView.Adapter<MedicineFavoriteListRecyclerAdapter.MyViewHolder>() {
    var medicineWishList = medWishList
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view:View = LayoutInflater.from(context).inflate(com.example.practo.R.layout.medicine_favorite_list_card_layout,p0,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
       return medicineWishList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        var medicine = medicineWishList.get(p1)
        p0.setData(medicine,p1)
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
                itemView.favoriteList_medicine_imv.setImageResource(R.drawable.capsule)
            }
        }

        init {
            itemView.remove_item_from_favoriteList.setOnClickListener {
                Toast.makeText(context,"Item Removed",Toast.LENGTH_SHORT).show()
                listener.onRemoveMedicineFromFavoriteListListener(medicineWishList.get(adapterPosition).medicineId)
            }

            itemView.favoriteList_add_to_cart_txv.setOnClickListener {
                listener.onAddToCartClicked(medicineWishList.get(adapterPosition).medicineId)
            }
        }
    }

    fun setChangedFavoriteList(changedFavList:ArrayList<Medicine>){
        medicineWishList=changedFavList
        notifyDataSetChanged()
    }
}