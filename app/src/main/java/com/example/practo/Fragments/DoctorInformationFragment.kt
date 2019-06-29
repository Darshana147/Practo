package com.example.practo.Fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.example.practo.Model.Doctor
import com.example.practo.R
import com.example.practo.UseCases.DoctorDetailsUsecases
import com.example.practo.Utils.toast
import kotlinx.android.synthetic.main.fragment_doctor_information.*


private const val DOCTOR_DETAIL = "doctorDetail"

class DoctorInformationFragment : Fragment() {

    private lateinit var doctorDetail: Doctor
    private lateinit var doctorDetailsUsecases: DoctorDetailsUsecases
    private var doctorId: Int = 0
    val TAG_DOCTOR_INFO_FRAGMENT = "doctorInformationFragment"

    private var userBookMarkedList: HashSet<Int> = hashSetOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doctorId = arguments?.getInt(DOCTOR_DETAIL)!!
        doctorDetail = DoctorDetailsUsecases(context!!).getDoctorById(doctorId)!!
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
        initUseCases()
        setHasOptionsMenu(true)
        setValues()
        setListeners()
    }
    fun initUseCases(){
        doctorDetailsUsecases = DoctorDetailsUsecases(context!!)
        val doctorDetails = doctorDetailsUsecases.getAllBookmarkedDoctors()
        for(doctor in doctorDetails){
           userBookMarkedList.add(doctor.doctorId)
        }
    }

    fun customizeToolbar() {
        val actionBarSupport = (activity as AppCompatActivity).supportActionBar
        actionBarSupport?.setTitle(doctorDetail.name)
    }

    fun setListeners() {
        call_button_doctor_info.setOnClickListener {
            val phoneNumber = doctorDetail.hospitalDetails.hospitalContactNumber
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber.trim()))
            activity?.startActivity(intent)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.doctor_information_menu, menu)
        if(userBookMarkedList.contains(doctorId)) {
            menu?.findItem(R.id.doctor_information_bookmark_menu_item)?.setIcon(R.drawable.ic_bookmark_white)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.doctor_information_bookmark_menu_item -> {
                if(userBookMarkedList.contains(doctorId)){
                    item.setIcon(R.drawable.ic_bookmark)
                    userBookMarkedList.remove(doctorId)
                    context?.toast("Removed from 'My Doctors'")
                    doctorDetailsUsecases.removeDoctorFromMyDoctors(doctorId)
                }else {
                    context?.toast("Added to 'My Doctors'")
                    userBookMarkedList.add(doctorId)
                    doctorDetailsUsecases.insertDoctorToMyDoctors(doctorId)
                    item.setIcon(R.drawable.ic_bookmark_white)
                }
                return true
            }

            R.id.doctor_information_share_menu_item->{
                shareDoctorInformation()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }

    fun shareDoctorInformation(){
        val rupee = getString(R.string.rupee)
        val doctInfo:String = "${doctorDetail.name}\nSpecialization: ${doctorDetail.specialization}\n" +
                "Experience: ${doctorDetail.experience}\nConsultation fees: ${rupee} ${doctorDetail.consultationFee}\n\n" +
                "Hospital Name: ${doctorDetail.hospitalDetails.hospitalName}\nContact Number: ${doctorDetail.hospitalDetails.hospitalContactNumber}"
        val shareIntent: Intent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, doctInfo)

        activity?.startActivity(Intent.createChooser(shareIntent, "Please select an app: "))
    }

    fun setValues() {
        if (doctorDetail.gender.equals("male")) {
            doctor_image_info.setImageResource(R.drawable.ic_doctor_m)
        } else {
            doctor_image_info.setImageResource(R.drawable.ic_doctor_f)
        }
        doctor_name_info.text = doctorDetail.name
        doctor_specialization_info.text = doctorDetail.specialization
        doctor_experience_info.text = doctorDetail.experience
        doctor_consultation_fees_info.text = doctorDetail.consultationFee.toString()

        hospital_address_info.text =
            "${doctorDetail.hospitalDetails.hospitalAddress.address}, ${doctorDetail.hospitalDetails.hospitalAddress.city}, " +
                    "${doctorDetail.hospitalDetails.hospitalAddress.state}, ${doctorDetail.hospitalDetails.hospitalAddress.country}, " +
                    "${doctorDetail.hospitalDetails.hospitalAddress.pincode}"

        hospital_contact_number_info.text = doctorDetail.hospitalDetails.hospitalContactNumber

        doctor_qualification_info.text = doctorDetail.qualification
        doctor_work_experience_information.text = "${doctorDetail.experience} of Experience"
        doctor_past_experiences.text = doctorDetail.pastExperience
        doctor_services_info.text = doctorDetail.services

    }

    companion object {
        @JvmStatic
        fun newInstance(doctorDetail: Doctor) = DoctorInformationFragment().apply {
            arguments = Bundle().apply {
                putInt(DOCTOR_DETAIL, doctorDetail.doctorId)
            }
        }
    }

}
