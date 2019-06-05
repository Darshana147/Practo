package com.example.practo.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import com.example.practo.Adapters.SearchMedicineRecyclerAdaptor
import com.example.practo.DAO.MedicineDAO
import com.example.practo.InterfaceListeners.OnSearchMedicinesFragmentListener
import com.example.practo.R
import com.example.practo.Model.*
import com.example.practo.UseCases.FavoriteMedicineUseCases
import com.example.practo.UseCases.MedicineCartUseCases


class SearchMedicinesFragment : Fragment(), OnSearchMedicinesFragmentListener, AddToCartDialogFragment.OnInputSelected {

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdaptor: SearchMedicineRecyclerAdaptor
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var medicine: Medicine
    private lateinit var favoriteMedicineUseCases: FavoriteMedicineUseCases
    private lateinit var medicineCartUseCases: MedicineCartUseCases
    private lateinit var medicineDAO: MedicineDAO


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_search_medicines, container, false)
        medicineDAO = MedicineDAO(this.context!!)
        initUseCases()
        initRecyclerView()
        initLayoutManager()
        bindRecyclerViewWithAdapter()
        return rootView
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
            SearchMedicineRecyclerAdaptor(this.context!!, medicineDAO.getAllMedicines(), this, favoriteMedicineUseCases)
        recyclerView.adapter = recyclerViewAdaptor
    }

    fun filter(text: String) {
        var filteredList: ArrayList<Medicine> = ArrayList()

        for (medicine in medicineDAO.getAllMedicines()) {
            if (medicine.medicineName.toLowerCase().trim().contains(text.toLowerCase().trim()) || (medicine.medicineDescription.trim().toLowerCase().contains(
                    text.toLowerCase().trim()
                ))
            ) {
                filteredList.add(medicine)
            }
        }
        recyclerViewAdaptor.filterList(filteredList)
    }

    override fun onAddToCartClicked(medicine: Medicine) {
        var dialog = AddToCartDialogFragment()
        var args: Bundle = Bundle()
        args.putStringArrayList(
            "qty",
            arrayListOf<String>("1", "2", "3", "4", "5", "6", "7", "8", "9")
        )//get the count from the db
        dialog.arguments = args
        dialog.setTargetFragment(this, 1)
        dialog.show(fragmentManager, "AddToCartFragment")
        this.medicine = medicine
    }

    override fun sendItemQtyInputFromCartDialogFragment(input: String) {
        Toast.makeText(context, "Item added to Cart", Toast.LENGTH_SHORT).show()
        medicineCartUseCases.addMedicineToCart(MedicineCartItem(medicine, input.toInt()))
        (parentFragment as SearchMedicinePagerFragment).setUpBadge() //no need here as addToCart sends back the changes
    }


    override fun onAddToFavoriteListClicked() {
        (parentFragment as SearchMedicinePagerFragment).notifyChangesInFavoriteList()
    }

    fun notifyChangesInFavoriteMarkedMedicines(){
        recyclerViewAdaptor.dataSetChanged()
    }


}
