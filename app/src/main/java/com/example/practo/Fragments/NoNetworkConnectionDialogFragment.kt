package com.example.practo.Fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import com.example.practo.R



class NoNetworkConnectionDialogFragment : DialogFragment() {

    private lateinit var rootView:View
    private lateinit var okBtn:TextView
    private lateinit var cancelBtn:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_no_network_connection_dialog, container, false)
        initViews()
        initListeners()
        return rootView
    }

    fun initViews(){
        okBtn = rootView.findViewById(R.id.dialog_ok_btn)
        cancelBtn = rootView.findViewById(R.id.dialog_cancel_btn)
    }

    fun initListeners(){
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        okBtn.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS)
            targetFragment!!.startActivity(intent)
        }
    }


}
