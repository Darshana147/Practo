package com.example.practo.Fragments


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import com.example.practo.Adapters.SearchMedicinesTabAdapter
import com.example.practo.InterfaceListeners.FavoriteMedicinesFragmentListener
import com.example.practo.InterfaceListeners.OnSearchFragmentToolbarMenuListener
import com.example.practo.R
import com.example.practo.UseCases.FavoriteMedicineUseCases
import com.example.practo.UseCases.MedicineCartUseCases


class SearchMedicinePagerFragment : Fragment(), FavoriteMedicinesFragmentListener {

    private lateinit var rootView: View
    private lateinit var searcItem:MenuItem
    private lateinit var searchView: android.support.v7.widget.SearchView
    private lateinit var favoriteMedicineUseCases: FavoriteMedicineUseCases
    private lateinit var medicineCartUseCases: MedicineCartUseCases
    private lateinit var tabAdapter:SearchMedicinesTabAdapter
    private lateinit var cartCount:TextView
    private lateinit var searchMedicinesFragment:SearchMedicinesFragment
    private lateinit var favoriteListFragment:FavoriteMedicineListFragment
    private lateinit var mSearchFragmentToolbarMenuListener: OnSearchFragmentToolbarMenuListener
    private lateinit var viewPager:ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_search_medicine_pager, container, false)
        customizeToolbar()
        initFragment()
        initTabAdaptor()
        initUseCases()
        setHasOptionsMenu(true)
        addFragmentsToTabs()
        bindPagerAndTabAdaptor()
        setListeners()
        return rootView
    }

    fun setListeners(){
        favoriteListFragment.setFavoriteMedicinesFragmentListener(this)
    }

    fun initFragment(){
        searchMedicinesFragment = SearchMedicinesFragment()
        favoriteListFragment = FavoriteMedicineListFragment()
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

    fun initTabAdaptor(){
        tabAdapter = SearchMedicinesTabAdapter(childFragmentManager)

    }

    fun addFragmentsToTabs(){
        tabAdapter.addFragments(searchMedicinesFragment,"Search Medicines")
        tabAdapter.addFragments(favoriteListFragment,"Favorites")
    }

    fun bindPagerAndTabAdaptor(){
        viewPager = rootView.findViewById<ViewPager>(R.id.searchMedicinesViewPager)
        tabLayout = rootView.findViewById<TabLayout>(R.id.searchMedicineTabLayout)
        viewPager.adapter = tabAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    fun setSearchFragmentToolbarMenuListener(mSearchFragmentToolbarMenuListener: OnSearchFragmentToolbarMenuListener){
        this.mSearchFragmentToolbarMenuListener = mSearchFragmentToolbarMenuListener
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_medicine_toolbar_actions_menu,menu)
        //cart badge layout
        var item: MenuItem = menu!!.findItem(R.id.view_cart_menu_item)
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
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchMedicinesFragment.filter(newText.toString())
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
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }

    override fun onAddMedicinesBtnFromEmptyFavListClicked() {
        viewPager.setCurrentItem(0)
        tabLayout.setupWithViewPager(viewPager)
    }

    fun notifyChangesInFavoriteList(){
        favoriteListFragment.notifyFavoriteListChanges()
    }

    fun notifyChangesToSearchMedicinesFragment(){
        searchMedicinesFragment.notifyChangesInFavoriteMarkedMedicines()
    }


}
