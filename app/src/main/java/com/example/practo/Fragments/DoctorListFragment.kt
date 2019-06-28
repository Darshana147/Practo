package com.example.practo.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practo.Adapters.DoctorListRecyclerAdapter
import com.example.practo.Adapters.SearchMedicineRecyclerAdaptor
import com.example.practo.InterfaceListeners.DoctorListAdapterListener
import com.example.practo.InterfaceListeners.DoctorListFragmentListener
import com.example.practo.Model.Doctor
import com.example.practo.R
import com.example.practo.UseCases.DoctorDetailsUsecases
import kotlinx.android.synthetic.main.fragment_doctor_list.*

private const val SPECIALIZATION = "specialization"

class DoctorListFragment : Fragment(),DoctorListAdapterListener{

    val TAG_DOCTOR_LIST_FRAGMENT = "doctor_list_frag"
    private var specialization: String? =null
    private lateinit var recyclerViewAdaptor: DoctorListRecyclerAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var mDoctorListFragmentListener:DoctorListFragmentListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            specialization = it.getString(SPECIALIZATION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        customizeToolbar()
        initRecyclerView()
        initLayoutManager()
        bindRecyclerViewWithAdapter()
    }

    fun customizeToolbar(){
        val activity = getActivity() as AppCompatActivity
        val actionBarSupport = activity.supportActionBar
        actionBarSupport?.setTitle("Specialization")
    }

    fun initRecyclerView() {
        doctor_list_recycler_view.setHasFixedSize(true);
    }

    fun initLayoutManager() {
        layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
    }

    fun bindRecyclerViewWithAdapter() {
        doctor_list_recycler_view.layoutManager = layoutManager
        recyclerViewAdaptor =
            DoctorListRecyclerAdapter(this.context!!,getDoctorDetailsBySpecialization(),this)
        doctor_list_recycler_view.adapter = recyclerViewAdaptor
    }

    fun getDoctorDetailsBySpecialization():ArrayList<Doctor>{
      return DoctorDetailsUsecases(context!!).getDoctorDetailsBySpecialization(specialization!!)
    }

    override fun onDoctorSelectedFromList(doctor: Doctor) {
       mDoctorListFragmentListener.onDoctorSelectedFromList(doctor)
    }

    fun setDoctorListFragmentListener(listener: DoctorListFragmentListener){
        mDoctorListFragmentListener = listener
    }

    companion object{
        @JvmStatic
        fun newInstance(specialization:String) = DoctorListFragment().apply {
            arguments = Bundle().apply {
                putString(SPECIALIZATION,specialization)
            }
        }
    }

}
