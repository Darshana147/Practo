package com.example.practo.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.Adapters.MedicineOrderSummaryRecyclerAdapter
import com.example.practo.InterfaceListeners.MedicineOrderStatusListener
import com.example.practo.Model.MedicineOrder

import com.example.practo.R
import com.example.practo.UseCases.MedicineOrderUseCases
import com.example.practo.Utils.setDialogFragment
import com.vinay.stepview.models.Step
import kotlinx.android.synthetic.main.fragment_medicine_order_status.*

private const val ORDER_NUMBER = "param1"


class MedicineOrderStatusFragment : Fragment() {
    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdaptor: MedicineOrderSummaryRecyclerAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var medicineOrderUseCases:MedicineOrderUseCases
    private lateinit var mMedicineOrderStatusListener:MedicineOrderStatusListener
    private var orderNum:Int? = null
    private var medOrder: MedicineOrder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orderNum = it.getInt(ORDER_NUMBER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_medicine_order_status, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUseCases()
        initListeners()
        setAllValues()
        setStepView()
        initRecyclerView()
        initLayoutManager()
        bindRecyclerViewWithAdapter()
    }

    fun setAllValues(){
        orderNum?.let {
            order_id_order_status.text = "#${it.toString()}"
            medOrder = medicineOrderUseCases.getOrderByOrderid(it)
        }
        medOrder?.let {
            total_price_order_status.text = it.medicineCart.totalPrice.toString()
            total_quantity_order_status.text = it.medicineCart.totalNumOfItems.toString() + " Item(s)"
            type_of_address_order_status.text = it.deliveryAddressDetails.typeOfAddress
            user_name_order_status.text = it.deliveryAddressDetails.userName
            user_mobile_number_order_status.text = it.deliveryAddressDetails.userMobileNumber
            user_delivery_address_order_status.text =
                "${it.deliveryAddressDetails.userAddress}, ${it.deliveryAddressDetails.userLocality}\n${it.deliveryAddressDetails.userCity}, ${it.deliveryAddressDetails.userState}, ${it.deliveryAddressDetails.userCountry}\n${it.deliveryAddressDetails.userPinCode}"

        }

        if(medicineOrderUseCases.isMedicineDelivered(medOrder?.deliveryDate!!)){
            cancel_medicine_order_button.setText("Re-Order")
            medicineOrderUseCases.updateMedicineOrder(orderNum!!,"yes")
        } else {
            cancel_medicine_order_button.setText("Cancel Order")
        }
    }

    fun initUseCases(){
        medicineOrderUseCases = MedicineOrderUseCases(context!!)
    }

    fun initListeners(){
        cancel_medicine_order_button.setOnClickListener {
            context!!.setDialogFragment(this,fragmentManager,"Cancel_Order_Req_Dialog",CancelOrderCustomDialog())
        }

        swipe_refresh_order_status.setOnRefreshListener {
            reloadFragment()
            swipe_refresh_order_status.isRefreshing = false
        }
    }

    fun reloadFragment(){
        mMedicineOrderStatusListener.onPageRefreshed()
    }


    fun setStepView() {

        val stepList = ArrayList<Step>()
        stepList.add(Step("Ordered date ${medOrder?.orderedDate}", Step.State.COMPLETED))
        stepList.add(Step("Shipping", Step.State.CURRENT))
        stepList.add(Step("Out for delivery"))
        stepList.add(Step("Arriving By ${medOrder?.deliveryDate}"))

        if (medicineOrderUseCases.isShipped(medOrder?.orderedDate!!)) {
            stepList.get(1).state = Step.State.COMPLETED
            stepList.get(2).state = Step.State.CURRENT
        } else if (medicineOrderUseCases.isOutForDelivery(medOrder?.orderedDate!!, medOrder?.deliveryDate!!)) {
            stepList.get(1).state = Step.State.COMPLETED
            stepList.get(2).state = Step.State.COMPLETED
            stepList.get(3).state = Step.State.CURRENT

        } else if (medicineOrderUseCases.isMedicineDelivered(medOrder?.deliveryDate!!)) {
            stepList.get(1).state = Step.State.COMPLETED
            stepList.get(2).state = Step.State.COMPLETED
            stepList.get(3).state = Step.State.COMPLETED
        }

        vertical_step_view.setSteps(stepList)
            .setReverse(false)
            .setCompletedStepIcon(ResourcesCompat.getDrawable(resources, R.drawable.checked, null)!!)
            .setNotCompletedLineColor(ContextCompat.getColor(context!!, R.color.gray))
            .setCompletedLineColor(ContextCompat.getColor(context!!, R.color.design_default_color_primary))
            .setCompletedStepTextColor(ContextCompat.getColor(context!!, R.color.gray))
            .setCurrentStepTextColor(ContextCompat.getColor(context!!, R.color.design_default_color_primary))
            .setCurrentStepIcon(ResourcesCompat.getDrawable(resources, R.drawable.ic_current_state, null)!!)
            .setNotCompletedStepIcon(ResourcesCompat.getDrawable(resources, R.drawable.ic_not_completed_state, null)!!)
            .setNotCompletedStepTextColor(ContextCompat.getColor(context!!, R.color.gray))

    }

    fun initRecyclerView(){
        recyclerView = rootView.findViewById<RecyclerView>(R.id.medicine_order_status_recycler_view)
        recyclerView.setHasFixedSize(true);
    }

    fun initLayoutManager(){
        layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
    }

    fun bindRecyclerViewWithAdapter(){
        recyclerView.layoutManager = layoutManager
        orderNum?.let{
            val medOrderItems = medicineOrderUseCases.getOrderedItemsByOrderId(it)

                recyclerViewAdaptor = MedicineOrderSummaryRecyclerAdapter(this.context!!,medOrderItems)
        }
        recyclerView.adapter = recyclerViewAdaptor
    }

    fun  setMedicineOrderStatusListener(mMedicineOrderStatusListener: MedicineOrderStatusListener){
        this.mMedicineOrderStatusListener = mMedicineOrderStatusListener
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            MedicineOrderStatusFragment().apply {
                arguments = Bundle().apply {
                    putInt(ORDER_NUMBER, param1)
                }
            }
    }


}
