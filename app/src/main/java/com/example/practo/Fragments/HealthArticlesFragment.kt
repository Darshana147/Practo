package com.example.practo.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.practo.Adapters.HealthArticleRecylerAdapter
import com.example.practo.InterfaceListeners.OnHealthArticleSelectedListener
import com.example.practo.InterfaceListeners.OnHealthArticlesFragmentListener
import com.example.practo.Model.ArticlesSupplier
import com.example.practo.Model.HealthArticle
import com.example.practo.R


class HealthArticlesFragment : Fragment(),OnHealthArticleSelectedListener{

    private lateinit var rootView: View
    private lateinit var recyclerView:RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerViewAdapter: HealthArticleRecylerAdapter
    private lateinit var searchFilter:EditText
    private lateinit var mHealthArticleFragmentListener:OnHealthArticlesFragmentListener

    //for childFragment listener
    fun setHealthArticleFragmentListener(mHealthArticleFragmentListener: OnHealthArticlesFragmentListener){
        this.mHealthArticleFragmentListener=mHealthArticleFragmentListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_health_articles, container, false)
        initRecyclerView()
        initLayoutManager()
        bindRecyclerViewWithAdapter()
        applySearchFilter()
        return rootView
    }

    fun initRecyclerView(){
        recyclerView = rootView.findViewById<RecyclerView>(R.id.health_articles_recycler_view)
        recyclerView.setHasFixedSize(true);
    }

    fun initLayoutManager(){
        layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
    }

    fun bindRecyclerViewWithAdapter(){
        recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = HealthArticleRecylerAdapter(this.context!!,
            ArticlesSupplier.articles,this)
        recyclerView.adapter = recyclerViewAdapter
    }

    fun applySearchFilter(){
        searchFilter = rootView.findViewById(R.id.search_edit_text) as EditText
        searchFilter.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    fun filter(text:String){
        var filteredList:MutableList<HealthArticle> = mutableListOf()

        for(a in ArticlesSupplier.articles){
            if(a.articleTitle.toLowerCase().contains(text.toLowerCase().trim())||(
                        a.articleDescription.toLowerCase().contains(text.toLowerCase().trim())
                        )){
                filteredList.add(a)
            }
        }
        recyclerViewAdapter.filterList(filteredList)
    }

    override fun onHealthArticleClicked(adapterPosition:Int) {
        mHealthArticleFragmentListener?.let {
            it.onHealthArticleListener(adapterPosition)
        }
    }

}
