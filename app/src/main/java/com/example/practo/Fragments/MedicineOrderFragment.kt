package com.example.practo.Fragments


import android.annotation.SuppressLint
import android.os.AsyncTask
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
import com.example.practo.Adapters.MedicineOrderListSectionedRecyclerAdapter
import com.example.practo.InterfaceListeners.OnMedicineOrderListener
import com.example.practo.Model.MedicineOrder
import com.example.practo.Model.SectionModel
import com.example.practo.R
import com.example.practo.UseCases.MedicineOrderUseCases
import kotlinx.android.synthetic.main.fragment_medicine_order.view.*


class MedicineOrderFragment : Fragment(),
    MedicineOrderListSectionedRecyclerAdapter.MedicineOrderListSectionedRecyclerViewListener {

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerViewAdapter: MedicineOrderListSectionedRecyclerAdapter
    private lateinit var orderMedicines: Button
    private lateinit var mMedicineOrderListener: OnMedicineOrderListener
    private lateinit var medicineOrderUsecases: MedicineOrderUseCases
    private var allOrders: ArrayList<MedicineOrder> = arrayListOf()
    private var sectionModelOrderList:ArrayList<SectionModel> = arrayListOf()
    var isOrderListChanged = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_medicine_order, container, false)
        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        orderMedicines = rootView.findViewById(R.id.order_medicines_button)
        customizeToolbar()
        initUseCases()
        initListener()
        if(isOrderListChanged==true) {
            SetViewDisplay().execute(rootView)
            isOrderListChanged = false
        } else {
            viewDisplay(rootView,allOrders,sectionModelOrderList)
        }
//        viewDisplayThread(rootView)

    }

    fun viewDisplay(rootView: View,allOrders:ArrayList<MedicineOrder>,medicineOrderSectionList: ArrayList<SectionModel>) {
        if (allOrders.isEmpty()) {
            setVisibility(View.VISIBLE, View.INVISIBLE)

        } else {
            setVisibility(View.INVISIBLE, View.VISIBLE)
            Thread.sleep(100)
            initRecyclerView(rootView)
            initLayoutManager()
            activity?.runOnUiThread(object : Runnable {
                override fun run() {
                    bindRecyclerViewWithLayoutManager(medicineOrderSectionList)
                }

            })
        }
    }

    fun setVisibility(noOrderPlacedView: Int, orderPlacedView: Int) {
        activity?.runOnUiThread(object : Runnable {
            override fun run() {
                rootView.no_orders_placed_linear_layout.visibility = noOrderPlacedView
                rootView.order_placed_linear_layout.visibility = orderPlacedView
            }

        })
    }

    fun initListener() {
        rootView.medicine_order_list_recycler_view_swipe_refresh_layout.setOnRefreshListener {
            reloadFragment()
            rootView.medicine_order_list_recycler_view_swipe_refresh_layout.isRefreshing = false
        }
        orderMedicines.setOnClickListener {
            mMedicineOrderListener.onOrderMedicinesButtonClicked()
        }
    }

    fun reloadFragment() {
        mMedicineOrderListener.onMedicineOrderListPageRefreshed()
    }

    fun initUseCases() {
        medicineOrderUsecases = MedicineOrderUseCases(context!!)
    }

    fun customizeToolbar() {
        val activity = getActivity() as AppCompatActivity
        val actionBarSupport = activity.supportActionBar
        actionBarSupport?.setTitle("My Orders")
    }

    fun initRecyclerView(rootView: View) {
        recyclerView = rootView.findViewById<RecyclerView>(R.id.medicine_order_recycler_view)
        recyclerView.setHasFixedSize(true);
    }

    fun initLayoutManager() {
        layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
    }

    fun bindRecyclerViewWithLayoutManager(medicineOrderSectionList: ArrayList<SectionModel>) {
        recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = MedicineOrderListSectionedRecyclerAdapter(context!!, medicineOrderSectionList, this)
        recyclerView.adapter = recyclerViewAdapter

    }

    fun getMedicineSectionModel(medOrders: ArrayList<MedicineOrder>): ArrayList<SectionModel> {
        val medicineOrdersSectionModelList: ArrayList<SectionModel> = arrayListOf()
        val deliveredOrders = medicineOrderUsecases.getDeliveredOrders(medOrders)
        val currentOrders = medicineOrderUsecases.getCurrentOrders(medOrders)
        if (!currentOrders.isEmpty()) {
            medicineOrdersSectionModelList.add(SectionModel("CURRENT ORDERS", currentOrders))
        }
        if (!deliveredOrders.isEmpty()) {
            medicineOrdersSectionModelList.add(SectionModel("DELIVERED ORDERS", deliveredOrders))
        }
        return medicineOrdersSectionModelList
    }


    fun setMedicineOrderButtonListener(mMedicineOrderListener: OnMedicineOrderListener) {
        this.mMedicineOrderListener = mMedicineOrderListener
    }

    override fun onMedicineOrderFromListClicked(orderNum: Int) {
        mMedicineOrderListener.onMedicineOrderItemClicked(orderNum)
    }

    @SuppressLint("StaticFieldLeak")
    inner class SetViewDisplay : AsyncTask<View, Unit, Unit>() {
        val progressDialog = ProgressCustomDialog()
        override fun onPreExecute() {
            progressDialog.show(fragmentManager, "ProgressDialog")
            progressDialog.isCancelable = false
        }

        override fun doInBackground(vararg params: View?) {
            allOrders = medicineOrderUsecases.getAllOrders()
            sectionModelOrderList = getMedicineSectionModel(allOrders)
            viewDisplay(params.get(0)!!,allOrders,sectionModelOrderList)
        }

        override fun onPostExecute(result: Unit?) {
            progressDialog.dismiss()
        }
    }


    fun viewDisplayThread(rootView: View) {
        Thread(object : Runnable {
            override fun run() {
                viewDisplay(rootView,allOrders,sectionModelOrderList)
            }

        }).start()
    }

}
