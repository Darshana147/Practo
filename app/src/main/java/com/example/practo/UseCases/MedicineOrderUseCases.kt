package com.example.practo.UseCases

import android.content.Context
import android.util.Log
import com.example.practo.DAO.MedicineOrderDetailsDAO
import com.example.practo.Model.MedicineCartItem
import com.example.practo.Model.MedicineOrder
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class MedicineOrderUseCases(context: Context){

    private val medicineOrderDetailsDAO:MedicineOrderDetailsDAO
    private val medicineCartUseCases:MedicineCartUseCases

    init {
        medicineOrderDetailsDAO = MedicineOrderDetailsDAO(context)
        medicineCartUseCases = MedicineCartUseCases(context)
    }


    fun addMedicineOrder(medicineOrder: MedicineOrder):Int{
        medicineCartUseCases.emptyCart(1)
        return medicineOrderDetailsDAO.addNewOrder(1,medicineOrder)
    }

    fun getAllOrders():ArrayList<MedicineOrder>{
        return medicineOrderDetailsDAO.getAllUserOrders(1)
    }

    fun cancelOrder(orderId:Int){
        medicineOrderDetailsDAO.removeOrder(orderId)
    }

    fun getOrderedItemsByOrderId(orderId: Int):ArrayList<MedicineCartItem>{
        return medicineOrderDetailsDAO.getOrderedItemsByOrderid(1,orderId)
    }

    fun getOrderByOrderid(orderId: Int):MedicineOrder?{
        return medicineOrderDetailsDAO.getOrderByOrderId(1,orderId)
    }

    fun getShippedDate(orderedDate: String):Date{
        val sdf = SimpleDateFormat("dd MMMM YYYY hh:mm:ss",Locale.getDefault())
        val ordDate = convertStringToDate(orderedDate)
        val myCal = Calendar.getInstance()
        myCal.time = ordDate
        myCal.add(Calendar.MINUTE,3)
        val shippmentDate = myCal.time
        return shippmentDate
    }

    fun getOutOfDeliveryDate(orderedDate: String):Date{
        val sdf = SimpleDateFormat("dd MMMM YYYY hh:mm:ss",Locale.getDefault())
        val ordDate = convertStringToDate(orderedDate)
        val myCal = Calendar.getInstance()
        myCal.time = ordDate
        myCal.add(Calendar.MINUTE,6)
        val outForDeliveryDate = myCal.time
        return outForDeliveryDate
    }

    fun isShipped(orderedDate:String):Boolean{
        val curDate = convertStringToDate(SimpleDateFormat("dd MMMM yyyy hh:mm:ss", Locale.getDefault()).format(Date()))
        return curDate.before(getOutOfDeliveryDate(orderedDate))&&curDate.after(convertStringToDate(orderedDate))
    }

    fun isOutForDelivery(orderedDate: String,deliveryDate: String):Boolean{
        val curDate = convertStringToDate(SimpleDateFormat("dd MMMM yyyy hh:mm:ss", Locale.getDefault()).format(Date()))
        return curDate.after(getShippedDate(orderedDate))&&curDate.before(convertStringToDate(deliveryDate))
    }

    fun isMedicineDelivered(deliveryDate:String):Boolean{
        val delDate = convertStringToDate(deliveryDate)
        val curDate = convertStringToDate(SimpleDateFormat("dd MMMM yyyy hh:mm:ss", Locale.getDefault()).format(Date()))
        return curDate.after(delDate)
    }

    fun convertStringToDate(dateStr:String):Date{
        val myFormat = "dd MMMM yyyy hh:mm:ss"
        val sdf:SimpleDateFormat = SimpleDateFormat(myFormat,Locale.getDefault())
        val dateObj = sdf.parse(dateStr)
        return dateObj
    }

    fun updateMedicineOrder(orderId: Int,isDelivered:String){
        medicineOrderDetailsDAO.updateOrder(orderId,"yes")
    }

}