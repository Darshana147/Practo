package com.example.practo.Fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.example.practo.R
import com.example.practo.Utils.Validation
import kotlinx.android.synthetic.main.add_to_cart_custom_dialog.*
import java.lang.ClassCastException

private const val MEDICINE_PRICE = "param1"
private const val EXISTING_QUANTITY = "param2"

class AddToCartCustomDialog : DialogFragment() {

    interface OnQtyEntered {
        fun getQtyEntered(qty: Int)
    }

    private lateinit var rootView: View
    private lateinit var mOnQtyEntered: OnQtyEntered
    private lateinit var okButton: TextView
    private lateinit var cancelButton: TextView
    private lateinit var medicinePriceTxv: TextView
    private lateinit var totalMedicinePriceTxv: TextView
    private lateinit var cartQtyTxv: TextInputEditText
    private lateinit var cartQtyTil: TextInputLayout
    private var medPrice: Double = 0.0
    private var medQty: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val args:Bundle = arguments!!
//        medPrice = args.getDouble("medPrice")
//        medQty = args.getInt("existingQty")

        arguments?.let {
            medPrice = it.getDouble(MEDICINE_PRICE)
            medQty = it.getInt(EXISTING_QUANTITY)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.add_to_cart_custom_dialog, container)
        initViews()
        textListener(cartQtyTxv, cartQtyTil)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        okButton.setOnClickListener {
            if (!isValidInput()) {
                cart_qty_til.setError("Enter valid quantity, max limit '30'")
            } else {
                mOnQtyEntered.getQtyEntered(cart_qty_edit_txt.text.toString().toInt())
                dialog.dismiss()
            }
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        return rootView
    }

    fun initViews() {
        cartQtyTxv = rootView.findViewById(R.id.cart_qty_edit_txt)
        cartQtyTil = rootView.findViewById(R.id.cart_qty_til)
        cartQtyTxv.requestFocus()
        medicinePriceTxv = rootView.findViewById(R.id.med_price_txv)
        totalMedicinePriceTxv = rootView.findViewById(R.id.med_total_price_txv)
        medicinePriceTxv.text = medPrice.toString()
        if (medQty != 0) {
            cartQtyTxv.setText(medQty.toString())
            totalMedicinePriceTxv.text = (Math.round(medPrice * medQty.toDouble() * 100.0) / 100.0).toString()
            cartQtyTxv.requestFocus()
        } else {
            totalMedicinePriceTxv.text = " -"
        }
        okButton = rootView.findViewById(R.id.ok_txv)
        cancelButton = rootView.findViewById(R.id.cancel_txv)
    }

    fun isValidInput(): Boolean {
        return (!Validation.isEmpty(cart_qty_edit_txt.text.toString()) && Validation.isValidNumber(cart_qty_edit_txt.text.toString()) && Validation.isWithinValidRange(
            1,
            30,
            cart_qty_edit_txt.text.toString()
        ))
    }

    fun textListener(editText: TextInputEditText, editTextInputlayout: TextInputLayout) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (isValidInput()) {
                    if (Validation.isEmpty(editText.text.toString())) {
                        med_total_price_txv.text = " -"
                    }
                    editTextInputlayout.error = null
                    med_total_price_txv.text =
                        (Math.round(medPrice * editText.text.toString().toDouble() * 100.0) / 100.0).toString()
                } else {
                    if (!Validation.isWithinValidRange(1, 30, editText.text.toString())) {
                        editTextInputlayout.setError("Enter valid quantity, max limit '30'")
                    }
                    med_total_price_txv.text = " -"
                }
            }

        })

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mOnQtyEntered = targetFragment as OnQtyEntered
        } catch (e: ClassCastException) {
            Log.e("AddToCartDialogFragment", "msg: ClassCastException ${e.message}")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Double, param2: Int = 0) =
            AddToCartCustomDialog().apply {
                arguments = Bundle().apply {
                    putDouble(MEDICINE_PRICE, param1)
                    putInt(EXISTING_QUANTITY, param2)
                }
            }
    }
}