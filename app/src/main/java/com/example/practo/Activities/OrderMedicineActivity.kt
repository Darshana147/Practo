package com.example.practo.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.practo.R

import android.view.ViewStub
import android.widget.Toast
import com.example.practo.Fragments.MedicineOrderFragment
import com.example.practo.Fragments.SearchMedicinesFragment
import com.example.practo.Fragments.ViewCartFragment
import com.example.practo.InterfaceListeners.OnPlaceMedicineOrderListener
import com.example.practo.InterfaceListeners.OnViewCartListener
import com.example.practo.InterfaceListeners.SearchMedicinesFragmentListener
import com.example.practo.Model.Medicine
import kotlinx.android.synthetic.main.activity_order_medicine.*


class OrderMedicineActivity : AppCompatActivity(),OnPlaceMedicineOrderListener,OnViewCartListener,SearchMedicinesFragmentListener{

    private lateinit var medicineOrderFragment: MedicineOrderFragment
    private lateinit var searchMedicinesFragment: SearchMedicinesFragment
    private lateinit var viewCartFragment: ViewCartFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_medicine)
        initFragments()
        setFragmentListeners()
        setFragmentTransition(medicineOrderFragment)
        customizeToolbar()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "onResumeOrderMedicine", Toast.LENGTH_SHORT).show()
        var intent = intent
        if(intent.hasExtra("fragment")) {
            var fragment = intent.extras.getString("fragment")
        when(fragment){
            "searchMedicineFragment" -> {
                setFragmentTransition(searchMedicinesFragment)
            }
        }

        }

    }

    fun setFragmentListeners(){
        medicineOrderFragment.setMedicineOrderButtonListener(this)
        searchMedicinesFragment.setViewCartListener(this)
        searchMedicinesFragment.setSearchMedicinesFragmentListener(this)
    }

    fun initFragments(){
        medicineOrderFragment = MedicineOrderFragment()
        searchMedicinesFragment = SearchMedicinesFragment()
        viewCartFragment = ViewCartFragment()
    }

    fun customizeToolbar(){
        setSupportActionBar(activity_order_medicine_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> onBackPressed()
        }
        return false
    }

    fun setFragmentTransition(fragment:Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.order_medicine_fragment_container,fragment).commit()
    }

    fun setFragmentTransitionWithAddToBackStack(fragment:Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.order_medicine_fragment_container,fragment).addToBackStack(null).commit()
    }

    override fun onPlaceMedicineOrderButtonClicked() {
        setFragmentTransition(searchMedicinesFragment)
    }

    override fun onViewCartClicked() {
        setFragmentTransitionWithAddToBackStack(viewCartFragment)
    }

    override fun onAddToCartFromSearchMedicinesListener(medicine:Medicine,qty:Int) {
        Toast.makeText(applicationContext,"Item Added To Cart",Toast.LENGTH_SHORT).show()
        viewCartFragment.addItemToCart(medicine,qty)
    }



}
