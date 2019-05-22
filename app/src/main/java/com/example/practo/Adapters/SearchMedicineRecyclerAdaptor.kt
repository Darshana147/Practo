package com.example.practo.Adapters

import android.content.Context
import android.content.DialogInterface
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.practo.Fragments.AddToCartDialogFragment
import com.example.practo.InterfaceListeners.OnAddToCartSelectedListener
import com.example.practo.Model.Medicine
import com.example.practo.R
import kotlinx.android.synthetic.main.add_to_cart_custom_dialog_layout.view.*
import kotlinx.android.synthetic.main.search_medicine_card_layout.view.*

class SearchMedicineRecyclerAdaptor(var context: Context,var medList:ArrayList<Medicine>,val listener: OnAddToCartSelectedListener):RecyclerView.Adapter<SearchMedicineRecyclerAdaptor.MyViewHolder>() {
    var medicineList:ArrayList<Medicine> = medList
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.search_medicine_card_layout,p0,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
       return medicineList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        var medicine = medicineList.get(p1)
        p0.setData(medicine,p1)
    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun setData(medicine:Medicine,position:Int){
            itemView.medicine_name_txv.text = medicine.medicineName.toString()
            itemView.medicine_description_txv.text = medicine.medicineDescription.toString()
            itemView.medicine_price_txv.text = medicine.medicinePrice.toString()
            if(medicine.medicineType.equals("tablet")){
                itemView.medicine_imv.setImageResource(R.drawable.tablet)
            } else if(medicine.medicineType.equals("injection")){
                itemView.medicine_imv.setImageResource(R.drawable.injection)
            } else if(medicine.medicineType.equals("cream")){
                itemView.medicine_imv.setImageResource(R.drawable.cream)
            } else{
                itemView.medicine_imv.setImageResource(R.drawable.capsule)
            }
        }

        init {
           itemView.add_to_cart_txv.setOnClickListener {
               var medId = medicineList.get(adapterPosition).medicineId
               var medName = medicineList.get(adapterPosition).medicineName
               var medDescription = medicineList.get(adapterPosition).medicineDescription
               var medPrice = medicineList.get(adapterPosition).medicinePrice
               var medType = medicineList.get(adapterPosition).medicineType
               var medicine = Medicine(medId,medName,medDescription,medPrice,medType)

               listener.onAddToCartClicked(medicine)
           }
        }

    }

    fun filterList(filteredList:ArrayList<Medicine>){
        medicineList = filteredList
        notifyDataSetChanged()
    }
}