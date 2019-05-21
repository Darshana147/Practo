package com.example.practo.Adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.practo.Fragments.HealthArticlesFragment
import com.example.practo.InterfaceListeners.OnHealthArticleSelectedListener
import com.example.practo.Model.HealthArticle
import com.example.practo.R
import kotlinx.android.synthetic.main.health_article_list_card_layout.view.*


class HealthArticleRecylerAdapter(
    val context: Context,
    val healthArticles:List<HealthArticle>,
    val listener: OnHealthArticleSelectedListener
) : RecyclerView.Adapter<HealthArticleRecylerAdapter.MyViewHolder>(){

    var articles:MutableList<HealthArticle> = healthArticles as MutableList<HealthArticle>

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
      val view:View = LayoutInflater.from(context).inflate(R.layout.health_article_list_card_layout,p0,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val article = articles.get(p1)
        p0.setData(article,p1)
    }

    fun filterList(filteredList: MutableList<HealthArticle>){
        this.articles = filteredList
        notifyDataSetChanged()
    }


    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun setData(article:HealthArticle,position:Int){
            itemView.health_article_title_txv.text = article.articleTitle
            itemView.health_article_desc_txv.text = article.articleDescription
            itemView.doctor_details_txv.text = article.doctorName+", "+article.doctorSpecialization
        }

        init {
            itemView.share_health_article_imv.setOnClickListener {

                var message="Article Title: ${articles.get(adapterPosition).articleTitle} \nArticle Description: ${articles.get(adapterPosition).articleDescription}"
                Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
                shareArticleIntent(message)
            }

            itemView.article_background.setOnClickListener {
                listener.onHealthArticleClicked(adapterPosition)
            }
        }

        fun shareArticleIntent(message:String){
            val intentToShare: Intent = Intent()
            intentToShare.action = Intent.ACTION_SEND
            intentToShare.putExtra(Intent.EXTRA_TEXT, message)
            intentToShare.type = "text/plain"

            context!!.startActivity(Intent.createChooser(intentToShare, "Please select app: "))
        }
    }
}