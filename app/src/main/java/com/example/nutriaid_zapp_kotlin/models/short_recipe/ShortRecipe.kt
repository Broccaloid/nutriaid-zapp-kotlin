package com.example.nutriaid_zapp_kotlin.models.short_recipe

data class ShortRecipe(
    val calories: Int,
    val carbs: String,
    val fat: String,
    val id: Int,
    val image: String,
    val imageType: String,
    val protein: String,
    val title: String
)