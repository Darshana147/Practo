package com.example.practo.Model

import java.io.Serializable


data class Doctor(var doctorId:Int,var name:String, var gender:String, var qualification:String, var specialization:String, var experience:String, var consultationFee:Double, var hospitalDetails:Hospital, var services:String, var pastExperience:String)


 object DoctorDetailsSupplier{
     val doctorDetailsList = arrayListOf<Doctor>(
//         Doctor("Dr. N K Taparia","male","MBBS and MD – Pediatrics","Child Specialist","5 years",500.0,
//             Hospital("Pediatric Care Clinic", HospitalAddress("Ballygunge","Kolkata","West Bengal","India","700020"),"9887654321"),
//             "Growth & Development Evaluation,Vaccination, Immunization and Bronchial Asthma Treatment, Learning Disability (Dyslexia) Treatment",
//             "Worked in xyz hospital for 2 years, Working in Pediatric Care Clinic from past 3 years."),
//         Doctor("Dr. Shipra Mathur","female"," MBBS and MD – Pediatrics","Child Specialist","2 years",350.0,Hospital("Child and Adolescent Clinic",
//             HospitalAddress("Sohna Road, Gurgaon","Mumbai","Maharashtra","India","400017"),"9898767654"),
//             "Growth & Development Evaluation, Vaccination, Immunization and Bronchial Asthma Treatment",
//             "Working in Child and Adolescent clinic for the past 2 years")
     )
 }