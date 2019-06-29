package com.example.practo.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.Fragments.MyDoctorsFragment
import com.example.practo.Model.Doctor
import com.example.practo.R
import kotlinx.android.synthetic.main.bookmarked_doctors_card_layout.view.*
import kotlinx.android.synthetic.main.doctor_list_card_layout.view.*

class BookmarkedDoctorsRecyclerAdapter(val context:Context,var doctorList:ArrayList<Doctor>,val myDoctorsFragment:MyDoctorsFragment):RecyclerView.Adapter<BookmarkedDoctorsRecyclerAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.bookmarked_doctors_card_layout, p0, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return doctorList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.setData(doctorList.get(p1))
        if(myDoctorsFragment.is_in_edit_mode){
            p0.itemView.booked_doctor_checkBox.visibility = View.VISIBLE
            p0.itemView.booked_doctor_checkBox.isChecked = false
        } else {
            p0.itemView.booked_doctor_checkBox.visibility = View.GONE
        }

        p0.itemView.booked_doctor_checkBox.setOnClickListener {
            myDoctorsFragment.prepareSelectionList(p0.itemView.booked_doctor_checkBox,p1)
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(doctorDetail: Doctor) {
            itemView.bookmarked_doctor_name.text = doctorDetail.name
            itemView.bookmarked_doctor_specialization.text = doctorDetail.specialization
            itemView.bookmarked_doctor_hospital_name.text = doctorDetail.hospitalDetails.hospitalName
            itemView.bookmarked_doctor_hospital_location.text = "${doctorDetail.hospitalDetails.hospitalAddress.city}, ${doctorDetail.hospitalDetails.hospitalAddress.state}, ${doctorDetail.hospitalDetails.hospitalAddress.country}"
            if (doctorDetail.gender.equals("male")) {
                itemView.bookmarked_doctor_image.setImageResource(R.drawable.ic_doctor_m)
            } else {
                itemView.bookmarked_doctor_image.setImageResource(R.drawable.ic_doctor_f)
            }
        }
    }

    fun onUpdateRecyclerView(updatedDoctorBookmarkedList:ArrayList<Doctor>){
        doctorList = updatedDoctorBookmarkedList
        notifyDataSetChanged()
    }
}