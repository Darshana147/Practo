package com.example.practo.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.MenuItem
import com.example.practo.Fragments.*
import com.example.practo.InterfaceListeners.*
import com.example.practo.Model.MedicineOrder
import com.example.practo.Model.UserMedicineDeliveryAddressDetails
import com.example.practo.R
import com.example.practo.UseCases.UserDeliveryAddressDetailsUseCases

import kotlinx.android.synthetic.main.activity_order_medicine.*


class OrderMedicineActivity : AppCompatActivity(), OnMedicineOrderListener, OnSearchFragmentToolbarMenuListener,
    ViewCartFragmentListener,UserDeliveryAddressFragmentListener,UserSavedDeliveryAddressesFragmentListener,OrderPlacementListener, OrderPlacementSuccessFragmentListener,MedicineOrderStatusListener{

    private lateinit var medicineOrderFragment: MedicineOrderFragment
    private lateinit var viewCartFragment: ViewCartFragment
    private lateinit var medicineOrderStatusFragment:MedicineOrderStatusFragment
    private lateinit var medicineOrderPlacementFragment:MedicineOrderPlacementFragment
    private lateinit var userDeliveryAddressFragment:UserDeliveryAddressFragment
    private lateinit var userSavedDeliveryAddressesFragment: UserSavedDeliveryAddressesFragment
    private lateinit var searchMedicinePagerFragment: SearchMedicinePagerFragment
    private lateinit var orderPlacementSuccessFragment:OrderPlacementSuccessFragment
    private lateinit var userMedicineDeliveryAddressUseCases:UserDeliveryAddressDetailsUseCases


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_medicine)
        initFragments()
        initUseCases()
        setFragmentListeners()
        setFragmentTransition(medicineOrderFragment)
        getIntentDetails()
        customizeToolbar()
    }

    fun initUseCases(){
        userMedicineDeliveryAddressUseCases = UserDeliveryAddressDetailsUseCases(applicationContext)
    }

    fun getIntentDetails(){
        val intent = intent
        if (intent.hasExtra("fragment")) {
            val fragment = intent?.extras?.getString("fragment")
            when (fragment) {
                "searchMedicineFragment" -> {
                    setFragmentTransition(searchMedicinePagerFragment)
                }
            }

        }
    }

    fun setFragmentListeners() {
        medicineOrderFragment.setMedicineOrderButtonListener(this)
        searchMedicinePagerFragment.setSearchFragmentToolbarMenuListener(this)
        viewCartFragment.setViewCartFragmentListener(this)
        userDeliveryAddressFragment.setUserDeliveryAddressFragmentListener(this)
        userSavedDeliveryAddressesFragment.setUserSavedDeliveryAddressFragmentListener(this)
        medicineOrderPlacementFragment.setOrderPlacementListener(this)
//        orderPlacementSuccessFragment.setOrderPlacementSuccessFragmentListener(this)
    }

    fun initFragments() {
//        orderPlacementSuccessFragment = OrderPlacementSuccessFragment.newInstance(0)
        searchMedicinePagerFragment= SearchMedicinePagerFragment()
        medicineOrderFragment = MedicineOrderFragment()
        viewCartFragment = ViewCartFragment()
        userDeliveryAddressFragment = UserDeliveryAddressFragment()
        medicineOrderPlacementFragment=MedicineOrderPlacementFragment()
        userSavedDeliveryAddressesFragment= UserSavedDeliveryAddressesFragment()
    }

    fun customizeToolbar() {
        setSupportActionBar(activity_order_medicine_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return false
    }

    fun setFragmentTransition(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.order_medicine_fragment_container, fragment).commit()
    }

    fun setFragmentTransitionWithAddToBackStack(fragment: Fragment,key:String) {
        supportFragmentManager.beginTransaction().replace(R.id.order_medicine_fragment_container, fragment,key)
            .addToBackStack(key).commit()
    }

    override fun onOrderMedicinesButtonClicked() {
//        setFragmentTransition(searchMedicinePagerFragment)
        setFragmentTransitionWithAddToBackStack(searchMedicinePagerFragment,"search_medicine_pager_frag")
    }

    override fun onViewCartClicked() {
       setFragmentTransitionWithAddToBackStack(viewCartFragment,"cart_frag")

    }


    override fun onAddMedicinesBtnFromEmptyCartClicked() {
        supportFragmentManager.popBackStack("cart_frag",1)
          setFragmentTransition(searchMedicinePagerFragment)
    }


    override fun onCheckOutBtnClicked() {
        setFragmentTransitionWithAddToBackStack(userSavedDeliveryAddressesFragment,"user_saved_delivery_addresses_frag")
        if(userMedicineDeliveryAddressUseCases.getAllAddresses().isEmpty()){
            setFragmentTransitionWithAddToBackStack(userDeliveryAddressFragment,"user_delivery_address_frag")
        }
    }

    override fun onSaveButtonClicked() {
        supportFragmentManager.popBackStack("user_delivery_address_frag",1)
    }

    override fun onAddNewAddressClicked() {
       setFragmentTransitionWithAddToBackStack(userDeliveryAddressFragment,"user_delivery_address_frag")
    }

    override fun onDeliverHereBtnClicked(addressDetails: UserMedicineDeliveryAddressDetails) {
        medicineOrderPlacementFragment.setDeliveryAddress(addressDetails)
        setFragmentTransitionWithAddToBackStack(medicineOrderPlacementFragment,"medicine_order_placement_frag")
    }


    override fun onEditAddressClicked(addressDetails: UserMedicineDeliveryAddressDetails) {
        userDeliveryAddressFragment.editAddressDetails(addressDetails)
        setFragmentTransitionWithAddToBackStack(userDeliveryAddressFragment,"user_delivery_address_frag")
    }

    override fun onPlaceOrderClicked(orderNum: Int) {
        orderPlacementSuccessFragment = OrderPlacementSuccessFragment.newInstance(orderNum)
        orderPlacementSuccessFragment.setOrderPlacementSuccessFragmentListener(this)
        if(supportFragmentManager.backStackEntryCount==4){
            clearBackStackInclusive("search_medicine_pager_frag")
            setFragmentTransition(orderPlacementSuccessFragment)
        }else {
            clearBackStackInclusive("cart_frag")
            setFragmentTransition(orderPlacementSuccessFragment)
        }
    }

    fun clearBackStackInclusive(tag:String){
        supportFragmentManager.popBackStack(tag,FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun onViewMyOrdersButtonClicked() {
        setFragmentTransition(medicineOrderFragment)
    }

    override fun onMedicineOrderItemClicked(orderNum:Int) {
        medicineOrderStatusFragment = MedicineOrderStatusFragment.newInstance(orderNum)
        medicineOrderStatusFragment.setMedicineOrderStatusListener(this)
        setFragmentTransitionWithAddToBackStack(medicineOrderStatusFragment,"med_order_status_frag")
    }

    override fun onPageRefreshed() {
        val frag = supportFragmentManager.findFragmentByTag("med_order_status_frag")
        val ft = supportFragmentManager.beginTransaction()
        frag?.let {
            ft.detach(it)
            ft.attach(it)
        }
        ft.commit()
    }


}
