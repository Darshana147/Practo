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
import com.example.practo.Adapters.MedicineCartRecyclerAdaptor
import com.example.practo.InterfaceListeners.MedicineCartListener
import com.example.practo.InterfaceListeners.OnChangeCartItemQtyListener
import com.example.practo.Model.Medicine
import com.example.practo.Model.MedicineCart
import com.example.practo.R


class ViewCartFragment : Fragment(),OnChangeCartItemQtyListener,AddToCartDialogFragment.OnInputSelected{
    private lateinit var rootView:View
    //private var cartItemList:ArrayList<Medicine> = ArrayList()
    private var medicineCartItems:ArrayList<MedicineCart> = ArrayList()
    private lateinit var emptyCartView:LinearLayout
    private lateinit var cartNotEmptyView:LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdaptor:MedicineCartRecyclerAdaptor
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var mMedicineCartListener:MedicineCartListener
    private var medicineId:Int=0
    private var medicineCartQuantity:Int = 0

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
            medicineCartItems,this)
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

    fun addItemToCart(medicine: Medicine,qty:Int) {
        var flag = 0
        var medicineCartItem: MedicineCart

        for (item in medicineCartItems) {
            if (item.medicine.medicineId == medicine.medicineId) {
                flag = 1
                var index = medicineCartItems.indexOf(item)
                medicineCartItem = MedicineCart(medicine, qty, medicine.medicinePrice)
                medicineCartItems.set(index, medicineCartItem)
                break
            }
        }
        if (flag == 0) {
            medicineCartItem = MedicineCart(medicine, qty, medicine.medicinePrice)
            medicineCartItems.add(medicineCartItem)
        }
        setMedicineCartQuantity()
    }

    override fun onChangeQuantityClicked(medicineId:Int) {
        this.medicineId = medicineId
        var dialog = AddToCartDialogFragment()
        var args:Bundle = Bundle()
        args.putStringArrayList("qty",arrayListOf<String>("1","2","3","4","5","6","7","8","9"))//get the count from the db
        dialog.arguments = args
        dialog.setTargetFragment(this,1)
        dialog.show(fragmentManager,"AddToCartFragment")
    }

    override fun onCartItemRemoved(medicineId: Int) {
        for(cartItem in medicineCartItems){
            if(cartItem.medicine.medicineId==medicineId){
                medicineCartItems.remove(cartItem)
                break
            }
        }
        setMedicineCartQuantity()
        recyclerViewAdaptor.setChangedCartItemList(medicineCartItems)
    }


    override fun sendItemQtyInputFromCartDialogFragment(input: String) {
        var index=0
        lateinit var changedCartItem:MedicineCart
        for(cartItem in medicineCartItems){
            if(cartItem.medicine.medicineId == medicineId){
                changedCartItem = cartItem
                changedCartItem.medicineQuantity = input.toInt()
                index = medicineCartItems.indexOf(cartItem)
                break
            }
        }
        medicineCartItems.set(index,changedCartItem)
        setMedicineCartQuantity()
        recyclerViewAdaptor.setChangedCartItemList(medicineCartItems)
    }

    fun setMedicineCartQuantity(){
        medicineCartQuantity=0
        for(cartItem in medicineCartItems){
            medicineCartQuantity+=cartItem.medicineQuantity
        }
        checkViewDisplayState()
        sendMedicineCartChanges()
    }

    fun checkViewDisplayState(){
        if(medicineCartQuantity==0){
            viewDisplay()
        }
    }

    fun sendMedicineCartChanges(){
        mMedicineCartListener.sendMedicineCartQuantity(medicineCartQuantity)
    }

    fun setMedicineCartListener(mMedicineCartListener: MedicineCartListener){
        this.mMedicineCartListener = mMedicineCartListener
    }

}
