package com.example.practo.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.example.practo.Adapters.MedicineFavoriteListRecyclerAdapter
import com.example.practo.InterfaceListeners.FavoriteMedicineListListener
import com.example.practo.InterfaceListeners.FavoriteMedicinesFragmentListener
import com.example.practo.Model.MedicineCartItem


import com.example.practo.R
import com.example.practo.UseCases.FavoriteMedicineUseCases
import com.example.practo.UseCases.MedicineCartUseCases


class FavoriteMedicineListFragment : Fragment(),FavoriteMedicineListListener,AddToCartDialogFragment.OnInputSelected{

    private lateinit var rootView:View
    private lateinit var recyclerView:RecyclerView
    private lateinit var layoutManager:LinearLayoutManager
    private lateinit var recyclerViewAdaper:MedicineFavoriteListRecyclerAdapter
    private lateinit var favoriteMedicineUseCases:FavoriteMedicineUseCases
    private lateinit var favoriteListNotEmpty:LinearLayout
    private lateinit var favoriteListEmpty:LinearLayout
    private lateinit var medicineCartUseCases:MedicineCartUseCases
    private lateinit var addMedicinesToFavListBtn:Button
    private lateinit var mFavoriteMedicinesFragmentListener: FavoriteMedicinesFragmentListener
    private var medicineId: Int=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_favorite_list, container, false)
        initUseCases()
        initViews()
        initOnSelectListeners()
        initRecyclerView()
        initLayoutManager()
        viewDisplay()
        return rootView
    }

    fun initUseCases(){
        favoriteMedicineUseCases = FavoriteMedicineUseCases(this.context!!)
        medicineCartUseCases= MedicineCartUseCases(this.context!!)
    }

    fun initViews(){
        favoriteListEmpty=rootView.findViewById(R.id.favorite_list_empty)
        favoriteListNotEmpty=rootView.findViewById(R.id.favorite_list_not_empty)
        addMedicinesToFavListBtn = rootView.findViewById(R.id.add_medicines_to_empty_fav_list_btn)
    }

    fun initOnSelectListeners(){
        addMedicinesToFavListBtn.setOnClickListener {
          mFavoriteMedicinesFragmentListener.onAddMedicinesBtnFromEmptyFavListClicked()
        }
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
        recyclerViewAdaper = MedicineFavoriteListRecyclerAdapter(this.context!!,favoriteMedicineUseCases.getMedicinesFromFavoriteMedicineList(1),this)
        recyclerView.adapter = recyclerViewAdaper
    }

    fun viewDisplay(){
        if(favoriteMedicineUseCases.getMedicinesFromFavoriteMedicineList(1).isEmpty()){
            favoriteListNotEmpty.visibility = View.GONE
            favoriteListEmpty.visibility = View.VISIBLE
        } else {
            favoriteListEmpty.visibility = View.GONE
            bindRecyclerViewWithAdapter()
            favoriteListNotEmpty.visibility= View.VISIBLE
        }
    }

    override fun onRemoveMedicineFromFavoriteListListener(medicineId: Int) {
        favoriteMedicineUseCases.removeMedicineFromFavoriteList(1,medicineId)
        viewDisplay()
        recyclerViewAdaper.setChangedFavoriteList(favoriteMedicineUseCases.getMedicinesFromFavoriteMedicineList(1))
        (parentFragment as SearchMedicinePagerFragment).notifyChangesToSearchMedicinesFragment()
    }

    override fun onAddToCartClicked(medicineId: Int) {
        var dialog = AddToCartDialogFragment()
        var args:Bundle = Bundle()
        args.putStringArrayList("qty",arrayListOf<String>("1","2","3","4","5","6","7","8","9"))//get the count from the db
        dialog.arguments = args
        dialog.setTargetFragment(this,1)
        dialog.show(fragmentManager,"AddToCartFragment")
        this.medicineId=medicineId
    }

    override fun sendItemQtyInputFromCartDialogFragment(input: String) {
        Toast.makeText(context,input.toString(), Toast.LENGTH_SHORT).show()
        medicineCartUseCases.addMedicineToCart(MedicineCartItem(medicineCartUseCases.getMedicineById(medicineId),input.toInt()))
        (parentFragment as SearchMedicinePagerFragment).setUpBadge()
        Toast.makeText(context,"Item added to Cart",Toast.LENGTH_SHORT).show()
    }

    fun setFavoriteMedicinesFragmentListener(mFavoriteMedicinesFragmentListener: FavoriteMedicinesFragmentListener){
        this.mFavoriteMedicinesFragmentListener=mFavoriteMedicinesFragmentListener
    }

    fun notifyFavoriteListChanges(){
       viewDisplay()
    }

}
