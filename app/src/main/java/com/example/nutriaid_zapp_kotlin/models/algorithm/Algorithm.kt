package com.example.nutriaid_zapp_kotlin.models.algorithm

import android.content.ContentValues.TAG
import android.util.Log
import com.example.nutriaid_zapp_kotlin.apiServices.SpoonacularService
import com.example.nutriaid_zapp_kotlin.models.fullRecipe.ListFullRecipe
import com.example.nutriaid_zapp_kotlin.models.requests.SearchParameters
import com.example.nutriaid_zapp_kotlin.models.fullRecipe.RecipeFullData
import com.example.nutriaid_zapp_kotlin.models.interfaces.IAlgorithm
import com.example.nutriaid_zapp_kotlin.models.requests.FullRecipeParameters
import com.example.nutriaid_zapp_kotlin.models.shortRecipe.RecipeShortData
import com.example.nutriaid_zapp_kotlin.models.shortRecipe.ShortRecipe
import com.example.nutriaid_zapp_kotlin.repositories.ApiRepository
import retrofit2.Call
import retrofit2.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import retrofit2.Callback


class Algorithm(private val userSpecs: UserSpecs) : IAlgorithm {

    var number: String = "21"
    var minCarbs: String = "0"
    var minProtein: String = "0"
    var minCalories: String = "0"
    var maxFat: String = "1000"
    var maxCalories: String = "10000"

    override fun getRecipes(
        num: Int,
        firstRun: Boolean
    ) {
        //set macros according to userSpecs
        Log.d("mytag", "getRecipes() called")
        number = num.toString()
        if (userSpecs.dietExtras == "high-protein")
            minProtein = "30"
        if (userSpecs.dietExtras == "low-calorie")
            maxCalories = "500"
        if (userSpecs.dietExtras == "high-calorie")
            minCalories = "700"
        if (userSpecs.dietExtras == "high-carb")
            minCarbs = "100"
        if (userSpecs.dietExtras == "low-fat")
            maxFat = "15"
        Log.d("mytag", "minProtein: " + minProtein)

        //authentification
        val auth: FirebaseAuth = Firebase.auth
        val user = auth.currentUser
        val email = user?.email

        //reference
        val db = Firebase.firestore


        //delete recipes of the user from firebase before writing new ones
        db.collection("recipes").whereEqualTo("email", email.toString()).get()
            .addOnCompleteListener() { task ->
                val document = task.result
                for (doc in document) {
                    doc.reference.delete()
                }
                Log.d("mytag", "old recipes deleted")


                //get recipes from spoonacular and write them to firebase
                var api: ApiRepository = ApiRepository(SpoonacularService.getInstance())
                var diet: String? = null
                var intolerances: String? = null
                if (userSpecs.intolerances != "none") {
                    intolerances = userSpecs.intolerances
                }
                if (userSpecs.diet != "none") {
                    diet = userSpecs.diet
                }

                var search = SearchParameters(
                    number = number,
                    diet = userSpecs.diet,
                    intolerances = userSpecs.intolerances,
                    minCarbs = minCarbs,
                    minProtein = minProtein,
                    minCalories = minCalories,
                    maxFat = maxFat,
                    maxCalories = maxCalories,
                    addRecipeNutrition = true
                )
                var response = api.getListFullRecipes(search)

                Log.d("mytag", "getListFullRecipes called")

                response.enqueue(object : Callback<ListFullRecipe> {
                    override fun onResponse(
                        call: Call<ListFullRecipe>,
                        response: Response<ListFullRecipe>
                    ) {
                        val recipes = response.body()?.results //list<Typ RecipeFullData>

                        if (recipes != null) {
                            Log.d("mytag", "title from first new recipe" + recipes[0].title)
                            Log.d("mytag", "recipes fetched, number: " + recipes.size)
                            for (i in recipes) {
                                val temp = hashMapOf<String, String>(
                                    "recipeId" to i.id.toString(),
                                    "image" to i.image,
                                    "title" to i.title,
                                    "email" to email.toString(),
                                    "aggregateLikes" to i.aggregateLikes.toString()
                                )
                                db.collection("recipes").add(temp)
                                    .addOnSuccessListener { recipesRef ->
                                        Log.d(TAG, "recipe added with ID: ${recipesRef.id}")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "Error adding recipe", e)
                                    }
                            }
                        }
                    }
                    override fun onFailure(call: Call<ListFullRecipe>, t: Throwable) {
                        Log.d("mytag", "no response from spoonacular")
                    }
                })
            }
    }
}