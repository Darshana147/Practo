package com.example.practo.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.practo.Adapters.MedicineFavoriteListRecyclerAdapter
import com.example.practo.InterfaceListeners.FavoriteMedicineListListener
import com.example.practo.InterfaceListeners.FavoriteMedicinesFragmentListener
import com.example.practo.InterfaceListeners.IFragmentListener
import com.example.practo.InterfaceListeners.ISearch
import com.example.practo.Model.Medicine
import com.example.practo.Model.MedicineCartItem
import com.example.practo.R
import com.example.practo.UseCases.FavoriteMedicineUseCases
import com.example.practo.UseCases.MedicineCartUseCases
import com.example.practo.Utils.setDialogFragment
import com.example.practo.Utils.toast
import kotlinx.android.synthetic.main.fragment_favorite_list.*
import kotlinx.android.synthetic.main.fragment_favorite_list.view.*


class FavoriteMedicineListFragment : Fragment(),FavoriteMedicineListListener,AddToCartCustomDialog.OnQtyEntered,ISearch{

    private lateinit var rootView:View
    private lateinit var recyclerView:RecyclerView
    private lateinit var layoutManager:LinearLayoutManager
    private lateinit var recyclerViewAdaper:MedicineFavoriteListRecyclerAdapter
    private lateinit var favoriteMedicineUseCases:FavoriteMedicineUseCases
    private lateinit var favoriteListNotEmpty:FrameLayout
    private lateinit var favoriteListEmpty:LinearLayout
    private lateinit var medicineCartUseCases:MedicineCartUseCases
    private lateinit var addMedicinesToFavListBtn:Button
    private lateinit var mFavoriteMedicinesFragmentListener: FavoriteMedicinesFragmentListener
    private var cartItemsHashSet = hashSetOf<Int>()
    private lateinit var fragmentListener: IFragmentListener
    private var noFavMedicines = true
    private var textViewItem:TextView? =null
    private var medicineId: Int=0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_favorite_list, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUseCases()
        initViews()
        setFavoriteMedicinesFragmentListener()
        initOnSelectListeners()
        getValuesFromDb()
        initRecyclerView()
        initLayoutManager()
        viewDisplay()
    }

    fun getValuesFromDb(){
        cartItemsHashSet.clear()
        for(cartItem in medicineCartUseCases.getMedicineItemsFromCart()){
            cartItemsHashSet.add(cartItem.medicine.medicineId)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fragmentListener = parentFragment as IFragmentListener
        fragmentListener.addFragmentSearchContext(this)
        fragmentListener.addFilterFavoriteListFragmentContext(this)
    }

    override fun onDetach() {
        super.onDetach()
        fragmentListener.removeFragmentSearchContext(this)
    }

    override fun onSearched(text: String?) {
        text?.let {
            filter(text)
        }
    }


    fun filter(text: String) {
        val filteredList: ArrayList<Medicine> = ArrayList()
        for (medicine in favoriteMedicineUseCases.getMedicinesFromFavoriteMedicineList(1)) {
            if (medicine.medicineName.toLowerCase().trim().contains(text.toLowerCase().trim()) || (medicine.medicineDescription.trim().toLowerCase().contains(
                    text.toLowerCase().trim()
                ))
            ) {
                filteredList.add(medicine)
            }
        }
        if(filteredList.isEmpty()){
            recyclerView.visibility = View.GONE
            no_fav_search_results_txv.text = "Nothing Found for '$text'"
            rootView.fav_list_search_nothing_found_constraint_layout.visibility = View.VISIBLE
        } else {
            rootView.fav_list_search_nothing_found_constraint_layout.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            recyclerViewAdaper.filterList(filteredList)
        }
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

    fun bindRecyclerViewWithAdapter(favMedicines:ArrayList<Medicine>){
        recyclerView.layoutManager = layoutManager
        recyclerViewAdaper = MedicineFavoriteListRecyclerAdapter(this.context!!,favMedicines,this,cartItemsHashSet)
        recyclerView.adapter = recyclerViewAdaper
    }

    fun viewDisplay(){
        val favMedicines = favoriteMedicineUseCases.getMedicinesFromFavoriteMedicineList(1)
        if(favMedicines.isEmpty()){
            noFavMedicines=true
            favoriteListNotEmpty.visibility = View.GONE
            favoriteListEmpty.visibility = View.VISIBLE
        } else {
            noFavMedicines=false
            favoriteListEmpty.visibility = View.GONE
            bindRecyclerViewWithAdapter(favMedicines)
            favoriteListNotEmpty.visibility= View.VISIBLE
        }
    }

    override fun notifyChangesInMedicineFavoriteList() {
        viewDisplay()
    }

    override fun onRemoveMedicineFromFavoriteListListener(medicine: Medicine) {
        favoriteMedicineUseCases.removeMedicineFromFavoriteList(1,medicine.medicineId)
        context?.toast("Item Removed")
        viewDisplay()
        recyclerViewAdaper.favoriteListDataSetChanged(medicine)
        (parentFragment as SearchMedicinePagerFragment).notifyChangesToSearchMedicinesFragment(medicine.medicineId,getString(R.string.remove_favorite))
    }

    override fun onAddToCartClicked(medicineId: Int, view: TextView) {
        textViewItem = view
        this.medicineId = medicineId
        if ((textViewItem as TextView).text.toString().equals("ADD TO CART")) {
            context!!.setDialogFragment(
                this,
                fragmentManager,
                "AddToCartFragment",
                AddToCartCustomDialog.newInstance(medicineCartUseCases.getMedicineById(medicineId).medicinePrice)
            )
        } else {
            (parentFragment as SearchMedicinePagerFragment).onViewCartClicked()
        }
    }


    fun setFavoriteMedicinesFragmentListener(){
        this.mFavoriteMedicinesFragmentListener=parentFragment as FavoriteMedicinesFragmentListener
    }

    override fun getQtyEntered(qty: Int) {
        (textViewItem as TextView).text = "VIEW CART"
        customAddedToCartToast("Item added to cart")
        medicineCartUseCases.addMedicineToCart(MedicineCartItem(medicineCartUseCases.getMedicineById(medicineId),qty))
        (parentFragment as SearchMedicinePagerFragment).setUpBadge()
        (parentFragment as SearchMedicinePagerFragment).notifyChangesToSearchMedicinesFragment(medicineId,getString(R.string.add_to_cart))
    }

    override fun onFavoriteMedicineClicked(medicineId: Int) {
        context!!.setDialogFragment(this,fragmentManager,"MedicineDescription",MedicineDescriptionCustomDialog.newInstance(medicineCartUseCases.getMedicineById(medicineId)))
    }

    fun customAddedToCartToast(msg:String, duration:Int = Toast.LENGTH_SHORT){
        val toast = Toast.makeText(context, msg,
            duration)
        val toastLayout = layoutInflater.inflate(R.layout.custom_added_to_cart_toast_layout,null)
        toast.view = toastLayout
        toast.show()
    }


    override fun notifyItemAddedToCart(medicineId: Int) {
        if(noFavMedicines==false) {
            recyclerViewAdaper.notifyItemAddedToCart(medicineId)
        }
    }


}