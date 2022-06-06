package com.example.nutriaid_zapp_kotlin.models.requests

import com.example.nutriaid_zapp_kotlin.models.interfaces.IQueryParameters

class FullRecipeParameters (val id: Int, private val includeNutrition: Boolean = true) : IQueryParameters {
    override fun getQuerySearchParameters(): Map<String, String> {
        var parameters = HashMap<String, String>()
        parameters["includeNutrition"] = includeNutrition.toString()
        parameters["apiKey"] = "20191a90b51c46c3bb1ae5945c400586"
        return parameters
    }
}
