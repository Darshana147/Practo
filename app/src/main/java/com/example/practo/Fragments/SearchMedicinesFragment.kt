package com.example.practo.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import android.widget.Toast
import com.example.practo.Adapters.SearchMedicineRecyclerAdaptor
import com.example.practo.DAO.MedicineDAO
import com.example.practo.InterfaceListeners.IFragmentListener
import com.example.practo.InterfaceListeners.ISearch
import com.example.practo.InterfaceListeners.OnSearchMedicinesFragmentListener
import com.example.practo.R
import com.example.practo.Model.*
import com.example.practo.UseCases.FavoriteMedicineUseCases
import com.example.practo.UseCases.MedicineCartUseCases
import com.example.practo.Utils.setDialogFragment
import com.example.practo.Utils.toast
import kotlinx.android.synthetic.main.fragment_search_medicines.*
import kotlinx.android.synthetic.main.fragment_search_medicines.view.*


class SearchMedicinesFragment : Fragment(), OnSearchMedicinesFragmentListener, AddToCartCustomDialog.OnQtyEntered,ISearch{


    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdaptor: SearchMedicineRecyclerAdaptor
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var medicine: Medicine
    private lateinit var favoriteMedicineUseCases: FavoriteMedicineUseCases
    private lateinit var medicineCartUseCases: MedicineCartUseCases
    private lateinit var medicineDAO: MedicineDAO
    private lateinit var fragmentListener:IFragmentListener
    private var allMedicines:ArrayList<Medicine> = arrayListOf()
    private var favMedicines:ArrayList<Medicine> = arrayListOf()
    private var cartMedicines:ArrayList<MedicineCartItem> = arrayListOf()
    private var itemView:View? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_search_medicines, container, false)
        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        medicineDAO = MedicineDAO(this.context!!)
        initUseCases()
        getValuesFromDb()
        initRecyclerView()
        initLayoutManager()
        bindRecyclerViewWithAdapter()
    }

    fun getValuesFromDb(){
        allMedicines = medicineDAO.getAllMedicines()
        favMedicines = favoriteMedicineUseCases.getMedicinesFromFavoriteMedicineList(1)
        cartMedicines = medicineCartUseCases.getMedicineItemsFromCart()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fragmentListener = parentFragment as IFragmentListener
        fragmentListener.addFragmentSearchContext(this)
        fragmentListener.addSearchMedicinesFragmentListenerContext(this)
    }


    override fun onDetach() {
        super.onDetach()
        fragmentListener.removeFragmentSearchContext(this)
    }


    fun initUseCases() {
        medicineCartUseCases = MedicineCartUseCases(this.context!!)
        favoriteMedicineUseCases = FavoriteMedicineUseCases(this.context!!)
    }


    fun initRecyclerView() {
        recyclerView = rootView.findViewById<RecyclerView>(R.id.search_medicine_recycler_view)
        recyclerView.setHasFixedSize(true);
    }

    fun initLayoutManager() {
        layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
    }

    fun bindRecyclerViewWithAdapter() {
        recyclerView.layoutManager = layoutManager
        recyclerViewAdaptor =
            SearchMedicineRecyclerAdaptor(this.context!!, allMedicines,getFavMedicineHashSet(favMedicines),getCartMedicineHashSet(cartMedicines),this, favoriteMedicineUseCases)
        recyclerView.adapter = recyclerViewAdaptor
    }

    fun getFavMedicineHashSet(favMedicines:ArrayList<Medicine>):HashSet<Int>{
        val favMedicineHashSet = hashSetOf<Int>()
        for(favMedicine in favMedicines){
            favMedicineHashSet.add(favMedicine.medicineId)
        }

        return favMedicineHashSet
    }

    fun getCartMedicineHashSet(cartMedicineItems:ArrayList<MedicineCartItem>):HashSet<Int>{
        val cartMedicineHashSet = hashSetOf<Int>()
        for(cartItem in cartMedicineItems){
            cartMedicineHashSet.add(cartItem.medicine.medicineId)
        }
        return cartMedicineHashSet
    }

    override fun onSearched(text: String?) {
        text?.let {
            filter(it)
        }
    }

    fun filter(text: String) {
        val filteredList: ArrayList<Medicine> = ArrayList()
        for (medicine in allMedicines) {
            if (medicine.medicineName.toLowerCase().trim().contains(text.toLowerCase().trim()) || (medicine.medicineDescription.trim().toLowerCase().contains(
                    text.toLowerCase().trim()
                ))
            ) {
                filteredList.add(medicine)
            }
        }

        if(filteredList.isEmpty()){
            recyclerView.visibility = View.GONE
            no_search_results_txv.text = "Nothing Found for '$text'"
            rootView.search_nothing_found_constraint_layout.visibility = View.VISIBLE
        }else {
            rootView.search_nothing_found_constraint_layout.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            recyclerViewAdaptor.filterList(filteredList)
        }
    }


    override fun onAddToCartClicked(medicine: Medicine, itemView: View) {
        this.itemView = itemView
        if ((itemView as TextView).text.toString().equals("ADD TO CART")) {
            context?.setDialogFragment(
                this,
                fragmentManager,
                "AddToCartFragment",
                AddToCartCustomDialog.newInstance(medicine.medicinePrice)
            )
        } else {
            (parentFragment as SearchMedicinePagerFragment).onViewCartClicked()
        }
        this.medicine = medicine
    }


    override fun onAddToFavoriteListClicked() {
        (parentFragment as SearchMedicinePagerFragment).notifyChangesInFavoriteList()
    }


    override fun onNotifyDataSetChanged(medicineId:Int,str:String) {  //check
        when(str){
            getString(R.string.remove_favorite)->{
                recyclerViewAdaptor.notifyItemRemovedFromFavList(medicineId)
            }
            getString(R.string.add_to_cart)->{
                recyclerViewAdaptor.notifyItemAddedToCart(medicineId)
            }
        }

    }

    override fun getQtyEntered(qty: Int) {
        (itemView as TextView).text = "VIEW CART"
        customAddedToCartToast("Item added to Cart")
        val medCartItem = MedicineCartItem(medicine,qty)
        medicineCartUseCases.addMedicineToCart(medCartItem)
        (parentFragment as SearchMedicinePagerFragment).setUpBadge()
        (parentFragment as SearchMedicinePagerFragment).notifyFavoriteMedicinesFragmentItemAddedToCart(medicine.medicineId)
    }

    override fun onMedicineClicked(medicine: Medicine) {
        context?.setDialogFragment(this,fragmentManager,"MedicineDescription",MedicineDescriptionCustomDialog.newInstance(medicine))
    }


    fun customAddedToCartToast(msg:String, duration:Int = Toast.LENGTH_SHORT){
        val toast = Toast.makeText(context, msg,
            duration)
        val toastLayout = layoutInflater.inflate(R.layout.custom_added_to_cart_toast_layout,null)
        toast.view = toastLayout
        toast.show()
    }

}