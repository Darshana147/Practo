//package com.example.practo.db.UseCases

//import android.content.Context
//import com.example.practo.db.DAO.MedicineDAO
//import com.example.practo.db.Model.Dosage
//import com.example.practo.db.Model.Medicine
//import com.example.practo.db.Model.MedicineDescription


//class SearchMedicineFragmentUseCases(context: Context) {

//    private var medicineDAO:MedicineDAO
//
//    init {
//        medicineDAO = MedicineDAO(context)
//    }
//    fun getAllMedicines():ArrayList<Medicine>{
//        val medList = arrayListOf<Medicine>()
//        val medicineList = medicineDAO.getAllMedicines()
//        for(med in medicineList){
//            val medId = med.medicineId
//            val medName = med.medicineName
//            val medDescription = med.medicineDescription
//            val medPrice = med.medicinePrice
//            val medType = med.medicineType
//            val medContains = med.medicineDetailedDescription.ingredient
//            val medManufacturer = med.medicineDetailedDescription.company
//            val medDetailedDesc = med.medicineDetailedDescription.medicineDescription
//            val medSideEffects = med.medicineDetailedDescription.medicineSideEffects
//            val medPrescribedFor = med.medicineDetailedDescription.prescribedFor
//            var medDosage: Dosage? = null
//            if (med.medicineDetailedDescription.dosage?.missedDose != null && med.medicineDetailedDescription.dosage?.overDose != null) {
//
//                medDosage = Dosage(
//                    med.medicineDetailedDescription.dosage?.missedDose,
//                    med.medicineDetailedDescription.dosage?.overDose
//                )
//            }
//            val medGeneralInstructions = med.medicineDetailedDescription.generalInstructions
//            medicineList.add(Medicine(medId,medName,medDescription,medPrice,medType,
//                MedicineDescription(medContains,medManufacturer,medDetailedDesc,medSideEffects,medPrescribedFor,medDosage,medGeneralInstructions)))
//
//        }
//        return medList
//    }
//
//
//    fun getMedicineById(medId:Int){
//
//    }
//}