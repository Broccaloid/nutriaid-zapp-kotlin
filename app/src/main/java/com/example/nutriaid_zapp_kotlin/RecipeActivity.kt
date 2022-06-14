package com.example.nutriaid_zapp_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutriaid_zapp_kotlin.adapters.RecipeIngredientsAdapter
import com.example.nutriaid_zapp_kotlin.api_services.SpoonacularService

class RecipeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val recipe_id = intent.extras?.get("recipe_id")
        val recyclerView = findViewById<RecyclerView>(R.id.recipe_ingredientsList)
        val spoonacularService = SpoonacularService.getInstance()
        var ingredients: List<String>

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecipeIngredientsAdapter(ingredients)

    }
}