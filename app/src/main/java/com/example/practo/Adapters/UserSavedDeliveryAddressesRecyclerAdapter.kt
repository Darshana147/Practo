package com.example.practo.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.example.practo.Fragments.UserSavedDeliveryAddressesFragment
import com.example.practo.Model.Medicine
import com.example.practo.Model.UserMedicineDeliveryAddressDetails
import kotlinx.android.synthetic.main.search_medicine_card_layout.view.*
import kotlinx.android.synthetic.main.user_saved_delivery_addresses_card_layout.view.*


class UserSavedDeliveryAddressesRecyclerAdapter(var context: Context, var addressList: ArrayList<UserMedicineDeliveryAddressDetails>,var listener:UserSavedDeliveryAddressesFragment):RecyclerView.Adapter<UserSavedDeliveryAddressesRecyclerAdapter.MyViewHolder>(){
    var mSelectedItem = 0

    var userDeliveryAddressList = addressList
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(com.example.practo.R.layout.user_saved_delivery_addresses_card_layout, p0, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userDeliveryAddressList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val address = userDeliveryAddressList.get(p1)
        p0.itemView.user_delivery_address_radio_button.isChecked=false
        p0.setData(address, p1)
    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun setData(userDeliveryAddress:UserMedicineDeliveryAddressDetails,position: Int){
            this.itemView.user_name.text = userDeliveryAddress.userName
            itemView.user_delivery_address.text = "${userDeliveryAddress.userAddress}, ${userDeliveryAddress.userLocality}," +
                    "\n${userDeliveryAddress.userCity}, ${userDeliveryAddress.userState}, ${userDeliveryAddress.userCountry},\n${userDeliveryAddress.userPinCode}"
            itemView.user_mobile_number.text = userDeliveryAddress.userMobileNumber
            itemView.user_delivery_address_radio_button.setChecked(position==mSelectedItem)

        }

        init{
            itemView.edit_address_btn.setOnClickListener {
                listener.onEditAddressClicked(userDeliveryAddressList.get(adapterPosition))
            }

            itemView.user_delivery_address_radio_button.setOnClickListener{
                mSelectedItem = adapterPosition
                notifyDataSetChanged()
            }

            itemView.remove_address_imv.setOnClickListener {
                listener.onRemoveDeliveryAddressClicked(userDeliveryAddressList.get(adapterPosition).userAddressId)
            }
        }
    }

    fun changeInDeliveryAddressRecord(userDeliveryAddressDetails:ArrayList<UserMedicineDeliveryAddressDetails>){
        userDeliveryAddressList = userDeliveryAddressDetails
        notifyDataSetChanged()
    }

    fun getSelectedAddressDetails():UserMedicineDeliveryAddressDetails{
        return userDeliveryAddressList.get(mSelectedItem)
    }
}