package com.example.practo.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.example.practo.Adapters.HealthArticleRecylerAdapter
import com.example.practo.Adapters.MedicineOrderListRecyclerAdapter
import com.example.practo.InterfaceListeners.OnPlaceMedicineOrderListener
import com.example.practo.Model.ArticlesSupplier
import com.example.practo.Model.MedicineOrderSupplier

import com.example.practo.R
import kotlinx.android.synthetic.main.activity_main.view.*


class MedicineOrderFragment : Fragment() {
    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager:LinearLayoutManager
    private lateinit var recyclerViewAdapter:MedicineOrderListRecyclerAdapter
    private lateinit var placeOrderButton:Button
    private lateinit var mMedicineOrderButtonListener:OnPlaceMedicineOrderListener
    private var orderCount=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_medicine_order, container, false)
        var noOrdersPlacedLinearLayout = rootView.findViewById<LinearLayout>(R.id.no_orders_placed_linear_layout)
        var orderPlacedLinearLayout = rootView.findViewById<LinearLayout>(R.id.order_placed_linear_layout)

        if(orderCount==0){
            orderPlacedLinearLayout.visibility = View.GONE
            noOrdersPlacedLinearLayout.visibility = View.VISIBLE
        } else {
            noOrdersPlacedLinearLayout.visibility = View.GONE
            orderPlacedLinearLayout.visibility = View.VISIBLE
            initRecyclerView()
            initLayoutManager()
            bindRecyclerViewWithLayoutManager()
        }

        placeOrderButton = rootView.findViewById(R.id.place_order_button)
        placeOrderButton.setOnClickListener {
            mMedicineOrderButtonListener.onPlaceMedicineOrderButtonClicked()
        }

        return rootView
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
            MedicineOrderSupplier.orders)
        recyclerView.adapter = recyclerViewAdapter
    }


    fun setMedicineOrderButtonListener(mMedicineOrderListener: OnPlaceMedicineOrderListener){
        this.mMedicineOrderButtonListener = mMedicineOrderListener
    }


}
