package com.example.practo.Model

class HealthArticle(var articleTitle:String,var articleDescription:String, var doctorName:String,var doctorSpecialization:String ){
    constructor(articleTitle: String,articleDescription: String,doctorName: String,doctorSpecialization: String, articleDetail:String):this(articleTitle,articleDescription,doctorName,doctorSpecialization)
}

object ArticlesSupplier{
    val articles = listOf<HealthArticle>(
        HealthArticle("Oral Hygene","12 Tips for Healthy Teeth","Dr.Akshay Rathod","Dentist"),
        HealthArticle("Obesity","Loose Weight the Healthy Way","Ms Pallavi Jassal","Weight Specialist"),
        HealthArticle("Oral Hygene","12 Tips for Healthy Teeth","Dr.Akshay Rathod","Dentist"),
        HealthArticle("Obesity","Loose Weight the Healthy Way","Ms Pallavi Jassal","Weight Specialist"),
        HealthArticle("Oral Hygene","12 Tips for Healthy Teeth","Dr.Akshay Rathod","Dentist"),
        HealthArticle("Obesity","Loose Weight the Healthy Way","Ms Pallavi Jassal","Weight Specialist"),
        HealthArticle("Oral Hygene","12 Tips for Healthy Teeth","Dr.Akshay Rathod","Dentist"),
        HealthArticle("Obesity","Loose Weight the Healthy Way","Ms Pallavi Jassal","Weight Specialist"),
        HealthArticle("Oral Hygene","12 Tips for Healthy Teeth","Dr.Akshay Rathod","Dentist"),
        HealthArticle("Obesity","Loose Weight the Healthy Way","Ms Pallavi Jassal","Weight Specialist"),
        HealthArticle("Oral Hygene","12 Tips for Healthy Teeth","Dr.Akshay Rathod","Dentist"),
        HealthArticle("Obesity","Loose Weight the Healthy Way","Ms Pallavi Jassal","Weight Specialist"),
        HealthArticle("Oral Hygene","12 Tips for Healthy Teeth","Dr.Akshay Rathod","Dentist"),
        HealthArticle("Obesity","Loose Weight the Healthy Way","Ms Pallavi Jassal","Weight Specialist"),
        HealthArticle("Oral Hygene","12 Tips for Healthy Teeth","Dr.Akshay Rathod","Dentist"),
        HealthArticle("Obesity","Loose Weight the Healthy Way","Ms Pallavi Jassal","Weight Specialist")
    )
}