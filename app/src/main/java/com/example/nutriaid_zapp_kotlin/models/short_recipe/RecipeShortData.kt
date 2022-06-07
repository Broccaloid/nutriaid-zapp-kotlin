package com.example.nutriaid_zapp_kotlin.models.short_recipe

data class RecipeShortData(
    val number: Int,
    val offset: Int,
    val results: List<ShortRecipe>,
    val totalResults: Int
)