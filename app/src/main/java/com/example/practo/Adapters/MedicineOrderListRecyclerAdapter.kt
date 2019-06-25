package com.example.practo.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.InterfaceListeners.MedicineOrderListRecyclerViewListener
import com.example.practo.Model.MedicineOrder
import com.example.practo.R
import kotlinx.android.synthetic.main.medicine_order_card_view.view.*
import java.text.FieldPosition

class MedicineOrderListRecyclerAdapter(val context: Context,val medicineOrderList:List<MedicineOrder>,val mMedicineOrderListRecyclerViewListener: MedicineOrderListRecyclerViewListener,val medStatus:String):RecyclerView.Adapter<MedicineOrderListRecyclerAdapter.MyViewHolder>(){
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

        p0.itemView.med_order_linear_layout.setOnClickListener{
            mMedicineOrderListRecyclerViewListener.onMedicineOrderItemClicked(medicineOrderList.get(p1).orderId)
        }
    }

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun setData(medicineOrder:MedicineOrder,position: Int){
            itemView.delivery_date_order_list.text = medicineOrder.deliveryDate
            itemView.order_no_txv.text = "#PRAC-M${medicineOrder.orderId.toString()}"
            itemView.order_quantity_txv.text = medicineOrder.medicineCart.totalNumOfItems.toString()
            itemView.order_total_amount_txv.text = medicineOrder.medicineCart.totalPrice.toString()
            if(medStatus.toLowerCase().equals("delivered orders"))
              itemView.order_status.text = "Order has been delivered"
            else
                itemView.order_status.text = "Order has been confirmed"

        }
    }
}