package com.example.practo.Adapters

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.practo.R

class SliderAdapter(var context: Context,var textList:ArrayList<String>,var imageList:ArrayList<Int>):PagerAdapter() {
    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0==p1
    }

    override fun getCount(): Int {
        return textList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_slider,null)
        val imgView = view.findViewById<ImageView>(R.id.slider_imgView)
        imgView.setImageResource(imageList[position])
        val textView = view.findViewById<TextView>(R.id.slider_txv)
        textView.text=textList[position]
        val viewPager =container
        viewPager.addView(view,0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val viewPager = container
        val view = `object`as View
        viewPager.removeView(view)
    }
}