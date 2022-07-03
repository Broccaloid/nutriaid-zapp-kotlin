package com.example.nutriaid_zapp_kotlin.models.fullRecipe

data class ListFullRecipe(
    val results: List<RecipeFullData>,
    val offset: Int,
    val number: Int,
    val totalResults: Int
)
