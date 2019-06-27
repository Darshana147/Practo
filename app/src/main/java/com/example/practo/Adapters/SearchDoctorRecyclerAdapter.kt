package com.example.practo.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.InterfaceListeners.SearchDoctorsAdapterListener
import com.example.practo.R
import kotlinx.android.synthetic.main.search_doctors_by_specialization_item_layout.view.*

class SearchDoctorRecyclerAdapter(val context: Context, val specialists: ArrayList<String>, val specialistImages: ArrayList<Int>, val listener:SearchDoctorsAdapterListener):RecyclerView.Adapter<SearchDoctorRecyclerAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.search_doctors_by_specialization_item_layout, p0, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return specialists.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.setData(specialists[p1],specialistImages[p1])

        p0.itemView.doctor_specialist_linear_layout.setOnClickListener {
            listener.onSpecializationFieldSelected(specialists[p1])
        }
    }

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun setData(specialist:String,imgResource:Int){
            itemView.specialization_txv.text = specialist
            itemView.specialization_imv.setImageResource(imgResource)
        }
    }
}