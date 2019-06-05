package com.example.practo.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import android.widget.Toast
import com.example.practo.Fragments.*
import com.example.practo.InterfaceListeners.*
import com.example.practo.Model.UserDeliveryAddressStorage
import com.example.practo.Model.UserMedicineDeliveryAddressDetails
import com.example.practo.R

import kotlinx.android.synthetic.main.activity_order_medicine.*


class OrderMedicineActivity : AppCompatActivity(), OnPlaceMedicineOrderListener, OnSearchFragmentToolbarMenuListener,
    ViewCartFragmentListener,UserDeliveryAddressFragmentListener,UserSavedDeliveryAddressesFragmentListener{

    private lateinit var medicineOrderFragment: MedicineOrderFragment
    private lateinit var viewCartFragment: ViewCartFragment
    private lateinit var medicineOrderPlacementFragment:MedicineOrderPlacementFragment
    private lateinit var userDeliveryAddressFragment:UserDeliveryAddressFragment
    private lateinit var userSavedDeliveryAddressesFragment: UserSavedDeliveryAddressesFragment
    private lateinit var searchMedicinePagerFragment: SearchMedicinePagerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_medicine)
        initFragments()
        setFragmentListeners()
        setFragmentTransition(medicineOrderFragment)
        getIntentDetails()
        customizeToolbar()
    }

    fun getIntentDetails(){
        var intent = intent
        if (intent.hasExtra("fragment")) {
            var fragment = intent.extras.getString("fragment")
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
        //favoriteListFragment.setFavoriteMedicinesFragmentListener(this)
        userDeliveryAddressFragment.setUserDeliveryAddressFragmentListener(this)
        userSavedDeliveryAddressesFragment.setUserSavedDeliveryAddressFragmentListener(this)
    }

    fun initFragments() {
        searchMedicinePagerFragment= SearchMedicinePagerFragment()
        medicineOrderFragment = MedicineOrderFragment()
        //searchMedicinesFragment = SearchMedicinesFragment()
        viewCartFragment = ViewCartFragment()
        //favoriteListFragment = FavoriteMedicineListFragment()
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
        supportFragmentManager.beginTransaction().replace(R.id.order_medicine_fragment_container, fragment)
            .addToBackStack(key).commit()
    }

    override fun onPlaceMedicineOrderButtonClicked() {
        setFragmentTransition(searchMedicinePagerFragment)
    }

    override fun onViewCartClicked() {
       setFragmentTransitionWithAddToBackStack(viewCartFragment,"cart_frag")

    }


    override fun onAddMedicinesBtnFromEmptyCartClicked() {
        supportFragmentManager.popBackStack("cart_frag",1)
          setFragmentTransition(searchMedicinePagerFragment)
    }


    override fun onCheckOutBtnClicked() {
        if(UserDeliveryAddressStorage.userDeliveryAddress.isEmpty()){
            Toast.makeText(this,"empty saved address list",Toast.LENGTH_SHORT).show()
            setFragmentTransitionWithAddToBackStack(userDeliveryAddressFragment,"user_delivery_address_frag")
       }else {
            setFragmentTransitionWithAddToBackStack(userSavedDeliveryAddressesFragment,"user_saved_delivery_addresses_frag")
        }
    }

    override fun onSaveButtonClicked() {
        //setFragmentTransition(userSavedDeliveryAddressesFragment)
        //setFragmentTransitionWithAddToBackStack(userSavedDeliveryAddressesFragment,"user_saved_delivery_addresses_frag")
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

}
