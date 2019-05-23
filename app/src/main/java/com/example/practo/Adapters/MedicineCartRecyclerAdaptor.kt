package com.example.practo.Adapters

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.practo.InterfaceListeners.OnChangeCartItemQtyListener
import com.example.practo.Model.MedicineCart
import com.example.practo.R
import kotlinx.android.synthetic.main.cart_item_list_card_view.view.*
import kotlinx.android.synthetic.main.search_medicine_card_layout.view.*
import java.text.FieldPosition

class MedicineCartRecyclerAdaptor(var context: Context,var cartItemList:ArrayList<MedicineCart>,val listener:OnChangeCartItemQtyListener):RecyclerView.Adapter<MedicineCartRecyclerAdaptor.MyViewHolder>(){
    var cartList:ArrayList<MedicineCart> = cartItemList
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        var view:View = LayoutInflater.from(context).inflate(R.layout.cart_item_list_card_view,p0,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        var cartItem = cartList.get(p1)
        p0.setData(cartItem,p1)
    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private var medicineId = 0
        fun setData(cartItem:MedicineCart,position:Int){
            itemView.cart_item_medicine_name_txv.text = cartItem.medicine.medicineName
            itemView.cart_item_medicine_description_txv.text = cartItem.medicine.medicineDescription
            itemView.cart_item_medicine_price_txv.text = cartItem.medicine.medicinePrice.toString()
            if(cartItem.medicine.medicineType.equals("tablet")){
                itemView.cart_medicine_imv.setImageResource(R.drawable.tablet)
            } else if(cartItem.medicine.medicineType.equals("injection")){
                itemView.cart_medicine_imv.setImageResource(R.drawable.injection)
            } else if(cartItem.medicine.medicineType.equals("cream")){
                itemView.cart_medicine_imv.setImageResource(R.drawable.cream)
            } else{
                itemView.cart_medicine_imv.setImageResource(R.drawable.capsule)
            }
            //itemView.count_txv.text = cartItem.medicineQuantity.toString()
            medicineId = cartItem.medicine.medicineId
            itemView.cart_item_count_button.text = "Qty ${cartItem.medicineQuantity.toString()}"
        }

        init {
            itemView.cart_item_count_button.setOnClickListener {
               listener.onChangeQuantityClicked(medicineId)

            }

            itemView.remove_item_from_medicine_cart.setOnClickListener {
                listener.onCartItemRemoved(medicineId)
            }
        }
    }

    fun setChangedCartItemList(changedCartItemList:ArrayList<MedicineCart>){
        cartList = changedCartItemList
        notifyDataSetChanged()
    }

}