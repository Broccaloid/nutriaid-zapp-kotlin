package com.example.nutriaid_zapp_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.nutriaid_zapp_kotlin.apiServices.SpoonacularService
import com.example.nutriaid_zapp_kotlin.models.fullRecipe.ExtendedIngredient
import com.example.nutriaid_zapp_kotlin.models.fullRecipe.RecipeFullData
import com.example.nutriaid_zapp_kotlin.models.requests.FullRecipeParameters
import com.example.nutriaid_zapp_kotlin.repositories.ApiRepository
import com.example.nutriaid_zapp_kotlin.viewModels.RecipeActivityViewModel
import com.example.nutriaid_zapp_kotlin.viewModels.factories.RecipeActivityViewModelFactory

class RecipeActivity : AppCompatActivity() {
    private lateinit var viewModel: RecipeActivityViewModel
    private val spoonacularService = SpoonacularService.getInstance()
    private lateinit var ingredientList: List<ExtendedIngredient>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val recipeId: Int = intent.extras?.get("recipeId") as Int
        val addIngredientsButton: Button = findViewById(R.id.add_ingredients_button)

        addIngredientsButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                //Todo: add ingredientList to shopping list
            }
        })

        /*
            GET DATA
         */
        viewModel = ViewModelProvider(
            this,
            RecipeActivityViewModelFactory(ApiRepository(spoonacularService))
        ).get(RecipeActivityViewModel::class.java)

        viewModel.recipe.observe(this) {
            showRecipeData(it)
        }
        viewModel.errorMsg.observe(this) {
        }

        viewModel.getFullRecipeById(FullRecipeParameters(recipeId))


    }
    private fun showRecipeData(recipe: RecipeFullData) {
        val recipeImg: ImageView = findViewById(R.id.recipe_image)
        val recipeTitle: TextView = findViewById(R.id.recipe_title)
        val recipeCredits: TextView = findViewById(R.id.recipe_credits)
        val recipeTime: TextView = findViewById(R.id.recipe_time)
        val recipeLikes: TextView = findViewById(R.id.recipe_likes)
        val recipeServings: TextView = findViewById(R.id.recipe_servings)
        val recipeInstructions: TextView = findViewById(R.id.recipe_instructions)
        val recipeIngredients: TextView = findViewById(R.id.recipe_ingredients)
        ingredientList = recipe.extendedIngredients
        var ingredients = ""
        for(i in ingredientList) {
            ingredients += "${i.amount} ${i.unit}:  ${i.name} \n"
        }

        Glide.with(this).load(recipe.image).into(recipeImg)
        recipeTitle.text = recipe.title
        recipeCredits.text = recipe.creditsText
        recipeTime.text = "${recipe.readyInMinutes} min"
        recipeLikes.text = "${recipe.aggregateLikes} likes"
        recipeServings.text ="${recipe.servings} servings"
        recipeIngredients.text = ingredients
        recipeInstructions.text = recipe.instructions

    }
}
