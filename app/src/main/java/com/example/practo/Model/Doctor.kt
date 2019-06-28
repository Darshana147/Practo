package com.example.practo.Model

import java.io.Serializable


data class Doctor(var name:String,var gender:String,var designation:String,var specialization:String,var experience:String,var consultationFee:Double, var hospitalDetails:Hospital, var services:ArrayList<String>) : Serializable


 object DoctorDetailsSupplier{
     val doctorDetailsList = arrayListOf<Doctor>(
         Doctor("Dr. N K Taparia","male","MBBS and MD – Pediatrics","Child Specialist","5 years",500.0,
             Hospital("Pediatric Care Clinic", "Ballygunge, Kolkata","9887654321"),
             arrayListOf("Growth & Development Evaluation","Vaccination"," Immunization and Bronchial Asthma Treatment","Learning Disability (Dyslexia) Treatment")),
         Doctor("Dr. Shipra Mathur","female"," MBBS and MD – Pediatrics","Child Specialist","2 years",350.0,Hospital("Child and Adolescent Clinic","Sohna Road, Gurgaon","9898767654"),
             arrayListOf("Growth & Development Evaluation","Vaccination"," Immunization and Bronchial Asthma Treatment"))
     )
 }