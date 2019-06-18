package com.example.practo.Fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.Model.Medicine
import com.example.practo.Model.MedicineDescription
import com.example.practo.R
import kotlinx.android.synthetic.main.medicine_description_layout.*


class MedicineDescriptionCustomDialog:DialogFragment() {
    private lateinit var rootView:View
    private var medicine: Medicine?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.medicine_description_layout,container)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            medicine?.let{
            med_name_txv.text = it.medicineName
            med_company_name_txv.text = it.medicineDetailedDescription.company
            med_ingredient_txv.text = it.medicineDetailedDescription.ingredient
            med_description_txv.text = it.medicineDetailedDescription.medicineDescription
            med_side_effects_txv.text = it.medicineDetailedDescription.medicineSideEffects
            med_prescribed_for_txv.text = it.medicineDetailedDescription.prescribedFor
            it.medicineDetailedDescription.dosage?.let{
                missed_dose_txv.text = it.missedDose
                over_dose_txv.text = it.overDose
            }?:run{
                dosage_linear_layout.visibility = View.GONE
            }
            med_general_instructions.text = it.medicineDetailedDescription.generalInstructions

        }
        close_dialog.setOnClickListener {
            dialog.dismiss()
        }

    }


    companion object {
        @JvmStatic
        fun newInstance(medicine: Medicine?) =
            MedicineDescriptionCustomDialog().apply {
                this.medicine = medicine
            }
    }
}