package com.example.practo.Fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.R

class ProgressCustomDialog : DialogFragment() {

    private lateinit var rootView:View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            rootView = inflater.inflate(R.layout.progress_layout, container)
            return rootView
    }
}