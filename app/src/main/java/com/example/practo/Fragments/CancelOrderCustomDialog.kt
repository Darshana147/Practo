package com.example.practo.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.practo.R
import kotlinx.android.synthetic.main.fragment_cancel_order_custom_dialog.*
import java.lang.ClassCastException


class CancelOrderCustomDialog : DialogFragment() {
    interface CancelOrderCustomDialogListener{
        fun onOrderCanceled()
    }

    private lateinit var rootView: View
    private lateinit var onOrderCanceledListener: CancelOrderCustomDialogListener

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
            onOrderCanceledListener.onOrderCanceled()
            dialog.dismiss()

        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            onOrderCanceledListener = targetFragment as CancelOrderCustomDialogListener
        } catch (e: ClassCastException) {
            Log.e("AddToCartDialogFragment", "msg: ClassCastException ${e.message}")
        }
    }


}
