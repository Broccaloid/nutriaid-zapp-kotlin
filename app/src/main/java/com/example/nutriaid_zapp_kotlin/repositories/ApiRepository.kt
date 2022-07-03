package com.example.nutriaid_zapp_kotlin.repositories

import com.example.nutriaid_zapp_kotlin.apiServices.SpoonacularService
import com.example.nutriaid_zapp_kotlin.models.fullRecipe.ListFullRecipe
import com.example.nutriaid_zapp_kotlin.models.requests.FullRecipeParameters
import com.example.nutriaid_zapp_kotlin.models.requests.SearchParameters
import retrofit2.Call

class ApiRepository (private val spoonacularService: SpoonacularService) {
    fun getListShortRecipes(parameters: SearchParameters) =
        spoonacularService.getListShortRecipes(
            parameters.getQuerySearchParameters()
        )

    fun getListFullRecipes(parameters: SearchParameters): Call<ListFullRecipe> {
        parameters.addRecipeNutrition = true
        return spoonacularService.getListFullRecipes(
            parameters.getQuerySearchParameters()
        )
    }


    fun getFullRecipeById(parameters: FullRecipeParameters) =
        spoonacularService.getFullRecipeById(
            parameters.id,
            parameters.getQuerySearchParameters()
        )

}
