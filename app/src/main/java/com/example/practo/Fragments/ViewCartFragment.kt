package com.example.practo.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.practo.Adapters.MedicineCartRecyclerAdaptor
import com.example.practo.Adapters.SearchMedicineRecyclerAdaptor
import com.example.practo.Model.Medicine
import com.example.practo.Model.MedicineCart
import com.example.practo.Model.MedicineSupplier
import com.example.practo.R


class ViewCartFragment : Fragment() {

    private lateinit var rootView:View
    //private var cartItemList:ArrayList<Medicine> = ArrayList()
    private var medicineCartItems:ArrayList<MedicineCart> = ArrayList()
    private lateinit var emptyCartView:LinearLayout
    private lateinit var cartNotEmptyView:LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdaptor:MedicineCartRecyclerAdaptor
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_view_cart, container, false)
        customizeToolbar()
        initViews()
        initRecyclerView()
        initLayoutManager()
        viewDisplay()
        return rootView
    }

    fun initViews(){
        emptyCartView = rootView.findViewById(R.id.cart_emtpy_view)
        cartNotEmptyView = rootView.findViewById(R.id.cart_not_empty_view)
    }

    fun customizeToolbar(){
        var activity = getActivity() as AppCompatActivity
        var actionBarSupport = activity.supportActionBar
        actionBarSupport?.setTitle("My Cart")

    }

    fun initRecyclerView(){
        recyclerView = rootView.findViewById<RecyclerView>(R.id.cart_items_recycler_view)
        recyclerView.setHasFixedSize(true);
    }

    fun initLayoutManager(){
        layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
    }

    fun bindRecyclerViewWithAdapter(){
        recyclerView.layoutManager = layoutManager
        recyclerViewAdaptor = MedicineCartRecyclerAdaptor(this.context!!,
            medicineCartItems)
        recyclerView.adapter = recyclerViewAdaptor
    }

    fun viewDisplay(){
        if(medicineCartItems.isEmpty()){
            cartNotEmptyView.visibility = View.GONE
            emptyCartView.visibility = View.VISIBLE
        } else {
            emptyCartView.visibility = View.GONE
            bindRecyclerViewWithAdapter()
            cartNotEmptyView.visibility= View.VISIBLE
        }
    }

    fun addItemToCart(medicine: Medicine){
        var medicineCartItem:MedicineCart
        var flag=0
        var index=0
//        cartItemList.add(medicine) //every time added need to add to recycler view and notify recycler view,add to db
//        addItemToMedicineCart()
        //if(medicineCartItems.isEmpty()){
            medicineCartItem = MedicineCart(medicine,1,medicine.medicinePrice)
            medicineCartItems.add(medicineCartItem)
//        } else {
//            for(item in medicineCartItems){
//                if(item.medicine.medicineId == medicine.medicineId){
//                    medicineCartItems.
//                }
//            }
//        }
    }




}
