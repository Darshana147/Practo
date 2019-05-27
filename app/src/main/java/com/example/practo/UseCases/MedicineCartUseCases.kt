package com.example.practo.UseCases

import android.content.Context
import com.example.practo.DAO.CartDetailsDAO
import com.example.practo.DAO.MedicineCartItemDetailsDAO
import com.example.practo.DAO.MedicineDAO
import com.example.practo.Model.Medicine
import com.example.practo.Model.MedicineCartItem

class MedicineCartUseCases(context:Context) {
    private var medicineCartItemDetailsDAO:MedicineCartItemDetailsDAO
    private var medicineDAO:MedicineDAO
    private var medicineCartDetailsDAO:CartDetailsDAO

    init{
        medicineCartItemDetailsDAO = MedicineCartItemDetailsDAO(context)
        medicineDAO = MedicineDAO(context)
        medicineCartDetailsDAO = CartDetailsDAO(context)
    }

    fun isPresentInMedicineCart(medicineId: Int):Boolean{
        for(item in getMedicineItemsFromCart()){
            if(item.medicine.medicineId==medicineId){
                return true
            }
        }
        return false
    }

    fun addMedicineToCart(medicineCartItem:MedicineCartItem){
        if(isPresentInMedicineCart(medicineCartItem.medicine.medicineId)){
            changeMedicineCartItemQuantity(medicineCartItem.medicine.medicineId,medicineCartItem.medicineQuantity)
        }
        else{
            medicineCartItemDetailsDAO.insertMedicineItemIntoCart(medicineCartItem)
        }
    }


    fun getMedicineItemsFromCart():ArrayList<MedicineCartItem>{
        var medicineCartItemList:ArrayList<MedicineCartItem> = ArrayList()
        for(cartItem in medicineCartItemDetailsDAO.getAllCartItems()){
            var medicine:Medicine = medicineDAO.getMedicineById(cartItem.medicine.medicineId)
            var medicineCartItem = MedicineCartItem(medicine,cartItem.medicineQuantity)
            medicineCartItemList.add(medicineCartItem)
        }
        return medicineCartItemList
    }


    fun changeMedicineCartItemQuantity(medicineId:Int,medicineQuantity:Int){
        medicineCartItemDetailsDAO.changeMedicineQuantityByMedicineId(medicineId,medicineQuantity)
    }

    fun removeMedicineItemFromCart(medicineId:Int){
        if(isPresentInMedicineCart(medicineId)){
           medicineCartItemDetailsDAO.removeMedicineItemFromCart(medicineId)
        }
    }

    fun updateCartTotalQuantity(){
        var totalQuantity = 0
        for(item in getMedicineItemsFromCart()){
            totalQuantity+=item.medicineQuantity
        }
        medicineCartDetailsDAO.updateCartTotalQuantity(1,totalQuantity)
    }

    fun updateCartTotalPrice(){
        var totalPrice=0.0
        for(item in getMedicineItemsFromCart()){
            totalPrice+=item.medicine.medicinePrice*item.medicineQuantity
        }
        medicineCartDetailsDAO.updateCartTotalPrice(1,totalPrice)
    }

    fun getCartTotalQuantity():Int{
        return medicineCartDetailsDAO.getCartTotalQuantity(1)!!
    }

    fun getCartTotalPrice():Double{
        return medicineCartDetailsDAO.getCartTotalPrice(1)!!
    }


}