package com.example.practo.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.Model.MedicineOrder
import com.example.practo.R
import kotlinx.android.synthetic.main.medicine_order_card_view.view.*
import java.text.FieldPosition

class MedicineOrderListRecyclerAdapter(val context: Context,val medicineOrderList:List<MedicineOrder>):RecyclerView.Adapter<MedicineOrderListRecyclerAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.medicine_order_card_view,p0,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
       return medicineOrderList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val medicineOrder = medicineOrderList.get(p1)
        p0.setData(medicineOrder,p1)
    }

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun setData(medicineOrder:MedicineOrder,position: Int){
            itemView.order_no_txv.text = medicineOrder.orderId.toString()
            itemView.order_quantity_txv.text = medicineOrder.medicineQuantity.toString()
            itemView.order_total_amount_txv.text = medicineOrder.totalAmount.toString()
        }
    }
}