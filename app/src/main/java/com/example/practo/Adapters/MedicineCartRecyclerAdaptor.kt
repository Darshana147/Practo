package com.example.practo.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.InterfaceListeners.OnChangeCartItemListener
import com.example.practo.Model.MedicineCartItem
import com.example.practo.R
import kotlinx.android.synthetic.main.cart_item_list_card_view.view.*

class MedicineCartRecyclerAdaptor(var context: Context, var cartItemList:ArrayList<MedicineCartItem>, val listener:OnChangeCartItemListener):RecyclerView.Adapter<MedicineCartRecyclerAdaptor.MyViewHolder>(){
    var cartList:ArrayList<MedicineCartItem> = cartItemList
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.cart_item_list_card_view,p0,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val cartItem = cartList.get(p1)
        p0.setData(cartItem,p1)

        p0.itemView.medicine_cart_item_linear_layout.setOnClickListener {
            listener.onCartItemClicked(cartList.get(p1).medicine.medicineId)
        }
    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private var medicineId = 0
        fun setData(cartItem:MedicineCartItem,position:Int){
            itemView.cart_item_medicine_name_txv.text = cartItem.medicine.medicineName
            itemView.cart_item_medicine_description_txv.text = cartItem.medicine.medicineDescription
            itemView.cart_item_medicine_price_txv.text = (Math.round(cartItem.medicine.medicinePrice * cartItem.medicineQuantity.toDouble() * 100.0)/100.0).toString()
            if(cartItem.medicine.medicineType.equals("tablet")){
                itemView.cart_medicine_imv.setImageResource(R.drawable.tablet)
            } else if(cartItem.medicine.medicineType.equals("injection")){
                itemView.cart_medicine_imv.setImageResource(R.drawable.injection)
            } else if(cartItem.medicine.medicineType.equals("cream")){
                itemView.cart_medicine_imv.setImageResource(R.drawable.cream)
            } else{
                itemView.cart_medicine_imv.setImageResource(R.drawable.liquid_medicine)
            }
            //itemView.count_txv.text = cartItem.medicineQuantity.toString()
            medicineId = cartItem.medicine.medicineId
            itemView.cart_item_count_button.text = "Qty ${cartItem.medicineQuantity.toString()}"
        }

        init {
            itemView.cart_item_count_button.setOnClickListener {
               listener.onChangeQuantityClicked(cartItemList.get(adapterPosition).medicineQuantity,medicineId)

            }

            itemView.remove_item_from_medicine_cart.setOnClickListener {
                listener.onCartItemRemoved(medicineId)
            }
        }
    }

    fun setChangedCartItemList(changedCartItemList:ArrayList<MedicineCartItem>){
        cartList = changedCartItemList
        notifyDataSetChanged()
    }

}