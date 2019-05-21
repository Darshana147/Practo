package com.example.practo.Fragments


import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.Adapters.SliderAdapter


import com.example.practo.R
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private lateinit var rootView:View
    private lateinit var viewPager: ViewPager
    private lateinit var indicator: TabLayout
    private lateinit var textList:ArrayList<String>
    private lateinit var imageList:ArrayList<Int>
    private lateinit var handler:Handler



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_home, container, false)
        viewPager = rootView.findViewById(R.id.viewPager)
        indicator = rootView.findViewById(R.id.indicator)
        handler = Handler()
        setSlider()
        return rootView
    }


    fun setSlider(){

        textList = arrayListOf("Book an appointment with the right doctor","Too busy to see a doctor? Chat online instead.","Get medicines delivered at door step.")
        imageList= arrayListOf(R.drawable.image1,R.drawable.image3,R.drawable.image2)
        viewPager.adapter = SliderAdapter(this.context!!,textList,imageList)
        indicator.setupWithViewPager(viewPager,true)
        var timer = Timer()
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

            },5000)
        }

    }

}
