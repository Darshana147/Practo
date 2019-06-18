package com.example.practo.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.InterfaceListeners.OrderPlacementSuccessFragmentListener
import com.example.practo.Model.Medicine
import com.example.practo.Model.MedicineOrder

import com.example.practo.R
import com.example.practo.UseCases.MedicineCartUseCases
import com.example.practo.UseCases.MedicineOrderUseCases
import com.example.practo.UseCases.UserDeliveryAddressDetailsUseCases
import kotlinx.android.synthetic.main.fragment_medicine_order_placement.*
import kotlinx.android.synthetic.main.fragment_order_placement_success.*

private const val ORDER_NUMBER = "param1"

class OrderPlacementSuccessFragment : Fragment() {
    private lateinit var rootView: View
    private lateinit var mOrderPlacementSuccessFragmentListener: OrderPlacementSuccessFragmentListener
    private lateinit var medicineCartUseCases: MedicineCartUseCases
    private lateinit var medicineOrderUseCases:MedicineOrderUseCases
    private var orderNum:Int? = null

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
        rootView =  inflater.inflate(R.layout.fragment_order_placement_success, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        customizeToolbar()
        initUseCases()
        setListeners()
        setValues()
    }

    fun customizeToolbar(){
        val activity = getActivity() as AppCompatActivity
        val actionBarSupport = activity.supportActionBar
        actionBarSupport?.setTitle("Order Status")
    }

    fun setOrderPlacementSuccessFragmentListener(mOrderPlacementSuccessFragmentListener: OrderPlacementSuccessFragmentListener){
        this.mOrderPlacementSuccessFragmentListener = mOrderPlacementSuccessFragmentListener
    }

    fun setListeners(){
        view_my_orders_button.setOnClickListener {
            mOrderPlacementSuccessFragmentListener.onViewMyOrdersButtonClicked()
        }
    }

    fun setValues(){  //to-do
        var medOrder: MedicineOrder? = null

        orderNum?.let {
            medOrder = medicineOrderUseCases.getOrderByOrderid(it)
        }
        medOrder?.let {
            order_success_total_amount.text = (Math.round(it.medicineCart.totalPrice * 100.0) / 100.0).toString()
            val userDeliveryAddressDetails = it.deliveryAddressDetails
            user_name_order_success.text = userDeliveryAddressDetails.userName
            user_delivery_address_order_success.text =
                "${userDeliveryAddressDetails.userAddress}, ${userDeliveryAddressDetails.userLocality}\n${userDeliveryAddressDetails.userCity}, ${userDeliveryAddressDetails.userState}, ${userDeliveryAddressDetails.userCountry}\n${userDeliveryAddressDetails.userPinCode}"
            user_mobile_number_order_success.text = userDeliveryAddressDetails.userMobileNumber

            delivery_date_order_placement_success.text = it.deliveryDate
        }

    }

    fun initUseCases(){
        medicineCartUseCases = MedicineCartUseCases(context!!)
        medicineOrderUseCases = MedicineOrderUseCases(context!!)
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            OrderPlacementSuccessFragment().apply {
                arguments = Bundle().apply {
                    putInt(ORDER_NUMBER, param1)
                }
            }
    }

}
