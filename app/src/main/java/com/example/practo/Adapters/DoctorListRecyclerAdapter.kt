package com.example.practo.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.InterfaceListeners.DoctorListAdapterListener
import com.example.practo.Model.Doctor
import com.example.practo.R
import kotlinx.android.synthetic.main.doctor_list_card_layout.view.*

class DoctorListRecyclerAdapter(
    val context: Context,
    val doctorList: ArrayList<Doctor>,
    val listener: DoctorListAdapterListener
) : RecyclerView.Adapter<DoctorListRecyclerAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.doctor_list_card_layout, p0, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return doctorList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.setData(doctorList.get(p1))
        p0.itemView.doctor_list_linear_layout.setOnClickListener {
            listener.onDoctorSelectedFromList(doctorList.get(p1))
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(doctorDetail: Doctor) {
            itemView.doctor_name.text = doctorDetail.name
            itemView.doctor_specialization.text = doctorDetail.specialization
            itemView.hospital_name.text = doctorDetail.hospitalDetails.hospitalName
            itemView.hospital_location.text = "${doctorDetail.hospitalDetails.hospitalAddress.city}, ${doctorDetail.hospitalDetails.hospitalAddress.state}, ${doctorDetail.hospitalDetails.hospitalAddress.country}"
            if (doctorDetail.gender.equals("male")) {
                itemView.doctor_image.setImageResource(R.drawable.ic_doctor_m)
            } else {
                itemView.doctor_image.setImageResource(R.drawable.ic_doctor_f)
            }
        }
    }
}