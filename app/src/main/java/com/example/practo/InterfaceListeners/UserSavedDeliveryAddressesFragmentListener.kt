package com.example.practo.InterfaceListeners

import com.example.practo.Model.UserMedicineDeliveryAddressDetails

interface UserSavedDeliveryAddressesFragmentListener {
    fun onAddNewAddressClicked()

    fun onDeliverHereBtnClicked(addressDetails: UserMedicineDeliveryAddressDetails)

    fun onEditAddressClicked(addressDetails:UserMedicineDeliveryAddressDetails)

}