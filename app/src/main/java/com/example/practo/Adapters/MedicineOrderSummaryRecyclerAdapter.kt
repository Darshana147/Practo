package com.example.practo.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.Model.MedicineCartItem
import com.example.practo.R
import kotlinx.android.synthetic.main.cart_item_list_card_view.view.*
import kotlinx.android.synthetic.main.medicine_order_summary_card_layout.view.*

class MedicineOrderSummaryRecyclerAdapter(val context:Context,var cartMedicinesList:ArrayList<MedicineCartItem>):RecyclerView.Adapter<MedicineOrderSummaryRecyclerAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        var view:View = LayoutInflater.from(context).inflate(R.layout.medicine_order_summary_card_layout,p0,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartMedicinesList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        var cartItem = cartMedicinesList.get(p1)
        p0.setData(cartItem,p1)
    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun setData(medicineCartItem: MedicineCartItem,position:Int){
            itemView.order_item_medicine_name_txv.text = medicineCartItem.medicine.medicineName
            itemView.order_item_medicine_description_txv.text = medicineCartItem.medicine.medicineDescription
            itemView.order_item_medicine_price_txv.text = medicineCartItem.medicine.medicinePrice.toString()
            itemView.order_item_medicine_qty.text = "Qty "+medicineCartItem.medicineQuantity.toString()
            itemView.order_item_medicine_total_price.text = (Math.round(medicineCartItem.medicine.medicinePrice*medicineCartItem.medicineQuantity*100.0)/100.0).toString()

            if(medicineCartItem.medicine.medicineType.equals("tablet")){
                itemView.order_medicine_imv.setImageResource(R.drawable.tablet)
            } else if(medicineCartItem.medicine.medicineType.equals("injection")){
                itemView.order_medicine_imv.setImageResource(R.drawable.injection)
            } else if(medicineCartItem.medicine.medicineType.equals("cream")){
                itemView.order_medicine_imv.setImageResource(R.drawable.cream)
            } else{
                itemView.order_medicine_imv.setImageResource(R.drawable.liquid_medicine)
            }

        }
    }
}