package com.example.nutriaid_zapp_kotlin.models.requests

import com.example.nutriaid_zapp_kotlin.models.interfaces.IQueryParameters
import kotlin.reflect.full.memberProperties

class SearchParameters(
    val number: String? = null,
    val cuisine: String? = null,
    val diet: String? = null,
    val query: String? = null,
    val intolerances: String? = null,
    val sort: String? = "random",
    var addRecipeNutrition: Boolean = false,
    val addRecipeInformation: Boolean = false,
    val minCarbs: String? = null,
    val minProtein: String? = null,
    val minFat: String? = null,
    val minCalories: String? = null,
    val maxCarbs: String? = null,
    val maxProtein: String? = null,
    val maxFat: String? = null,
    val maxCalories: String? = null,
    val maxMagnesium: String? = null,
    val minMagnesium: String? = null,
    val maxCalcium: String? = null,
    val minCalcium: String? = null,
    val maxSodium: String? = null,
    val minSodium: String? = null,
    val maxPotassium: String? = null,
    val minPotassium: String? = null,
    val maxPhosphorus: String? = null,
    val minPhosphorus: String? = null,
    val maxVitaminC: String? = null,
    val minVitaminC: String? = null,
    val maxVitaminD: String? = null,
    val minVitaminD: String? = null,
    val maxVitaminE: String? = null,
    val minVitaminE: String? = null,
    val maxVitaminK: String? = null,
    val minVitaminK: String? = null,
    val maxVitaminA: String? = null,
    val minVitaminA: String? = null,
    val maxVitaminB1: String? = null,
    val minVitaminB1: String? = null,
    val maxVitaminB2: String? = null,
    val minVitaminB2: String? = null,
    val maxVitaminB3: String? = null,
    val minVitaminB3: String? = null,
    val maxVitaminB5: String? = null,
    val minVitaminB5: String? = null,
    val maxVitaminB6: String? = null,
    val minVitaminB6: String? = null,
    val maxVitaminB12: String? = null,
    val minVitaminB12: String? = null,
) : IQueryParameters {
    override fun getQuerySearchParameters() : Map<String, String>{
        val parameters = HashMap<String, String>()
        for (prop in SearchParameters::class.memberProperties) {
            prop.get(this)?.let { parameters.put(prop.name, it.toString()) }
        }
        parameters["apiKey"] = "20191a90b51c46c3bb1ae5945c400586"
        return parameters
    }
}
