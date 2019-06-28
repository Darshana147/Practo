package com.example.practo.Fragments


import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.Adapters.SliderAdapter
import com.example.practo.InterfaceListeners.HomePageListener


import com.example.practo.R
import com.example.practo.Utils.toast
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private lateinit var rootView:View
    private lateinit var viewPager: ViewPager
    private lateinit var indicator: TabLayout
    private lateinit var textList:ArrayList<String>
    private lateinit var imageList:ArrayList<Int>
    private lateinit var handler:Handler
    private lateinit var doctorCardView:CardView
    private lateinit var pharmacyCardView: CardView
    private lateinit var chatCardView: CardView
    private lateinit var diagnosticCardView: CardView
    private lateinit var mHomePageListener: HomePageListener



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_home, container, false)
        initViews()
        customizeToolbar()
        handler = Handler()
        setSlider()
        setListeners()
        return rootView
    }

    fun customizeToolbar() {
        val activity = getActivity() as AppCompatActivity
        val actionBarSupport = activity.supportActionBar
        actionBarSupport?.setTitle("Practo")
    }


    fun setSlider(){

        textList = arrayListOf("Book an appointment with the right doctor","Too busy to see a doctor? Chat online instead.","Get medicines delivered at door step.")
        imageList= arrayListOf(R.drawable.image1,R.drawable.image3,R.drawable.image2)
        viewPager.adapter = SliderAdapter(this.context!!,textList,imageList)
        indicator.setupWithViewPager(viewPager,true)
        val timer = Timer()
        timer.scheduleAtFixedRate(SliderTimer(),3000,5000)

    }

    inner class SliderTimer:TimerTask(){

        override fun run() {
            handler.postDelayed(object:Runnable{
                override fun run() {
                    if(viewPager.currentItem<textList.size-1){
                        viewPager.setCurrentItem(viewPager.currentItem+1)
                    } else {
                        viewPager.setCurrentItem(0)
                    }
                }

            },4000)
        }

    }

    fun initViews(){
        viewPager = rootView.findViewById(R.id.viewPager)
        indicator = rootView.findViewById(R.id.indicator)
        doctorCardView = rootView.findViewById(R.id.doctor_card_view)
        chatCardView = rootView.findViewById(R.id.chat_card_view)
        pharmacyCardView = rootView.findViewById(R.id.pharmacy_card_view)
        diagnosticCardView = rootView.findViewById(R.id.diagnostic_card_view)
    }

    fun setListeners(){
        doctorCardView.setOnClickListener {
            mHomePageListener.onDoctorClicked()
        }

        chatCardView.setOnClickListener {
//            Toast.makeText(context,"Chat Clicked",Toast.LENGTH_SHORT).show()
            context?.toast("Chat Clicked")
        }

        pharmacyCardView.setOnClickListener {
            mHomePageListener.onPharmacyClicked()
        }

        diagnosticCardView.setOnClickListener {
//            Toast.makeText(context,"Diagnostic Clicked",Toast.LENGTH_SHORT).show()
            context?.toast("Diagnostic Clicked")
        }
    }

    fun setPharmacyListener(mHomePageListener: HomePageListener){
        this.mHomePageListener = mHomePageListener
    }

}
