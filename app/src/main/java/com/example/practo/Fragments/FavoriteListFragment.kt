package com.example.practo.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.Adapters.MedicineFavoriteListRecyclerAdapter
import com.example.practo.Adapters.SearchMedicineRecyclerAdaptor
//import com.example.practo.Model.MedicineSupplier
//import com.example.practo.Model.WishListSupplier

import com.example.practo.R


class FavoriteListFragment : Fragment() {
    private lateinit var rootView:View
    private lateinit var recyclerView:RecyclerView
    private lateinit var layoutManager:LinearLayoutManager
    private lateinit var recyclerViewAdaper:MedicineFavoriteListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_favorite_list, container, false)
        initRecyclerView()
        initLayoutManager()
        bindRecyclerViewWithAdapter()
        return rootView
    }

    fun initViews(){
        recyclerView
    }

    fun initRecyclerView(){
        recyclerView = rootView.findViewById<RecyclerView>(R.id.favorite_list_recycler_view)
        recyclerView.setHasFixedSize(true);
    }

    fun initLayoutManager(){
        layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
    }

    fun bindRecyclerViewWithAdapter(){
        recyclerView.layoutManager = layoutManager
        //recyclerViewAdaper = MedicineFavoriteListRecyclerAdapter(this.context!!,WishListSupplier.medicineWishList)
        recyclerView.adapter = recyclerViewAdaper
    }


}
