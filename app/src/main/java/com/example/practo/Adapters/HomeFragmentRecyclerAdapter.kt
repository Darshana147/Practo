package com.example.practo.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.R
import kotlinx.android.synthetic.main.health_article_list_card_layout.view.*
import kotlinx.android.synthetic.main.home_fragment_card_view.view.*

class HomeFragmentRecyclerAdapter(var context: Context,var textList:ArrayList<String>,var imgList:ArrayList<Int> ):RecyclerView.Adapter<HomeFragmentRecyclerAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.home_fragment_card_view,p0,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        var text:String = textList.get(p1)
        var img:Int=imgList.get(p1)
       p0.setData(p1,text,img)
    }

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        fun setData(pos:Int,text:String,img:Int){
            itemView.home_frag_image_view.setImageResource(img)
            itemView.home_frag_txv.text = text
        }
    }
}