package com.example.practo.Model

import java.time.LocalDateTime
import java.util.*

// class MedicineOrder(var medicineCart: MedicineCart,var deliveryAddressDetails: UserMedicineDeliveryAddressDetails){
//     var orderId:Int = 0
//     constructor(orderId: Int,medicineCart: MedicineCart,deliveryAddressDetails: UserMedicineDeliveryAddressDetails):this(medicineCart,deliveryAddressDetails){
//       this.orderId = orderId
//     }
// }

 class MedicineOrder(var medicineCart: MedicineCart,var deliveryAddressDetails: UserMedicineDeliveryAddressDetails,var deliveryDate:String, var orderedDate:String, var isDelivered:Boolean){
     var orderId:Int = 0
     constructor(orderId:Int,medicineCart: MedicineCart,deliveryAddressDetails: UserMedicineDeliveryAddressDetails,deliveryDate: String,orderedDate: String,isDelivered: Boolean):this(medicineCart,deliveryAddressDetails,deliveryDate,orderedDate,isDelivered){
         this.orderId = orderId
     }
 }

// object MedicineOrderSupplier{
//    val orders = arrayListOf<MedicineOrder>()
//}

