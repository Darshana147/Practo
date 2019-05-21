package com.example.practo.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ReadAboutHealthTabAdaptor(fragmentManager:FragmentManager): FragmentPagerAdapter(fragmentManager){
    var fragmentsList= mutableListOf<Fragment>()
    var tabTitleList = mutableListOf<String>()

    fun addFragments(fragment:Fragment,tabTitle:String){
        fragmentsList.add(fragment)
        tabTitleList.add(tabTitle)
    }
    override fun getCount(): Int {
       return fragmentsList.size
    }

    override fun getItem(p0: Int): Fragment {
        return fragmentsList.get(p0)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitleList.get(position)
    }

}

