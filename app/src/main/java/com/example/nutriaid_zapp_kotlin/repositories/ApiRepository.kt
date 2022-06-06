package com.example.nutriaid_zapp_kotlin.repositories

import android.content.res.Resources
import com.example.nutriaid_zapp_kotlin.R
import com.example.nutriaid_zapp_kotlin.api_services.SpoonacularService
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