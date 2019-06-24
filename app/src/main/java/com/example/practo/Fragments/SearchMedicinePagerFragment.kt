package com.example.practo.Fragments


import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import com.example.practo.Adapters.MedicineFavoriteListRecyclerAdapter
import com.example.practo.Adapters.SearchMedicinesTabAdapter
import com.example.practo.InterfaceListeners.*
import com.example.practo.R
import com.example.practo.UseCases.FavoriteMedicineUseCases
import com.example.practo.UseCases.MedicineCartUseCases
import com.example.practo.Utils.toast
import kotlinx.android.synthetic.main.activity_order_medicine.*
import kotlinx.android.synthetic.main.fragment_favorite_list.*
import kotlinx.android.synthetic.main.fragment_search_medicines.*


class SearchMedicinePagerFragment : Fragment(), FavoriteMedicinesFragmentListener, IFragmentListener {

    private lateinit var rootView: View
    private lateinit var searchItem: MenuItem
    private lateinit var searchView: android.support.v7.widget.SearchView
    private lateinit var favoriteMedicineUseCases: FavoriteMedicineUseCases
    private lateinit var medicineCartUseCases: MedicineCartUseCases
    private lateinit var tabAdapter: SearchMedicinesTabAdapter
    private lateinit var cartCount: TextView
    private lateinit var searchMedicinesFragment: SearchMedicinesFragment
    private lateinit var favoriteListFragment: FavoriteMedicineListFragment
    private lateinit var mSearchFragmentToolbarMenuListener: OnSearchFragmentToolbarMenuListener
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private var iSearchList = arrayListOf<ISearch>()
    private var mFavoriteMedicineListListener: FavoriteMedicineListListener? = null
    private var mSearchMedicinesFragmentListener: OnSearchMedicinesFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_search_medicine_pager, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        customizeToolbar()
        initFragment()
        initTabAdaptor()
        initUseCases()
        setHasOptionsMenu(true)
        addFragmentsToTabs()
        bindPagerAndTabAdaptor()
        setListeners()
    }


    fun setListeners() {
        favoriteListFragment.setFavoriteMedicinesFragmentListener(this)
    }

    fun initFragment() {
        searchMedicinesFragment = SearchMedicinesFragment()
        favoriteListFragment = FavoriteMedicineListFragment()
    }

    fun initUseCases() {
        medicineCartUseCases = MedicineCartUseCases(this.context!!)
        favoriteMedicineUseCases = FavoriteMedicineUseCases(this.context!!)
    }

    fun customizeToolbar() {
        val activity = getActivity() as AppCompatActivity
        val actionBarSupport = activity.supportActionBar
        actionBarSupport?.setTitle("Search Medicines")
    }

    fun initTabAdaptor() {
        tabAdapter = SearchMedicinesTabAdapter(childFragmentManager)

    }

    fun addFragmentsToTabs() {
        tabAdapter.addFragments(searchMedicinesFragment, "Search Medicines")
        tabAdapter.addFragments(favoriteListFragment, "Favorites")
    }

    fun bindPagerAndTabAdaptor() {
        viewPager = rootView.findViewById<ViewPager>(R.id.searchMedicinesViewPager)
        tabLayout = rootView.findViewById<TabLayout>(R.id.searchMedicineTabLayout)
        viewPager.adapter = tabAdapter
        tabLayout.setupWithViewPager(viewPager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                val item = activity?.activity_order_medicine_toolbar?.menu?.findItem(R.id.search_menu_item)
                item?.collapseActionView()
                when (p0) {
                    0 -> {
                        enableSearchBar()
                        search_medicine_recycler_view.adapter?.notifyDataSetChanged()
                    }
                    1 -> if (isNoFavoriteMedicines()) {
                        disableSearchBar()
                    } else {
                        enableSearchBar()
                        favorite_list_recycler_view.adapter?.notifyDataSetChanged()
                    }
                }
            }

        })
    }


    fun setSearchFragmentToolbarMenuListener(mSearchFragmentToolbarMenuListener: OnSearchFragmentToolbarMenuListener) {
        this.mSearchFragmentToolbarMenuListener = mSearchFragmentToolbarMenuListener
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.search_medicine_toolbar_actions_menu, menu)
        //cart badge layout
        val item: MenuItem = menu!!.findItem(R.id.view_cart_menu_item)
        val actionView = item.actionView
        cartCount = actionView.findViewById<TextView>(R.id.cart_badge_txv)
        setUpBadge()
        actionView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onOptionsItemSelected(item)
            }

        })
        //search bar
        searchItem = menu.findItem(R.id.search_menu_item)
        if (isNoFavoriteMedicines() && viewPager.currentItem == 1) {
            searchItem.setEnabled(false)
            searchItem.getIcon().setAlpha(130)
        } else {
            searchItem.setEnabled(true)
            searchItem.getIcon().setAlpha(255)
            searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d("abcd", iSearchList.size.toString() + " onQueryTextSubmit")
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d("abcd", iSearchList.size.toString() + " onQueryTextChange")
                    for (iSearchLocal in iSearchList) {
                        iSearchLocal.onSearched(newText)
                    }
                    return false
                }

            })
        }
        super.onCreateOptionsMenu(menu, inflater)

    }

    fun setUpBadge() {
        if (medicineCartUseCases.getCartTotalQuantity() == 0) {
            if (cartCount.getVisibility() != View.GONE) {
                cartCount.setVisibility(View.GONE);
            }
        } else {
            if (medicineCartUseCases.getCartTotalQuantity() >= 100 && medicineCartUseCases.getCartTotalQuantity() < 1000) {
                cartCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.textsizefor100ele))
            } else if (medicineCartUseCases.getCartTotalQuantity() >= 1000) {
                cartCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.textsizefor1000ele))
            } else {
                cartCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.textsize))
            }
            cartCount.setText(medicineCartUseCases.getCartTotalQuantity().toString());
            if (cartCount.getVisibility() != View.VISIBLE) {
                cartCount.setVisibility(View.VISIBLE);
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.view_cart_menu_item -> {
//                mSearchFragmentToolbarMenuListener.onViewCartClicked()
                onViewCartClicked()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }

    fun onViewCartClicked(){
        mSearchFragmentToolbarMenuListener.onViewCartClicked()
    }

    override fun onAddMedicinesBtnFromEmptyFavListClicked() {
        viewPager.setCurrentItem(0)
        tabLayout.setupWithViewPager(viewPager)
    }

    fun enableSearchBar() {
        val item = activity?.activity_order_medicine_toolbar?.menu?.findItem(R.id.search_menu_item)
        item?.collapseActionView()
        item?.setEnabled(true)
        item?.icon?.alpha = 255
    }

    fun disableSearchBar() {
        val item = activity?.activity_order_medicine_toolbar?.menu?.findItem(R.id.search_menu_item)
        item?.collapseActionView()
        item?.setEnabled(false)
        item?.icon?.alpha = 130
    }

    fun isNoFavoriteMedicines(): Boolean {
        return favoriteMedicineUseCases.getMedicinesFromFavoriteMedicineList(1).isEmpty()
    }


    fun notifyChangesToSearchMedicinesFragment() {
        val item = activity?.activity_order_medicine_toolbar?.menu?.findItem(R.id.search_menu_item)
        if (isNoFavoriteMedicines() && viewPager.currentItem == 1) {
            disableSearchBar()
        }
        mSearchMedicinesFragmentListener?.onNotifyDataSetChanged()
    }

    override fun addFragmentSearchContext(iSearch: ISearch) {
        iSearchList.add(iSearch)
    }

    override fun addFilterFavoriteListFragmentContext(favoriteMedicinesListListener: FavoriteMedicineListListener) {
        mFavoriteMedicineListListener = favoriteMedicinesListListener
    }

    override fun addSearchMedicinesFragmentListenerContext(searchMedicinesFragmentListener: OnSearchMedicinesFragmentListener) {
        mSearchMedicinesFragmentListener = searchMedicinesFragmentListener
    }


    override fun removeFragmentSearchContext(iSearch: ISearch) {
        iSearchList.remove(iSearch)
    }

    fun notifyChangesInFavoriteList() {
        mFavoriteMedicineListListener?.notifyChangesInMedicineFavoriteList()
    }
    
}