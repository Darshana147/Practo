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
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.practo.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import java.util.*

class UserDeliveryAddressFragment : Fragment(){

    private lateinit var rootView:View
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest:LocationRequest
    private lateinit var geoCoder:Geocoder
    private lateinit var address: List<Address>
    private lateinit var userCity:EditText
    private lateinit var userState:EditText
    private lateinit var userPostalCode:EditText
    private lateinit var getUserLocationBtn:Button
    private lateinit var userLocality:EditText
    private lateinit var userCountry:EditText
    private lateinit var cancelBtn:Button
    private lateinit var saveBtn:Button
    private lateinit var userName:EditText
    private lateinit var userNameTextInputLayout:TextInputLayout
    private lateinit var city: String
    private lateinit var state: String
    private lateinit var pincode: String
    private lateinit var country:String

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
    }

    fun checkFieldsIfEmpty(){
        var noEmptyField=true
        if(userName.getText().toString().equals("")){
            noEmptyField=false
            userName.setError(("Enter First Name"))
       }
// else if(){
//
//        }
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
            geoCoder = Geocoder(this.context, Locale.ENGLISH)
            locationRequest = LocationRequest()
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            locationRequest.setFastestInterval(1000)

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult?) {
                    super.onLocationResult(p0)
                    if (isNetworkConnected()) {

                        address = geoCoder.getFromLocation(p0!!.lastLocation.latitude, p0!!.lastLocation.longitude, 1)
                        city = address.get(0).subAdminArea.toString()
                        state = address.get(0).adminArea.toString()
                        pincode = address.get(0).postalCode
                        country = address.get(0).countryName

                        userCity.setText(city)
                        userState.setText(state)
                        userPostalCode.setText(pincode)
                        userCountry.setText(country)
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
    }



    override fun onPause() {
        super.onPause()
        resetAllUserDetails()
    }

    fun isNetworkConnected():Boolean{
        val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true

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


}
