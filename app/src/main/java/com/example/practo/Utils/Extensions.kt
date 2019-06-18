package com.example.practo.Utils

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.practo.Fragments.NoNetworkConnectionDialogFragment
import com.example.practo.R

fun Context.toast(msg:String,duration:Int = Toast.LENGTH_SHORT){
    val toast = Toast.makeText(this, msg,
        duration)
    val toastView = toast.view
    val toastMessage = toastView.findViewById<TextView>(android.R.id.message)
    toastMessage.setTextColor(Color.WHITE)
    toastView.getBackground().setColorFilter(resources.getColor(R.color.gray), PorterDuff.Mode.SRC_IN);
    toast.show()
}


fun Context.setDialogFragment(fragContext:Fragment,fragManager:FragmentManager?,tag:String,dialogFragment:DialogFragment){
    fragManager?.let{
        val fragmentTransaction = it.beginTransaction()
        val prevDialog = it.findFragmentByTag(tag)
        prevDialog?.let {
            fragmentTransaction.remove(it).commit()
        }
        dialogFragment.setTargetFragment(fragContext,1)
        dialogFragment.show(fragManager,tag)
    }
}