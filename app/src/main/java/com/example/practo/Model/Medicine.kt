package com.example.practo.Model

data class Medicine(var medicineId:Int,var medicineName:String,var medicineDescription:String,var medicinePrice:Double,var medicineType:String)



object MedicineSupplier{
    val medicineList = arrayListOf<Medicine>(
        Medicine(1,"Asthalin Inhaler 200md","Cipla Limited Respiratory",113.42,"liquid"),
        Medicine(2,"Abec L Tablet 30`s", "Emcure Pharma Ltd(renal Care)", 73.280,"tablet"),
        Medicine(3,"Ecosprin 150mg Tablet 14`s","Usv Limited (condor)",7.04,"tablet"),
        Medicine(4,"Be Flame Sp Tablet 10`s","Bon Bointmentas Life",59.50,"tablet"),
        Medicine(5,"Benadryl Cough Formula 150ml","Pfizer Ltd",99.00,"liquid"),
        Medicine(6,"Injek Injection 0.5ml","Neon Laboratories Limited",14.25,"injection"),
        Medicine(7,"Zonamax Es Injection Vial","Macleods (acuphar)",152.26,"injection"),
        Medicine(8,"Humog 75iu Injection Vial","Bharat Serum & Vaccines Ltd",606.06,"injection"),
        Medicine(9,"Emani Naturally Fair & Fairness Cream 45ml","Emani Limited",100.00,"cream"),
        Medicine(10,"Asthalin Inhaler 200md","Cipla Limited Respiratory",113.42,"liquid"),
        Medicine(11,"Abec L Tablet 30`s", "Emcure Pharma Ltd(renal Care)", 73.280,"tablet"),
        Medicine(12,"Ecosprin 150mg Tablet 14`s","Usv Limited (condor)",7.04,"tablet"),
        Medicine(13,"Be Flame Sp Tablet 10`s","Bon Bointmentas Life",59.50,"tablet"),
        Medicine(14,"Benadryl Cough Formula 150ml","Pfizer Ltd",99.00,"liquid"),
        Medicine(15,"Injek Injection 0.5ml","Neon Laboratories Limited",14.25,"injection"),
        Medicine(16,"Zonamax Es Injection Vial","Macleods (acuphar)",152.26,"injection"),
        Medicine(17,"Humog 75iu Injection Vial","Bharat Serum & Vaccines Ltd",606.06,"injection"),
        Medicine(18,"Emani Naturally Fair & Fairness Cream 45ml","Emani Limited",100.00,"cream"),
        Medicine(19,"Asthalin Inhaler 200md","Cipla Limited Respiratory",113.42,"liquid"),
        Medicine(20,"Abec L Tablet 30`s", "Emcure Pharma Ltd(renal Care)", 73.280,"tablet"),
        Medicine(21,"Ecosprin 150mg Tablet 14`s","Usv Limited (condor)",7.04,"tablet"),
        Medicine(22,"Be Flame Sp Tablet 10`s","Bon Bointmentas Life",59.50,"tablet"),
        Medicine(23,"Benadryl Cough Formula 150ml","Pfizer Ltd",99.00,"liquid"),
        Medicine(24,"Injek Injection 0.5ml","Neon Laboratories Limited",14.25,"injection"),
        Medicine(25,"Zonamax Es Injection Vial","Macleods (acuphar)",152.26,"injection"),
        Medicine(26,"Humog 75iu Injection Vial","Bharat Serum & Vaccines Ltd",606.06,"injection"),
        Medicine(27,"Emani Naturally Fair & Fairness Cream 45ml","Emani Limited",100.00,"cream")
    )
}