package com.example.nutriaid_zapp_kotlin.models.fullRecipe

data class Ingredient(
    val amount: Double,
    val id: Int,
    val name: String,
    val nutrients: List<Nutrient>,
    val unit: String
)
