package com.example.practo.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import android.widget.Toast
import com.example.practo.Adapters.SearchMedicineRecyclerAdaptor
import com.example.practo.DAO.MedicineDAO
import com.example.practo.InterfaceListeners.OnAddToCartSelectedListener


import com.example.practo.R
import com.example.practo.InterfaceListeners.OnSearchFragmentToolbarMenuListener
import com.example.practo.Model.*
import com.example.practo.UseCases.FavoriteMedicineUseCases
import com.example.practo.UseCases.MedicineCartUseCases



class SearchMedicinesFragment : Fragment(),OnAddToCartSelectedListener,AddToCartDialogFragment.OnInputSelected{

    private lateinit var rootView:View
    private lateinit var searcItem:MenuItem
    private lateinit var searchView: android.support.v7.widget.SearchView
    private lateinit var mSearchFragmentToolbarMenuListener: OnSearchFragmentToolbarMenuListener
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdaptor:SearchMedicineRecyclerAdaptor
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var medicine:Medicine
    private lateinit var cartCount:TextView
    private lateinit var favoriteMedicineUseCases: FavoriteMedicineUseCases
    private lateinit var medicineCartUseCases:MedicineCartUseCases
    private lateinit var medicineDAO:MedicineDAO



    fun setSearchFragmentToolbarMenuListener(mSearchFragmentToolbarMenuListener: OnSearchFragmentToolbarMenuListener){
        this.mSearchFragmentToolbarMenuListener = mSearchFragmentToolbarMenuListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_search_medicines, container, false)
        medicineDAO = MedicineDAO(this.context!!)
        initUseCases()
        customizeToolbar()
        initRecyclerView()
        initLayoutManager()
        bindRecyclerViewWithAdapter()
        setHasOptionsMenu(true)
        return rootView
    }

    fun initUseCases(){
        medicineCartUseCases = MedicineCartUseCases(this.context!!)
        favoriteMedicineUseCases = FavoriteMedicineUseCases(this.context!!)
    }


    fun customizeToolbar(){
        var activity = getActivity() as AppCompatActivity
        var actionBarSupport = activity.supportActionBar
        actionBarSupport?.setTitle("Search Medicines")
    }

    fun initRecyclerView(){
        recyclerView = rootView.findViewById<RecyclerView>(R.id.search_medicine_recycler_view)
        recyclerView.setHasFixedSize(true);
    }

    fun initLayoutManager(){
        layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
    }

    fun bindRecyclerViewWithAdapter(){
        recyclerView.layoutManager = layoutManager
        recyclerViewAdaptor = SearchMedicineRecyclerAdaptor(this.context!!,medicineDAO.getAllMedicines(),this,favoriteMedicineUseCases)
        recyclerView.adapter = recyclerViewAdaptor
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_medicine_toolbar_actions_menu,menu)
        //cart badge layout
        var item:MenuItem = menu!!.findItem(R.id.view_cart_menu_item)
        var actionView = item.actionView
        cartCount = actionView.findViewById<TextView>(R.id.cart_badge_txv)
        setUpBadge()
        actionView.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionsItemSelected(item)
            }

        })

        //search bar
        searcItem = menu!!.findItem(R.id.search_menu_item)
        searchView = searcItem.actionView as SearchView
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               filter(newText.toString())
                return false
            }

        })


        super.onCreateOptionsMenu(menu, inflater)
    }

    fun setUpBadge(){
        if (cartCount != null) {
            if (medicineCartUseCases.getCartTotalQuantity() == 0) {
                if (cartCount.getVisibility() != View.GONE) {
                    cartCount.setVisibility(View.GONE);
                }
            } else {
                if(medicineCartUseCases.getCartTotalQuantity()>=100&&medicineCartUseCases.getCartTotalQuantity()<1000){
                    cartCount.setTextSize(TypedValue.COMPLEX_UNIT_PX,resources.getDimension(R.dimen.textsizefor100ele))
                } else if(medicineCartUseCases.getCartTotalQuantity()>=1000){
                    cartCount.setTextSize(TypedValue.COMPLEX_UNIT_PX,resources.getDimension(R.dimen.textsizefor1000ele))
                } else {
                    cartCount.setTextSize(TypedValue.COMPLEX_UNIT_PX,resources.getDimension(R.dimen.textsize))
                }
                cartCount.setText(medicineCartUseCases.getCartTotalQuantity().toString());
                if (cartCount.getVisibility() != View.VISIBLE) {
                    cartCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.view_cart_menu_item -> {
                mSearchFragmentToolbarMenuListener.onViewCartClicked()
                return true
            }
            R.id.favorites_list_menu_item -> {
                mSearchFragmentToolbarMenuListener.onWishListClicked()
                Toast.makeText(context, "WishList", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }
    
    fun filter(text:String){
        var filteredList:ArrayList<Medicine> = ArrayList()

        for(medicine in medicineDAO.getAllMedicines()){
            if(medicine.medicineName.toLowerCase().trim().contains(text.toLowerCase().trim())||(medicine.medicineDescription.trim().toLowerCase().contains(text.toLowerCase().trim()))){
                filteredList.add(medicine)
            }
        }
        recyclerViewAdaptor.filterList(filteredList)
    }

    override fun onAddToCartClicked(medicine: Medicine) {
        var dialog = AddToCartDialogFragment()
        var args:Bundle = Bundle()
        args.putStringArrayList("qty",arrayListOf<String>("1","2","3","4","5","6","7","8","9"))//get the count from the db
        dialog.arguments = args
        dialog.setTargetFragment(this,1)
        dialog.show(fragmentManager,"AddToCartFragment")
        this.medicine = medicine
    }

    override fun sendItemQtyInputFromCartDialogFragment(input: String) {
        Toast.makeText(context,"Item added to Cart",Toast.LENGTH_SHORT).show()
        medicineCartUseCases.addMedicineToCart(MedicineCartItem(medicine,input.toInt()))
        setUpBadge() //no need here as addToCart sends back the changes
    }


}
