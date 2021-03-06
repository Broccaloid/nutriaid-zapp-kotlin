package com.example.nutriaid_zapp_kotlin

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.nutriaid_zapp_kotlin.apiServices.SpoonacularService
import com.example.nutriaid_zapp_kotlin.models.fullRecipe.ExtendedIngredient
import com.example.nutriaid_zapp_kotlin.models.fullRecipe.Nutrient
import com.example.nutriaid_zapp_kotlin.models.fullRecipe.RecipeFullData
import com.example.nutriaid_zapp_kotlin.models.requests.FullRecipeParameters
import com.example.nutriaid_zapp_kotlin.repositories.ApiRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.nutriaid_zapp_kotlin.viewModels.RecipeActivityViewModel
import com.example.nutriaid_zapp_kotlin.viewModels.factories.RecipeActivityViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


class RecipeActivity : AppCompatActivity() {
    private lateinit var viewModel: RecipeActivityViewModel
    private val spoonacularService = SpoonacularService.getInstance()
    private lateinit var ingredientList: List<ExtendedIngredient>
    private val auth = Firebase.auth
    private val user = auth.currentUser
    private val db = Firebase.firestore
    private var recipeNutrition = mutableListOf<Nutrient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        recipeNutrition.clear()
        val recipeId: Int = intent.extras?.get("recipeId") as Int
        val email = auth.currentUser?.email
        val addIngredientsButton: Button = findViewById(R.id.add_ingredients_button)
        val trackButton: Button = findViewById(R.id.track_button)

        if (user == null) {
            addIngredientsButton.isEnabled = false
            trackButton.isEnabled = false
        }

        addIngredientsButton.setOnClickListener {
            //Todo: add ingredientList to shopping list
        }
        trackButton.setOnClickListener {
            val date = getCurrentDateTime()
            val dateInString = date.toString("yyyy/MM/dd")

            for (i in 0 until recipeNutrition.size) {
                val values = hashMapOf(
                    "email" to email,
                    "date" to dateInString,
                    "amount" to recipeNutrition[i].amount,
                    "name" to recipeNutrition[i].name,
                    "dailyNeed" to recipeNutrition[i].percentOfDailyNeeds,
                    "unit" to recipeNutrition[i].unit,
                )
                db.collection("trackValues")
                    .add(values)
            }
        }

        addIngredientsButton.setOnClickListener {
            val size = ingredientList.size - 1
            for (i in 0..size) {
                val id: Int = ingredientList[i].id
                val ingredient = hashMapOf(
                    "email" to email,
                    "ingredientId" to id,
                    "amount" to ingredientList[i].amount,
                    "unit" to ingredientList[i].unit,
                    "name" to ingredientList[i].name
                )
                db.collection("ingredients")
                    .add(ingredient)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }
        }

        /*
            GET DATA
        */
        viewModel = ViewModelProvider(
            this,
            RecipeActivityViewModelFactory(ApiRepository(spoonacularService))
        ).get(RecipeActivityViewModel::class.java)

        viewModel.recipe.observe(this) {
            showRecipeData(it)
            getNutrients(it)
        }
        viewModel.errorMsg.observe(this) {
        }

        viewModel.getFullRecipeById(FullRecipeParameters(recipeId))
    }

    private fun getNutrients(recipe: RecipeFullData) {
        val nutrition = recipe.nutrition
        val listNutrients = nutrition.nutrients
        for (n in listNutrients) {
            if (n.name == "Calories") {
                recipeNutrition.add(n)
            } else if (n.name == "Carbohydrates") {
                recipeNutrition.add(n)
            } else if (n.name == "Fat") {
                recipeNutrition.add(n)
            } else if (n.name == "Protein") {
                recipeNutrition.add(n)
            }
        }
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
        for (i in ingredientList) {
            ingredients += "${i.amount} ${i.unit}:  ${i.name} \n"
        }

        Glide.with(this).load(recipe.image).into(recipeImg)
        recipeTitle.text = recipe.title
        recipeCredits.text = recipe.creditsText
        recipeTime.text = "${recipe.readyInMinutes} min"
        recipeLikes.text = "${recipe.aggregateLikes} likes"
        recipeServings.text = "${recipe.servings} servings"
        recipeIngredients.text = ingredients
        recipeInstructions.text = Html.fromHtml(recipe.instructions)
    }

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}