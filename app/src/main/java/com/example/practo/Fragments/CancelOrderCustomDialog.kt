package com.example.practo.Fragments


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.practo.R
import kotlinx.android.synthetic.main.fragment_cancel_order_custom_dialog.*


class CancelOrderCustomDialog : DialogFragment() {
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cancel_order_custom_dialog, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()

    }

    fun setListeners(){
        dialog_no_txv.setOnClickListener {
            dialog.dismiss()
        }

        dialog_yes_cancel_txv.setOnClickListener {
            dialog.dismiss()

        }
    }


}
