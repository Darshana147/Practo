package com.example.practo.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.example.practo.Fragments.DoctorListFragment
import com.example.practo.Fragments.MyDoctorsFragment
import com.example.practo.Fragments.SearchDoctorsFragment
import com.example.practo.InterfaceListeners.MyDoctorsListener
import com.example.practo.InterfaceListeners.SearchDoctorFragmentListener
import com.example.practo.R
import kotlinx.android.synthetic.main.activity_my_doctors.*

class MyDoctorsActivity : AppCompatActivity(), MyDoctorsListener, SearchDoctorFragmentListener{

    private lateinit var myDoctorsFragment: MyDoctorsFragment
    private lateinit var searchDoctorsFragment: SearchDoctorsFragment
    private lateinit var doctorListFragment:DoctorListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_doctors)
        initFragments()
        getIntentDetails()
        setFragmentListeners()
        customizeToolbar()
    }


    fun initFragments(){
        myDoctorsFragment = MyDoctorsFragment()
        searchDoctorsFragment = SearchDoctorsFragment()
        doctorListFragment = DoctorListFragment()
    }

    fun setFragmentListeners(){
        myDoctorsFragment.setMyDoctorListener(this)
        searchDoctorsFragment.setSearchDoctorFragmentListener(this)
    }


    fun setFragmentTransition(fragment: Fragment, tag:String) {
        supportFragmentManager.beginTransaction().replace(R.id.my_doctors_fragment_container,fragment,tag).commit()
    }

    fun setFragmentTransitionWithAddToBackStack(fragment: Fragment,key:String) {
        supportFragmentManager.beginTransaction().replace(R.id.my_doctors_fragment_container, fragment,key)
            .addToBackStack(key).commit()
    }


    fun getIntentDetails(){
        val intent = intent
        if (intent.hasExtra("fragment")) {
            val fragment = intent?.extras?.getString("fragment")
            when (fragment) {
//                "searchMedicineFragment" -> {
//                    setFragmentTransition(searchMedicinePagerFragment,"search_medicine_pager_frag")
//                }
            }

        } else {
            setFragmentTransition(myDoctorsFragment,"my_doctors_frag")
        }
    }

    fun customizeToolbar() {
        setSupportActionBar(activity_my_doctors_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return false
    }


    override fun onSearchDoctorsClicked() {
        setFragmentTransitionWithAddToBackStack(searchDoctorsFragment,searchDoctorsFragment.Tag_SEARCH_DOCTORS)
    }

    override fun onSpecializationFieldSelected(specialization:String) {
        setFragmentTransitionWithAddToBackStack(doctorListFragment,doctorListFragment.TAG_DOCTOR_LIST_FRAGMENT)
    }


}