package com.example.practo.Model

data class MedicineDescription(var company:String,var ingredient:String,var medicineDescription:String,
                               var medicineSideEffects:String,var prescribedFor:String,var dosage:Dosage?,var generalInstructions:String)



data class Dosage(var missedDose:String?,var overDose:String?)


//object MedicineDescriptionSupplier{
//    var listOfDescriptions = arrayListOf(
//        MedicineDescription(1,"Rapither Ab Injection 2ml","Ipca Laboratories Pvt Ltd","Arteether","Rapither AB 75 mg Injection is an effective anti-malarial medicine. It is used to treat severe malaria caused by Plasmodium falciparum strains when other medicines are not effective.","Headache, Nausea or Vomiting, Persistent cough, Dizziness, Body pain, Pain at the injection site, Stomach discomfort and pain, Chills and rigot, Watery diarrhea, Unusual tiredness and weakness, Low WBC and Platelet count, Swelling of the liver","This medicine is used to treat malaria caused by Plasmodium falciparum strain of the parasite. It may also be used for the treatment of malaria that are resistant to other medicines.",Dosage("Contact your doctor immediately if you miss a scheduled dose of this medicine.","Contact your doctor immediately if an overdose with this medicine is suspected."),"Take Rapither AB 75 mg Injection as prescribed by the doctor. Do not take more or less than the recommended quantity and duration. It is advised to take this medicine under the supervision of a doctor or a healthcare professional."),
//        MedicineDescription(2,"Biofil Ab Particles 5ml","Adinos","Collagen","These are sterile medicated collagen particles (fish origin) that are used for chronic wounds.","Wound may appear larger during the first several days of treatment due to the reduction of edema, An increase in drainage may be seen in the first several days of treatment","Infected, non infected wounds and Ulcers.",null,"Assess, debride and clean the wound according to your protocols" +
//                "Apply BioFil®-AB Particles sufficiently to cover the wound surface. Irrigate the wound with saline to cleanse the area thoroughly"),
//        MedicineDescription(3,"Asthalin","Cipla Ltd","Salbutamol","Asthalin 100 mcg Inhaler is a bronchodilator medicine that relaxes the muscles of the airways leading to the lung and improves the amount of air flow to and from the lungs. It is used to prevent and treat the symptoms of asthma and Chronic obstructive pulmonary disease.","Shaking and trembling of arms and feet, Irregular heartbeat, Coughing, Difficulty in breathing, Swelling of face, lips, eyelids, tongue, hands and feet, Skin rash and spotting, Headache","Acute asthma, Chronic obstructive pulmonary disease, Bronchitis",Dosage("Take the missed dose as soon as you remember. If it is almost time for the next scheduled dose, then the missed dose can be skipped.","\n" +
//                "Contact your doctor immediately if an overdose with this medicine is suspected. Symptoms of an overdose may include dry mouth, shaking, chest pain, increased heart rate, convulsions, and fainting."),"Take Asthalin 100 mcg Inhaler exactly as prescribed. The prescribed dose form, frequency, and duration of uptake are specific as per your needs. Do not take this medicine in larger or smaller quantities or for longer than recommended. Contact your doctor if no visible improvements in symptoms are observed after use."),
//        MedicineDescription(4,"Abec-L Tablet","Emcure Pharmaceuticals Ltd","Abacavir (600 mg) + Lamivuding (300 mg)","Abec-L Tablet is a combination medicine used to treat human immunodeficiency virus (HIV) infections in adults. This medicine is not a cure for HIV; it only prevents the virus from multiplying in your body. Monitoring of liver function and kidney function may be necessary while receiving this medicine based on the clinical condition of the patient.",
//            "Chills and fever, Diarrhea, Headache, Fatigue, Loss of appetite, Depression, Stuffy nose, Muscle pain, Sore throat","This medicine is used in the treatment of human immunodeficiency virus (HIV) infection.",Dosage("Take the missed dose as soon as you remember. Skip the missed dose if it is almost time for your next scheduled dose. Do not use extra medicine to make up for the missed dose.","Seek emergency medical treatment or contact the doctor in case of an overdose."),"Take this medicine exactly as instructed by the doctor. Do not take in larger or smaller quantities than recommended. Ensure that proper hygiene measures are adopted. Complete the course of treatment to prevent the spread of the infection. Close monitoring of liver function and kidney function may be necessary while receiving this medicine based on the clinical condition."),
//        MedicineDescription(5,"Be Flame SP Tablet 10's","Bon Bointmentas Life","Aceclofenac, Serratiopeptidase","BE FLAME SP TABLET is a non-steroidal anti-inflammatory drugs (NSAIDs), prescribed for people with painful rheumatic conditions such as osteoarthritis, rheumatoid arthritis and ankylosing spondylitis.","Abdominal pain, Constipation, Diarrhea, Nausea and vomiting, Skin rash","Rheumatoid Arthritis, Osteoarthritis, Ankylosing Spondylitis",null,"The usual dose for BE FLAME SP TABLET is 100 mg tablet taken twice daily, preferably in the morning and evening. It can be taken with food or after food. Drinking an adequate amount of water minimises chances of indigestion and stomach irritations. The duration and amount of medicine should be followed as per the doctor’s prescription.")
//    )
//
//    fun getMedicineDescriptionById(medicineId:Int):MedicineDescription?{
//        for(medicineDesc in listOfDescriptions){
//            if(medicineDesc.medicineId==medicineId){
//                return medicineDesc
//            }
//        }
//        return null
//    }
//}