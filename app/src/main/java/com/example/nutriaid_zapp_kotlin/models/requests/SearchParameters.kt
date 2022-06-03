package com.example.nutriaid_zapp_kotlin.models.requests

import retrofit2.http.Query

data class SearchParameters (
    val number: Int,
    val cuisine: String,
    val diet: String,
    val name: String
    )
