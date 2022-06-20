package com.example.nutriaid_zapp_kotlin.models.algorithm

import com.example.nutriaid_zapp_kotlin.models.requests.SearchParameters
import com.example.nutriaid_zapp_kotlin.api_services.SpoonacularService
import com.example.nutriaid_zapp_kotlin.models.short_recipe.RecipeShortData
import com.example.nutriaid_zapp_kotlin.repositories.ApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class Algorithm(private val userSpecs: UserSpecs) : IAlgorithm {
    var number: String = "3"
    var cuisine: String = "0" //for recipe-exploring
    var diet: String = "0" //for recipe-exploring
    var minCarbs: String = "0"
    var minProtein: String = "0"
    var minCalories: String = "0"
    var maxFat: String = "0"
    var maxCalories: String = "0"

    override fun getRecipeIDList(num : Int):List<Int>{
        number = num.toString()
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
        var api : ApiRepository = ApiRepository(SpoonacularService.getInstance())
        var search = SearchParameters(number = number, diet = userSpecs.diet, intolerances = userSpecs.intolerances, minCarbs = minCarbs, minProtein = minProtein, minCalories = minCalories, maxFat = maxFat, maxCalories = maxCalories)
        var response  = api.getListShortRecipes(search)
        lateinit var IDList:MutableList<Int>
        var IDListIndex : Int = 0

        response.enqueue(object : Callback<RecipeShortData> {
            override fun onResponse(call: Call<RecipeShortData>, response: Response<RecipeShortData>) {
                val recipes = response.body()?.results //list<Typ shortRecipe>
                if (recipes != null) {
                    for(i in recipes){
                        IDList[IDListIndex] = i.id
                        IDListIndex++
                    }
                }
            }
            override fun onFailure(call: Call<RecipeShortData>, t: Throwable) {}
        })
        return IDList
    }





}