package com.example.practo.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.CheckBox
import com.example.practo.Adapters.BookmarkedDoctorsRecyclerAdapter
import com.example.practo.InterfaceListeners.BackPressedListener
import com.example.practo.InterfaceListeners.BookmarkedDoctorRecyclerAdapterListener
import com.example.practo.InterfaceListeners.MyDoctorsListener
import com.example.practo.Model.Doctor
import com.example.practo.R
import com.example.practo.UseCases.DoctorDetailsUsecases
import com.example.practo.Utils.toast
import kotlinx.android.synthetic.main.activity_my_doctors.*
import kotlinx.android.synthetic.main.activity_my_doctors.view.*
import kotlinx.android.synthetic.main.fragment_my_doctors.*
import kotlinx.android.synthetic.main.fragment_my_doctors.view.*


class MyDoctorsFragment : Fragment(), BackPressedListener,BookmarkedDoctorRecyclerAdapterListener{

    private lateinit var mMyDoctorsListener: MyDoctorsListener
    private lateinit var doctorDetailsUsecases: DoctorDetailsUsecases
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerViewAdapter: BookmarkedDoctorsRecyclerAdapter
    private lateinit var bookmarkedDoctors: ArrayList<Doctor>
    private lateinit var rootView: View
    private var selectedDoctorList: ArrayList<Doctor> = arrayListOf()
    var is_in_edit_mode = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my_doctors, container, false)
        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        customizeToolbar()
        initUsecases()
        bookmarkedDoctors = doctorDetailsUsecases.getAllBookmarkedDoctors()
        initListeners()
        viewDisplay(rootView, bookmarkedDoctors)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        if (!bookmarkedDoctors.isEmpty()) {
            inflater?.inflate(R.menu.my_doctors_menu_toolbar, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.edit_bookmarked_doctors_menu_item -> {
                onEditMenuitemClicked(item)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun onEditMenuitemClicked(item: MenuItem) {
        if (item.title.toString().toLowerCase().trim().equals("edit")) {
            is_in_edit_mode = true
            search_doctors_button.setText("Remove")
            search_doctors_button.setBackgroundColor(ContextCompat.getColor(context!!, R.color.red))
            item.title = "Cancel"
            recyclerViewAdapter.notifyDataSetChanged()

        } else {
            item.title = "Edit"
            is_in_edit_mode = false
            search_doctors_button.setText("Search Doctors")
            search_doctors_button.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorAccent))
            recyclerViewAdapter.notifyDataSetChanged()
        }
    }

    fun initUsecases() {
        doctorDetailsUsecases = DoctorDetailsUsecases(context!!)
    }

    fun customizeToolbar() {
        val activity = getActivity() as AppCompatActivity
        val actionBarSupport = activity.supportActionBar
        actionBarSupport?.setTitle("My Doctors")
    }

    fun initListeners() {
        search_doctors_button.setOnClickListener {
            if (is_in_edit_mode) {
                if(selectedDoctorList.isEmpty()){
                    context?.toast("Please select doctor(s) to remove")
                }else {
                    removeSelectedDoctors()
                    recyclerViewAdapter.onUpdateRecyclerView(bookmarkedDoctors)
                    clearActionMode()
                }
            } else {
                mMyDoctorsListener.onSearchDoctorsClicked()
            }
        }
    }

    fun removeSelectedDoctors() {
        for (doctor in selectedDoctorList) {
            bookmarkedDoctors.remove(doctor)
        }
        doctorDetailsUsecases.removeSelectedListFromDb(selectedDoctorList)
    }


    fun clearActionMode() {
        is_in_edit_mode = false
        if (!bookmarkedDoctors.isEmpty()) {
            setVisibility(View.INVISIBLE, View.VISIBLE)
            activity?.activity_my_doctors_toolbar?.menu?.clear()
            activity?.activity_my_doctors_toolbar?.inflateMenu(R.menu.my_doctors_menu_toolbar)
        } else {
            setVisibility(View.VISIBLE, View.INVISIBLE)
            activity?.activity_my_doctors_toolbar?.menu?.clear()

        }
        selectedDoctorList.clear()
        search_doctors_button.setText("Search Doctorrs")
        search_doctors_button.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorAccent))
    }


    fun setMyDoctorListener(mMyDoctorsListener: MyDoctorsListener) {
        this.mMyDoctorsListener = mMyDoctorsListener
    }


    fun initRecyclerView(rootView: View) {
        recyclerView = rootView.findViewById<RecyclerView>(R.id.my_doctors_recycler_view)
        recyclerView.setHasFixedSize(true);
    }

    fun initLayoutManager() {
        layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
    }

    fun bindRecyclerViewWithLayoutManager(bookmarkedDoctorList: ArrayList<Doctor>) {
        recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = BookmarkedDoctorsRecyclerAdapter(context!!, bookmarkedDoctorList, this,this)
        recyclerView.adapter = recyclerViewAdapter

    }


    fun viewDisplay(rootView: View, bookmarkedDoctorList: ArrayList<Doctor>) {
        if (bookmarkedDoctorList.isEmpty()) {
            setVisibility(View.VISIBLE, View.INVISIBLE)

        } else {
            setVisibility(View.INVISIBLE, View.VISIBLE)
            Thread.sleep(100)
            initRecyclerView(rootView)
            initLayoutManager()
            activity?.runOnUiThread(object : Runnable {
                override fun run() {
                    bindRecyclerViewWithLayoutManager(bookmarkedDoctorList)
                }

            })
        }
    }

    fun setVisibility(noBookMarkedDoctorsView: Int, bookmarkedDoctorsView: Int) {
        activity?.runOnUiThread(object : Runnable {
            override fun run() {
                rootView.no_bookmarked_doctors_linear_layout.visibility = noBookMarkedDoctorsView
                rootView.bookmarked_doctors_linear_layout.visibility = bookmarkedDoctorsView
            }

        })
    }


    fun prepareSelectionList(view: CheckBox, position: Int) {
        if (view.isChecked) {
            selectedDoctorList.add(bookmarkedDoctors.get(position))
        } else {
            selectedDoctorList.remove(bookmarkedDoctors.get(position))
        }
    }


    override fun onBackPressed(): Boolean {
        if (is_in_edit_mode) {
            clearActionMode()
            recyclerViewAdapter.notifyDataSetChanged()
            return true
        } else {
            return false
        }
    }

    override fun onDoctorClicked(doctor: Doctor) {
        mMyDoctorsListener.onBookmarkedDoctorClicked(doctor)
    }



}
