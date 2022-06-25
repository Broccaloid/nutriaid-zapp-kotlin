package com.example.nutriaid_zapp_kotlin.repositories

import com.example.nutriaid_zapp_kotlin.apiServices.SpoonacularService
import com.example.nutriaid_zapp_kotlin.models.requests.FullRecipeParameters
import com.example.nutriaid_zapp_kotlin.models.requests.SearchParameters

class ApiRepository (private val spoonacularService: SpoonacularService) {
    fun getListShortRecipes(parameters: SearchParameters) =
        spoonacularService.getListShortRecipes(
            parameters.getQuerySearchParameters()
        )

    fun getFullRecipeById(parameters: FullRecipeParameters) =
        spoonacularService.getFullRecipeById(
            parameters.id,
            parameters.getQuerySearchParameters()
        )
}
