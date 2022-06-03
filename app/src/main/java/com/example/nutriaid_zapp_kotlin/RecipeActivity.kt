package com.example.nutriaid_zapp_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class RecipeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val recipe_id_view: TextView = findViewById(R.id.recipe_id_text)
        val recipe_id = intent.extras?.get("recipe_id")

        recipe_id_view.text = "Recipe Id: " + recipe_id
    }
}