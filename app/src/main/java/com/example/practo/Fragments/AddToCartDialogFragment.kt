package com.example.practo.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import com.example.practo.R
import java.lang.ClassCastException

class AddToCartDialogFragment:DialogFragment() {

    interface OnInputSelected{
        fun sendItemQtyInputFromCartDialogFragment(input:String)
    }
    private lateinit var mOnInputSelected:OnInputSelected
    private lateinit var rootView:View
    private lateinit var listView: ListView
    private lateinit var cancelImv:ImageView
    private lateinit var qtyArray:ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var args:Bundle = arguments!!
        qtyArray = args.getStringArrayList("qty")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.add_to_cart_custom_dialog_layout,container)
        initViews()
        var listAdapter = ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,qtyArray)
        listView.adapter = listAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
            //to-do
            var value=parent.getItemAtPosition(position).toString()
            if(value!=null&&(!value.equals("Remove Item"))) {
                qtyArray.add(0,"Remove Item")
                listAdapter.notifyDataSetChanged()
                mOnInputSelected.sendItemQtyInputFromCartDialogFragment(value)
            }
            dialog.dismiss()
        }

        cancelImv.setOnClickListener {
            dialog.dismiss()
        }

        return rootView
    }

    fun initViews(){
        listView = rootView.findViewById(R.id.cart_dialog_list_view)
        cancelImv = rootView.findViewById(R.id.cancel_imv)
    }

    fun sendValue(value:Any){

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try{
            mOnInputSelected = targetFragment as OnInputSelected
        }catch(e:ClassCastException){
            Log.e("AddToCartDialogFragment","msg: ClassCastException ${e.message}")
        }
    }

}