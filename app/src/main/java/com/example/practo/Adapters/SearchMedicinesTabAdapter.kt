package com.example.practo.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SearchMedicinesTabAdapter(fragmentManager: FragmentManager):FragmentPagerAdapter(fragmentManager) {
    private var fragmentsList= arrayListOf<Fragment>()
    private var tabTitleList = arrayListOf<String>()

    override fun getItem(p0: Int): Fragment {
        return fragmentsList.get(p0)
    }

    override fun getCount(): Int {
        return fragmentsList.size
    }

    fun addFragments(fragment:Fragment,tabTitle:String){
        fragmentsList.add(fragment)
        tabTitleList.add(tabTitle)
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitleList.get(position)
    }

}