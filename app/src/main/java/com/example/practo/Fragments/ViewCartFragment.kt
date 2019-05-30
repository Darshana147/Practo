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
import android.widget.TextView
import android.widget.Toast
import com.example.practo.Adapters.MedicineCartRecyclerAdaptor
import com.example.practo.InterfaceListeners.OnChangeCartItemQtyListener
import com.example.practo.InterfaceListeners.ViewCartFragmentListener
import com.example.practo.Model.MedicineCartItem
import com.example.practo.R
import com.example.practo.UseCases.MedicineCartUseCases


class ViewCartFragment : Fragment(),OnChangeCartItemQtyListener,AddToCartDialogFragment.OnInputSelected{


    private lateinit var rootView:View
    private lateinit var emptyCartView:LinearLayout
    private lateinit var cartNotEmptyView:LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdaptor:MedicineCartRecyclerAdaptor
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var medicineCartTotalItemCountTxv:TextView
    private lateinit var medicineCartTotalAmountTxv:TextView
    private lateinit var medicineCartUseCases: MedicineCartUseCases
    private lateinit var medicineCartItems:ArrayList<MedicineCartItem>
    private lateinit var addMedicinesToCartBtn:Button
    private lateinit var checkOutBtn:Button
    private lateinit var viewCartFragmentListener: ViewCartFragmentListener
    private var medicineId:Int=0


        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_view_cart, container, false)
        customizeToolbar()
        initViews()
        initUseCases()
        initOnSelectListeners()
        initRecyclerView()
        initLayoutManager()
        viewDisplay()
        return rootView
    }

    fun initUseCases(){
        medicineCartUseCases = MedicineCartUseCases(this.context!!)
        medicineCartItems = medicineCartUseCases.getMedicineItemsFromCart()
    }


    fun initViews(){
        emptyCartView = rootView.findViewById(R.id.cart_emtpy_view)
        cartNotEmptyView = rootView.findViewById(R.id.cart_not_empty_view)
        medicineCartTotalItemCountTxv = rootView.findViewById(R.id.cart_total_item_count)
        medicineCartTotalAmountTxv = rootView.findViewById<TextView>(R.id.cart_total_amount)
        addMedicinesToCartBtn = rootView.findViewById(R.id.add_medicines_to_empty_cart_btn)
        checkOutBtn = rootView.findViewById(R.id.CheckOutBtn)
    }

    fun initOnSelectListeners(){
        addMedicinesToCartBtn.setOnClickListener {
            viewCartFragmentListener.onAddMedicinesBtnFromEmptyCartClicked()
        }
        checkOutBtn.setOnClickListener {
            viewCartFragmentListener.onCheckOutBtnClicked()
        }
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
            setMedicineCartQuantity()
            setMedicineCartTotalAmount()
            applyChangesInCartTotalItemCount()
            applyChangesInCartTotalAmount()
            cartNotEmptyView.visibility= View.VISIBLE
        }
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
        medicineCartUseCases.removeMedicineItemFromCart(medicineId)
        Toast.makeText(context,"Item Removed", Toast.LENGTH_SHORT).show()
        setMedicineCartTotalAmount()
        setMedicineCartQuantity()
        applyChangesInCartTotalAmount()
        applyChangesInCartTotalItemCount()
        recyclerViewAdaptor.setChangedCartItemList(medicineCartItems)
    }


    override fun sendItemQtyInputFromCartDialogFragment(input: String) {
        var index=0
        lateinit var changedCartItem:MedicineCartItem
        for(cartItem in medicineCartItems){
            if(cartItem.medicine.medicineId == medicineId){
                changedCartItem = cartItem
                changedCartItem.medicineQuantity = input.toInt()
                index = medicineCartItems.indexOf(cartItem)
                break
            }
        }
        medicineCartItems.set(index,changedCartItem)
        medicineCartUseCases.changeMedicineCartItemQuantity(medicineId,input.toInt())
        setMedicineCartTotalAmount()
        setMedicineCartQuantity()
        applyChangesInCartTotalAmount()
        applyChangesInCartTotalItemCount()
        recyclerViewAdaptor.setChangedCartItemList(medicineCartItems)
    }

    fun applyChangesInCartTotalItemCount(){
        medicineCartTotalItemCountTxv.text = medicineCartUseCases.getCartTotalQuantity().toString()+" Items"
    }

    fun applyChangesInCartTotalAmount(){
        medicineCartTotalAmountTxv.text = medicineCartUseCases.getCartTotalPrice().toString()
    }

    fun setMedicineCartQuantity(){
        medicineCartUseCases.updateCartTotalQuantity()
        checkViewDisplayState()
    }

    fun setMedicineCartTotalAmount(){
        medicineCartUseCases.updateCartTotalPrice()
    }

    fun checkViewDisplayState(){
        if(medicineCartUseCases.getCartTotalQuantity()==0){
            viewDisplay()
        }
    }

    fun setViewCartFragmentListener(viewCartFragmentListener: ViewCartFragmentListener){
        this.viewCartFragmentListener = viewCartFragmentListener
    }

}
