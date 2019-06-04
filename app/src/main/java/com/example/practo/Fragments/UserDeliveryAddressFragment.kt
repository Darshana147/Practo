package com.example.practo.Fragments


import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.practo.InterfaceListeners.UserDeliveryAddressFragmentListener
import com.example.practo.Model.UserDeliveryAddressStorage
import com.example.practo.Model.UserMedicineDeliveryAddressDetails

import com.example.practo.R
import com.example.practo.Utils.Validation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import kotlinx.android.synthetic.main.fragment_favorite_list.view.*
import kotlinx.android.synthetic.main.fragment_user_delivery_address.*
import java.util.*

class UserDeliveryAddressFragment : Fragment(){

    private lateinit var rootView:View
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest:LocationRequest
    private lateinit var geoCoder:Geocoder
    private lateinit var mUserDeliveryAddressFragmentListener: UserDeliveryAddressFragmentListener
    private lateinit var address: List<Address>
    private lateinit var userCity:TextInputEditText
    private lateinit var userState:TextInputEditText
    private lateinit var userPostalCode:TextInputEditText
    private lateinit var getUserLocationBtn:Button
    private lateinit var userLocality:TextInputEditText
    private lateinit var userCountry:TextInputEditText
    private lateinit var cancelBtn:Button
    private lateinit var saveBtn:Button
    private lateinit var userName:TextInputEditText
    private lateinit var userMobileNumber:TextInputEditText
    private lateinit var userAddress:TextInputEditText
    private lateinit var typeOfAddress:RadioGroup
    private lateinit var userNameTextInputLayout:TextInputLayout
    private lateinit var userMobileTextInputLayout: TextInputLayout
    private lateinit var userPostalCodeTextInputLayout: TextInputLayout
    private lateinit var userAddresTextInputLayout: TextInputLayout
    private lateinit var userLocalityTextInputLayout: TextInputLayout
    private lateinit var userCityTextInputLayout: TextInputLayout
    private lateinit var userStateTextInputLayout: TextInputLayout
    private lateinit var userCountryTextInputLayout: TextInputLayout

    private var userAddressDetails:UserMedicineDeliveryAddressDetails? = null
    private var editDetails:Boolean=false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_user_delivery_address, container, false)
        customizeToolbar()
        initViews()
        initListeners()
        return rootView
    }

    override fun onResume() {
        super.onResume()
        if (userAddressDetails != null) {
            editDetails=true
            assignValuesToBeEdited()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        userAddressDetails=null
        editDetails=false
    }

    fun assignValuesToBeEdited(){
        userName.setText(userAddressDetails?.userName)
        userMobileNumber.setText(userAddressDetails?.userMobileNumber)
        userPostalCode.setText(userAddressDetails?.userPinCode)
        userAddress.setText(userAddressDetails?.userAddress)
        userLocality.setText(userAddressDetails?.userLocality)
        userCity.setText(userAddressDetails?.userCity)
        userState.setText(userAddressDetails?.userState)
        userCountry.setText(userAddressDetails?.userCountry)
    }


    fun customizeToolbar(){
        var activity = getActivity() as AppCompatActivity
        var actionBarSupport = activity.supportActionBar
        actionBarSupport?.setTitle("Address")
    }


    fun initViews(){
        userCity = rootView.findViewById(R.id.user_city_edit_text)
        userState = rootView.findViewById(R.id.user_state_edit_text)
        getUserLocationBtn = rootView.findViewById(R.id.get_user_location_btn)
        userPostalCode = rootView.findViewById(R.id.user_pin_code_edit_text)
        userLocality = rootView.findViewById(R.id.user_locality_edit_text)
        userCountry = rootView.findViewById(R.id.user_country_edit_text)
        cancelBtn = rootView.findViewById(R.id.cancel_btn)
        saveBtn = rootView.findViewById(R.id.save_btn)
        userName = rootView.findViewById(R.id.user_name_edit_text)
        userNameTextInputLayout = rootView.findViewById(R.id.user_name_til)
        userAddress = rootView.findViewById(R.id.user_address_edit_text)
        userMobileNumber = rootView.findViewById(R.id.user_mobile_number_edit_text)
        userMobileTextInputLayout = rootView.findViewById(R.id.user_mobile_number_til)
        userPostalCodeTextInputLayout = rootView.findViewById(R.id.user_pin_code_til)
        userAddresTextInputLayout = rootView.findViewById(R.id.user_address_til)
        userLocalityTextInputLayout = rootView.findViewById(R.id.user_locality_til)
        userCityTextInputLayout = rootView.findViewById(R.id.user_city_til)
        userStateTextInputLayout = rootView.findViewById(R.id.user_state_til)
        userCountryTextInputLayout = rootView.findViewById(R.id.user_country_til)
        typeOfAddress = rootView.findViewById(R.id.type_of_address_radio_group)
    }

    fun initListeners(){
        getUserLocationBtn.setOnClickListener {
            callPermissions()
        }
        cancelBtn.setOnClickListener {
            resetAllUserDetails()
        }
        saveBtn.setOnClickListener {
            checkFieldsIfEmpty()
        }
        setTextListener()

    }

    fun setTextListener(){
        textListener(userName,userNameTextInputLayout)
        textListener(userMobileNumber,userMobileTextInputLayout)
        textListener(userPostalCode,userPostalCodeTextInputLayout)
        textListener(userAddress,userAddresTextInputLayout)
        textListener(userLocality,userLocalityTextInputLayout)
        textListener(userCity,userCityTextInputLayout)
        textListener(userState,userStateTextInputLayout)
        textListener(userCountry,userCountryTextInputLayout)
    }

    fun checkFieldsIfEmpty(){
        var noEmptyField=true
        if(Validation.isEmpty(userCountry.text.toString())){
            noEmptyField=false
            userCountry.requestFocus()
            userCountryTextInputLayout.error="Please enter city"
        }
        if(Validation.isEmpty(userState.text.toString())){
            noEmptyField=false
            userState.requestFocus()
            userStateTextInputLayout.error="Please enter city"
        }
        if(Validation.isEmpty(userCity.text.toString())){
            noEmptyField=false
            userCity.requestFocus()
            userCityTextInputLayout.error="Please enter city"
        }
        if(Validation.isEmpty(userLocality.text.toString())){
            noEmptyField=false
            userLocality.requestFocus()
            userLocalityTextInputLayout.error="Please enter locality"
        }
        if(Validation.isEmpty(userAddress.text.toString())){
            noEmptyField=false
            userAddress.requestFocus()
            userAddresTextInputLayout.error="Please enter address"
        }
        if(Validation.isEmpty(userPostalCode.text.toString())){
            noEmptyField=false
            userPostalCode.requestFocus()
            userPostalCodeTextInputLayout.error="Please enter pincode"
        }
        if(Validation.isEmpty(userMobileNumber.text.toString())){
            noEmptyField=false
            userMobileNumber.requestFocus()
            userMobileTextInputLayout.error="Please enter mobile number"
        }
        if(Validation.isEmpty(userName.text.toString())){
            noEmptyField=false
            userName.requestFocus()
            userNameTextInputLayout.setError(("Please enter Name"))
       }
        if(noEmptyField==true){
            if(editDetails==false) {
                saveUserDetails()
            }else{
                updateUserDetails()
            }
            mUserDeliveryAddressFragmentListener.onSaveButtonClicked()
        }

    }

    fun getUserDetails():UserMedicineDeliveryAddressDetails{
        var name=userName.text.toString()
        var mobileNumber = userMobileNumber.text.toString()
        var pincode = userPostalCode.text.toString()
        var address = userAddress.text.toString()
        var locality = userLocality.text.toString()
        var city = userCity.text.toString()
        var state = userState.text.toString()
        var country = userCountry.text.toString()
        var typeOfAddress =  rootView.findViewById<RadioButton>(typeOfAddress.getCheckedRadioButtonId()).text.toString()
        return UserMedicineDeliveryAddressDetails(name,mobileNumber,pincode,address,locality,city,state,country,typeOfAddress)

    }

    fun updateUserDetails(){
        var index =UserDeliveryAddressStorage.userDeliveryAddress.indexOf(userAddressDetails)
        Toast.makeText(context,index.toString(),Toast.LENGTH_SHORT).show()
        UserDeliveryAddressStorage.userDeliveryAddress.remove(userAddressDetails)
        UserDeliveryAddressStorage.userDeliveryAddress.add(index,getUserDetails())
    }


    fun saveUserDetails(){
        UserDeliveryAddressStorage.userDeliveryAddress.add(getUserDetails())
        Toast.makeText(context,"Details Saved",Toast.LENGTH_SHORT).show()
    }



    fun callPermissions(){
        val permissions =
            arrayOf<String>(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        Permissions.check(this.context/*context*/, permissions, "Location needed"/*options*/, Permissions.Options()
            .setSettingsDialogTitle("Warning!")
            .setRationaleDialogTitle("Location Permission"),
            object : PermissionHandler() {
                override fun onGranted() {
                    // do your task.
                    requestLocationUpdates()
                }

                override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
                    super.onDenied(context, deniedPermissions)
                    callPermissions()
                }

            })
    }

    fun requestLocationUpdates() {

        if (ContextCompat.checkSelfPermission(
                this.context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PermissionChecker.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this.context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PermissionChecker.PERMISSION_GRANTED
        ) {

            fusedLocationProviderClient = FusedLocationProviderClient(this.context!!)
            geoCoder = Geocoder(this.context, Locale.getDefault())
            locationRequest = LocationRequest()
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            locationRequest.setFastestInterval(1000)

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult?) {
                    super.onLocationResult(p0)
                    if (isNetworkConnected()) {

                        address = geoCoder.getFromLocation(p0!!.lastLocation.latitude, p0!!.lastLocation.longitude, 1)

                        userCity.setText(address.get(0).subAdminArea.toString())
                        userState.setText(address.get(0).adminArea.toString())
                        userPostalCode.setText(address.get(0).postalCode)
                        userCountry.setText(address.get(0).countryName)
                    } else {
                        setDialogFragment()

                    }

                }
            }, Looper.getMainLooper())
        } else {
            callPermissions()
        }
    }

    fun resetAllUserDetails(){
        userCity.setText(null)
        userState.setText(null)
        userPostalCode.setText(null)
        userCountry.setText(null)
        userName.setText(null)
        userAddress.setText(null)
        userMobileNumber.setText(null)
        userLocality.setText(null)

        userNameTextInputLayout.error=null
        userCityTextInputLayout.error=null
        userStateTextInputLayout.error=null
        userAddresTextInputLayout.error=null
        userPostalCodeTextInputLayout.error=null
        userCountryTextInputLayout.error=null
        userMobileTextInputLayout.error=null
        userLocalityTextInputLayout.error=null
    }



    override fun onPause() {
        super.onPause()
        resetAllUserDetails()
    }

    fun isNetworkConnected():Boolean{
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        val connectionManager : ConnectivityManager
        if(cm != null){
            connectionManager = cm as ConnectivityManager
        }
        else{
            Toast.makeText(context,"Try again",Toast.LENGTH_SHORT).show()
            return false
        }
        if(connectionManager!=null) {
            val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        } else{
            Toast.makeText(context,"Try again",Toast.LENGTH_SHORT).show()
            return false
        }

    }


    fun setDialogFragment(){
        var fragmentTransaction = fragmentManager!!.beginTransaction()
        var prevDialog = fragmentManager!!.findFragmentByTag("noNetworkConnectionDialogFragment")
        if(prevDialog!=null){
            fragmentTransaction.remove(prevDialog)
        }
        var dialogFragment = NoNetworkConnectionDialogFragment()
        dialogFragment.setTargetFragment(this,1)
        dialogFragment.show(fragmentManager,"noNetworkConnectionDialogFragment")
    }


    fun textListener(editText:TextInputEditText,editTextInputlayout:TextInputLayout){
        editText.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
               if(!Validation.isEmpty(editText.text.toString())){
                   editTextInputlayout.error=null
               }
            }

        })

    }

    fun setUserDeliveryAddressFragmentListener(mUserDeliveryAddressFragmentListener: UserDeliveryAddressFragmentListener){
        this.mUserDeliveryAddressFragmentListener=mUserDeliveryAddressFragmentListener
    }


    fun editAddressDetails(addressDetails:UserMedicineDeliveryAddressDetails){
        userAddressDetails = addressDetails
    }

}
