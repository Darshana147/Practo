package com.example.practo.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.Adapters.SearchDoctorRecyclerAdapter
import com.example.practo.InterfaceListeners.SearchDoctorFragmentListener
import com.example.practo.InterfaceListeners.SearchDoctorsAdapterListener
import com.example.practo.Model.DoctorDetailsSupplier
import com.example.practo.R
import com.example.practo.UseCases.DoctorDetailsUsecases
import java.io.Serializable


class SearchDoctorsFragment : Fragment(),SearchDoctorsAdapterListener{

    val Tag_SEARCH_DOCTORS = "search_doctors_fragment"
    private lateinit var rootView:View
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdaptor: SearchDoctorRecyclerAdapter
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var mSearchDoctorsFragmentListener:SearchDoctorFragmentListener
    private val specialistsList = arrayListOf<String>("Child Specialist","Dental care","General Physician","Ear Nose Throat","Bone and Joints","Eye Specialist","Ayurveda","Heart","Lungs and Breathing","Cancer")
    private val imageList = arrayListOf<Int>(R.drawable.child_specialist_icon,R.drawable.ic_tooth,R.drawable.ic_stethoscope,R.drawable.ent_icon,R.drawable.knee_joint_icon,R.drawable.ic_visibility,R.drawable.ic_ayurvedic,R.drawable.ic_cardiogram,R.drawable.ic_lungs,R.drawable.ic_cancer)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_search_doctors, container, false)
        if(DoctorDetailsSupplier.doctorDetailsList.isEmpty()) {
            DoctorDetailsUsecases(context!!).getAllDoctorDetails()
        }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        customizeToolbar()
        initRecyclerView()
        initLayoutManager()
        setRecyclerView()
    }

    fun customizeToolbar(){
        val activity = getActivity() as AppCompatActivity
        val actionBarSupport = activity.supportActionBar
        actionBarSupport?.setTitle("Search Doctors")
    }

    fun initRecyclerView(){
        recyclerView = rootView.findViewById<RecyclerView>(R.id.search_doctor_recycler_view)
        recyclerView.setHasFixedSize(true);
    }

    fun initLayoutManager(){
        layoutManager = GridLayoutManager(activity,2)
    }

    fun setRecyclerView(){
        recyclerView.layoutManager = layoutManager
        recyclerViewAdaptor = SearchDoctorRecyclerAdapter(context!!,specialistsList,imageList,this)
        recyclerView.adapter = recyclerViewAdaptor
    }

    fun setSearchDoctorFragmentListener(mSearchDoctorsFragmentListener:SearchDoctorFragmentListener){
        this.mSearchDoctorsFragmentListener = mSearchDoctorsFragmentListener
    }


    override fun onSpecializationFieldSelected(specialization:String) {
       mSearchDoctorsFragmentListener.onSpecializationFieldSelected(specialization)
    }


}
