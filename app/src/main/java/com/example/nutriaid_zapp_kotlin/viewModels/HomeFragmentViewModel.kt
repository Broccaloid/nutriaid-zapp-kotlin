package com.example.nutriaid_zapp_kotlin.viewModels

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nutriaid_zapp_kotlin.models.dbResponse.DBRecipe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragmentViewModel : ViewModel() {
    val recipesList = MutableLiveData<List<DBRecipe>>()
    val errorMsg = MutableLiveData<String>()
    private var auth = Firebase.auth
    private val db = Firebase.firestore

    fun getRecommendationsForDay(day: Int) {
        val userEmail = auth.currentUser?.email
        val list = mutableListOf<DBRecipe>()

        db.collection("recipes")
            .whereEqualTo("email", userEmail)
            .get()
            .addOnSuccessListener { recipes ->
                for (item in recipes) {
                    Log.d(ContentValues.TAG, "${item.id} => ${item.data}")
                    try {
                        list.add(
                            DBRecipe(
                                id = item.data["id"] as Int? ?: 1,
                                title = item.data["title"] as String? ?: "Default",
                                image = item.data["image"] as String? ?: "https://spoonacular.com/recipeImages/637902-312x231.jpg",
                                aggregateLikes = item.data["aggregateLikes"] as String? ?: "100",
                                readyInMinutes = item.data["readyInMinutes"] as String? ?: "45"
                            )
                        )
                    } catch (e: Exception) {
                    }
                    try {
                        when (day) {
                            0 -> {
                                recipesList.postValue(list.slice(0..2))
                            }
                            1 -> {
                                recipesList.postValue(list.slice(3..5))
                            }
                            2 -> {
                                recipesList.postValue(list.slice(6..8))
                            }
                            3 -> {
                                recipesList.postValue(list.slice(9..11))
                            }
                            4 -> {
                                recipesList.postValue(list.slice(12..14))
                            }
                            5 -> {
                                recipesList.postValue(list.slice(15..17))
                            }
                            6 -> {
                                recipesList.postValue(list.slice(18..20))
                            }
                        }
                    } catch (e: Exception) {

                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }
}