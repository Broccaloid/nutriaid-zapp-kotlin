package com.example.nutriaid_zapp_kotlin.models.algorithm

import com.example.nutriaid_zapp_kotlin.models.requests.SearchParameters
import com.example.nutriaid_zapp_kotlin.api_services.SpoonacularService
import com.example.nutriaid_zapp_kotlin.models.short_recipe.RecipeShortData
import com.example.nutriaid_zapp_kotlin.repositories.ApiRepository

fun algorithm(userSpecs: userSpecs){
    var number: String = "3"
    var cuisine: String //for recipe-exploring
    var diet: String //for recipe-exploring
    var minCarbs: String = "0"
    var minProtein: String = "0"
    var minCalories: String = "0"
    var maxFat: String = "0"
    var maxCalories: String = "0"

    for (i in userSpecs.dietExtras){
        if(i == "high-protein")
            minProtein = "30"
        if(i == "low-calorie")
            maxCalories = "500"
        if(i == "high-calorie")
            minCalories = "700"
        if(i == "high-carb")
            minCarbs = "100"
        if(i == "low-fat")
            maxFat = "20"
    }

    var search = SearchParameters(number, null, userSpecs.diet, null, userSpecs.intolerances, minCarbs, minProtein, null, minCalories, null, null, maxFat, maxCalories)
    var return : RecipeShortData = SpoonacularService.getListShortRecipes(search)

    //TODO("write return to firebase")
    //TODO("get return to view-models")
    //TODO("create alarm, whose intent launches the algorithm")
}