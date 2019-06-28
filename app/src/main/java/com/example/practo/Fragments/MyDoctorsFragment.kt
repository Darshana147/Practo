package com.example.practo.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.InterfaceListeners.MyDoctorsListener
import com.example.practo.R
import kotlinx.android.synthetic.main.fragment_my_doctors.*



class MyDoctorsFragment : Fragment(){

    private lateinit var mMyDoctorsListener:MyDoctorsListener


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_doctors, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        customizeToolbar()
        initListeners()
    }

    fun customizeToolbar() {
        val activity = getActivity() as AppCompatActivity
        val actionBarSupport = activity.supportActionBar
        actionBarSupport?.setTitle("My Doctors")
    }

    fun initListeners(){
        search_doctors_button.setOnClickListener {
            mMyDoctorsListener.onSearchDoctorsClicked()
        }
    }


    fun setMyDoctorListener(mMyDoctorsListener: MyDoctorsListener){
        this.mMyDoctorsListener = mMyDoctorsListener
    }


}
