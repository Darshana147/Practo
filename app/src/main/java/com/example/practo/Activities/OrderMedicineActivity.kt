package com.example.practo.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.MenuItem
import com.example.practo.Fragments.*
import com.example.practo.R

import com.example.practo.InterfaceListeners.FavoriteMedicinesFragmentListener
import com.example.practo.InterfaceListeners.OnPlaceMedicineOrderListener
import com.example.practo.InterfaceListeners.OnSearchFragmentToolbarMenuListener
import com.example.practo.InterfaceListeners.ViewCartFragmentListener
import kotlinx.android.synthetic.main.activity_order_medicine.*


class OrderMedicineActivity : AppCompatActivity(), OnPlaceMedicineOrderListener, OnSearchFragmentToolbarMenuListener,
    ViewCartFragmentListener,FavoriteMedicinesFragmentListener{

    private lateinit var medicineOrderFragment: MedicineOrderFragment
    private lateinit var searchMedicinesFragment: SearchMedicinesFragment
    private lateinit var viewCartFragment: ViewCartFragment
    private lateinit var favoriteListFragment: FavoriteMedicineListFragment
    private lateinit var userDeliveryAddressFragment:UserDeliveryAddressFragment

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
                    setFragmentTransition(searchMedicinesFragment)
                }
            }

        }
    }

    fun setFragmentListeners() {
        medicineOrderFragment.setMedicineOrderButtonListener(this)
        searchMedicinesFragment.setSearchFragmentToolbarMenuListener(this)
        viewCartFragment.setViewCartFragmentListener(this)
        favoriteListFragment.setFavoriteMedicinesFragmentListener(this)
    }

    fun initFragments() {
        medicineOrderFragment = MedicineOrderFragment()
        searchMedicinesFragment = SearchMedicinesFragment()
        viewCartFragment = ViewCartFragment()
        favoriteListFragment = FavoriteMedicineListFragment()
        userDeliveryAddressFragment = UserDeliveryAddressFragment()
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
        setFragmentTransition(searchMedicinesFragment)
    }

    override fun onViewCartClicked() {
       setFragmentTransitionWithAddToBackStack(viewCartFragment,"cart_frag")

    }

    override fun onWishListClicked() {
        setFragmentTransitionWithAddToBackStack(favoriteListFragment,"fav_frag")
    }

    override fun onAddMedicinesBtnFromEmptyCartClicked() {
        supportFragmentManager.popBackStack("cart_frag",1)
        setFragmentTransition(searchMedicinesFragment)
    }

    override fun onAddMedicinesBtnFromEmptyFavListClicked() {
       supportFragmentManager.popBackStack("fav_frag",1)
        setFragmentTransition(searchMedicinesFragment)
    }

    override fun onCheckOutBtnClicked() {
       setFragmentTransitionWithAddToBackStack(userDeliveryAddressFragment,"user_delivery_address_frag")
    }

}
