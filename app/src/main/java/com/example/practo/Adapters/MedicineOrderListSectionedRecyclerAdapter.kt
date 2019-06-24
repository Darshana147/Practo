package com.example.practo.Adapters

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.practo.InterfaceListeners.MedicineOrderListRecyclerViewListener
import com.example.practo.Model.SectionModel
import com.example.practo.R
import kotlinx.android.synthetic.main.medicine_order_sectioned_list_layout.view.*

class MedicineOrderListSectionedRecyclerAdapter(val context:Context,val medicineSectionList:ArrayList<SectionModel>,val listener:MedicineOrderListSectionedRecyclerViewListener):RecyclerView.Adapter<MedicineOrderListSectionedRecyclerAdapter.MyViewHolder>(),MedicineOrderListRecyclerViewListener{
    override fun onMedicineOrderItemClicked(orderNum: Int) {
       listener.onMedicineOrderFromListClicked(orderNum)
    }

    var medSectionList = medicineSectionList
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.medicine_order_sectioned_list_layout,p0,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return medSectionList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.itemView.medicine_order_list_recycler_view.setHasFixedSize(true)
        p0.itemView.medicine_order_list_recycler_view.isNestedScrollingEnabled = false
        val linearLayoutManager = LinearLayoutManager(context,LinearLayout.VERTICAL,false)
        p0.itemView.medicine_order_list_recycler_view.layoutManager = linearLayoutManager
        val adapter = MedicineOrderListRecyclerAdapter(context,medSectionList.get(p1).orderList,this,medSectionList.get(p1).sectionLabel)
        p0.itemView.medicine_order_list_recycler_view.adapter = adapter

        p0.setData(medSectionList.get(p1).sectionLabel)
    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun setData(sectionLabel:String){
            itemView.medicine_order_list_section_label_txv.text = sectionLabel
        }
    }

    interface MedicineOrderListSectionedRecyclerViewListener{
        fun onMedicineOrderFromListClicked(orderNum:Int)
    }
}