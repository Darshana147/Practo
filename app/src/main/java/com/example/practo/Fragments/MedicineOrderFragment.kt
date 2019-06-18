package com.example.practo.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.example.practo.Adapters.MedicineOrderListRecyclerAdapter
import com.example.practo.InterfaceListeners.MedicineOrderListRecyclerViewListener
import com.example.practo.InterfaceListeners.OnMedicineOrderListener

import com.example.practo.R
import com.example.practo.UseCases.MedicineOrderUseCases


class MedicineOrderFragment : Fragment(),MedicineOrderListRecyclerViewListener{

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager:LinearLayoutManager
    private lateinit var recyclerViewAdapter:MedicineOrderListRecyclerAdapter
    private lateinit var orderMedicines:Button
    private lateinit var mMedicineOrderListener:OnMedicineOrderListener
    private lateinit var medicineOrderUsecases:MedicineOrderUseCases

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_medicine_order, container, false)
        customizeToolbar()
        initUseCases()
        val noOrdersPlacedLinearLayout = rootView.findViewById<LinearLayout>(R.id.no_orders_placed_linear_layout)
        val orderPlacedLinearLayout = rootView.findViewById<LinearLayout>(R.id.order_placed_linear_layout)

        if(medicineOrderUsecases.getAllOrders().isEmpty()){
            orderPlacedLinearLayout.visibility = View.GONE
            noOrdersPlacedLinearLayout.visibility = View.VISIBLE
        } else {
            noOrdersPlacedLinearLayout.visibility = View.GONE
            orderPlacedLinearLayout.visibility = View.VISIBLE
            initRecyclerView()
            initLayoutManager()
            bindRecyclerViewWithLayoutManager()
        }

        orderMedicines = rootView.findViewById(R.id.order_medicines_button)
        orderMedicines.setOnClickListener {
            mMedicineOrderListener.onOrderMedicinesButtonClicked()
        }

        return rootView
    }

    fun initUseCases(){
        medicineOrderUsecases = MedicineOrderUseCases(context!!)
    }

    fun customizeToolbar(){
        val activity = getActivity() as AppCompatActivity
        val actionBarSupport = activity.supportActionBar
        actionBarSupport?.setTitle("My Orders")

    }

    fun initRecyclerView(){
        recyclerView = rootView.findViewById<RecyclerView>(R.id.medicine_order_recycler_view)
        recyclerView.setHasFixedSize(true);
    }

    fun initLayoutManager(){
        layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
    }

    fun bindRecyclerViewWithLayoutManager(){
        recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = MedicineOrderListRecyclerAdapter(this.context!!,
            medicineOrderUsecases.getAllOrders(),this)
        recyclerView.adapter = recyclerViewAdapter
    }


    fun setMedicineOrderButtonListener(mMedicineOrderListener: OnMedicineOrderListener){
        this.mMedicineOrderListener = mMedicineOrderListener
    }

    override fun onMedicineOrderItemClicked(orderNum: Int) {
        mMedicineOrderListener.onMedicineOrderItemClicked(orderNum)
    }


}
