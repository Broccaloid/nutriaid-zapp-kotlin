package com.example.nutriaid_zapp_kotlin.models.full_recipe

data class Ingredient(
    val amount: Double,
    val id: Int,
    val name: String,
    val nutrients: List<Nutrient>,
    val unit: String
)