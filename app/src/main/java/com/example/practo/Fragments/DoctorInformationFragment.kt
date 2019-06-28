package com.example.practo.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.example.practo.Model.Doctor
import com.example.practo.R
import com.example.practo.Utils.toast

private const val DOCTOR_DETAIL = "doctorDetail"

class DoctorInformationFragment : Fragment() {

    private lateinit var doctorDetail: Doctor
    val TAG_DOCTOR_INFO_FRAGMENT = "doctorInformationFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         doctorDetail = arguments?.getSerializable(DOCTOR_DETAIL) as Doctor
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_information, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        customizeToolbar()
        setHasOptionsMenu(true)
    }

    fun customizeToolbar(){
        val actionBarSupport = (activity as AppCompatActivity).supportActionBar
        actionBarSupport?.setTitle(doctorDetail.name)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.doctor_information_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.doctor_information_bookmark_menu_item->{
                context?.toast("bookMarkselected")
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }

    companion object{
        @JvmStatic
        fun newInstance(doctorDetail: Doctor) = DoctorInformationFragment().apply {
            arguments = Bundle().apply {
                putSerializable(DOCTOR_DETAIL,doctorDetail)
            }
        }
    }


}
