package com.example.nutriaid_zapp_kotlin.models.algorithm

import com.example.nutriaid_zapp_kotlin.models.requests.SearchParameters

lateinit var search: SearchParameters

fun algorithm(userSpecs: userSpecs){
    search.number = 3
    search.diet = userSpecs.diet
    search.intolerances = userSpecs.intolerances

    for (i in userSpecs.dietExtras){
        if(i == "high-protein")
            search.minProtein = "30"
        if(i == "low-calorie")
            search.maxCalories = "500"
        if(i == "high-calorie")
            search.minCalories = "700"
        if(i == "high-carb")
            search.minCarbs = "100"
        if(i == "low-fat")
            search.maxFat = "20"
    }

}