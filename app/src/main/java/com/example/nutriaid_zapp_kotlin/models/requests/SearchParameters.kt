package com.example.nutriaid_zapp_kotlin.models.requests

import com.example.nutriaid_zapp_kotlin.models.interfaces.IQueryParameters

class SearchParameters(
    private val number: String? = null,
    private val cuisine: String? = null,
    private val diet: String? = null,
    private val query: String? = null,
    private val intolerances: String? = null
) : IQueryParameters {
    override fun getQuerySearchParameters() : Map<String, String>{
        val parameters = HashMap<String, String>()
        number?.let { parameters.put("number", it) }
        cuisine?.let { parameters.put("cuisine", it) }
        diet?.let { parameters.put("diet", it) }
        query?.let { parameters.put("name", it) }
        intolerances?.let { parameters.put("intolerances", it) }
        parameters["apiKey"] = "20191a90b51c46c3bb1ae5945c400586"
        return parameters
    }
}
