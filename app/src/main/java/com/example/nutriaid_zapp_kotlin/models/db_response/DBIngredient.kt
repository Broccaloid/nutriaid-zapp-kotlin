package com.example.nutriaid_zapp_kotlin.models.db_response

data class DBIngredient(
    val listId: Long,
    val ingredientId: Long,
    val amount: Double,
    val unit: String,
    val name: String,
)
