package com.example.nutriaid_zapp_kotlin.models.requests

import com.example.nutriaid_zapp_kotlin.models.interfaces.IQueryParameters

class SearchParameters(
    private val number: String? = null,
    private val cuisine: String? = null,
    private val diet: String? = null,
    private val query: String? = null,
    private val intolerances: String? = null,
    private val minCarbs: String? = null,
    private val minProtein: String? = null,
    private val minFat: String? = null,
    private val minCalories: String? = null,
    private val maxCarbs: String? = null,
    private val maxProtein: String? = null,
    private val maxFat: String? = null,
    private val maxCalories: String? = null,
) : IQueryParameters {
    override fun getQuerySearchParameters() : Map<String, String>{
        val parameters = HashMap<String, String>()
        number?.let { parameters.put("number", it) }
        cuisine?.let { parameters.put("cuisine", it) }
        diet?.let { parameters.put("diet", it) }
        query?.let { parameters.put("name", it) }
        intolerances?.let { parameters.put("intolerances", it) }
        minCarbs?.let { parameters.put("minCarbs", it) }
        minProtein?.let { parameters.put("minProtein", it) }
        minFat?.let { parameters.put("minFat", it) }
        minCalories?.let { parameters.put("minCalories", it) }
        maxCarbs?.let { parameters.put("maxCarbs", it) }
        maxProtein?.let { parameters.put("maxProtein", it) }
        maxFat?.let { parameters.put("maxFat", it) }
        maxCalories?.let { parameters.put("maxCalories", it) }
        parameters["apiKey"] = "20191a90b51c46c3bb1ae5945c400586"
        return parameters
    }
}
