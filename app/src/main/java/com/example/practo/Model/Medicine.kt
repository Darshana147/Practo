package com.example.practo.Model

class Medicine(var medicineId:Int){
    var medicineName: String = ""
    var medicineDescription: String = ""
    var medicinePrice: Double = 0.0
    var medicineType: String = ""
    lateinit var medicineDetailedDescription: MedicineDescription
    constructor(medicineId:Int,medicineName:String,medicineDescription: String,medicinePrice: Double,medicineType: String,medicineDetailedDescription: MedicineDescription):this(medicineId){
        this.medicineName = medicineName
        this.medicineDescription = medicineDescription
        this.medicinePrice = medicinePrice
        this.medicineType = medicineType
        this.medicineDetailedDescription = medicineDetailedDescription

    }

}

