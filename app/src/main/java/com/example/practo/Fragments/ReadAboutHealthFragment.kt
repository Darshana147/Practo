package com.example.practo.Fragments


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.practo.Adapters.ReadAboutHealthTabAdaptor
import android.widget.Toolbar
import com.example.practo.InterfaceListeners.OnHealthArticlesFragmentListener
import com.example.practo.InterfaceListeners.ReadAboutHealthFragmentListener
import com.example.practo.R


class ReadAboutHealthFragment : Fragment(),OnHealthArticlesFragmentListener{


    private lateinit var rootView:View
    private lateinit var tabLayout: TabLayout
    private lateinit var tabAdaptor: ReadAboutHealthTabAdaptor
    private lateinit var viewPager: ViewPager

    private lateinit var healthArticlesFragment:HealthArticlesFragment
    private lateinit var healthQAFragment: HealthQAFragment

    private lateinit var mReadAboutHealthFragmentListener:ReadAboutHealthFragmentListener

    fun setReadAboutHealthFragmentListener(mReadAboutHealthFragmentListener: ReadAboutHealthFragmentListener){
        this.mReadAboutHealthFragmentListener=mReadAboutHealthFragmentListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_read_about_health, container, false)

        customizeToolbar()
        initTabAdaptor()
        addFragmentsToTabs()
        bindPagerAndTabAdaptor()
        healthArticlesFragment.setHealthArticleFragmentListener(this)

        return rootView

    }

    fun customizeToolbar(){
        var activity = getActivity() as AppCompatActivity
        activity.supportActionBar?.setTitle("Read about health")
        var toolbar = rootView.findViewById<Toolbar>(R.id.activity_main_toolbar)

    }


    fun initTabAdaptor(){
        tabAdaptor = ReadAboutHealthTabAdaptor(childFragmentManager)

    }

    fun initFragments(){
        healthArticlesFragment = HealthArticlesFragment()
        healthQAFragment = HealthQAFragment()
    }

    fun addFragmentsToTabs(){
        initFragments()
        tabAdaptor.addFragments(healthArticlesFragment,"Health Articles")
        tabAdaptor.addFragments(healthQAFragment,"Health Q&A")
    }

    fun bindPagerAndTabAdaptor(){
        viewPager = rootView.findViewById<ViewPager>(R.id.readAboutHealthViewPager)
        tabLayout = rootView.findViewById<TabLayout>(R.id.readAboutHealthTabLayout)
        viewPager.adapter = tabAdaptor
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onHealthArticleListener(adapterPosition: Int) {
       mReadAboutHealthFragmentListener?.let {
           it.onReadAboutHealthFragmentListener(adapterPosition)
       }
    }


}
