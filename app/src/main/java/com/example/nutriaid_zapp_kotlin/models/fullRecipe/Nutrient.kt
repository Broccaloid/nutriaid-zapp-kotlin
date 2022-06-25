package com.example.nutriaid_zapp_kotlin.models.fullRecipe

data class Nutrient(
    val amount: Double,
    val name: String,
    val percentOfDailyNeeds: Double,
    val unit: String
)
