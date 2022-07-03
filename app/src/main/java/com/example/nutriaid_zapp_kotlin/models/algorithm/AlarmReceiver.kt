package com.example.nutriaid_zapp_kotlin.models.algorithm

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import com.example.nutriaid_zapp_kotlin.models.interfaces.IAlgorithm
import com.example.nutriaid_zapp_kotlin.models.algorithm.UserSpecs
import com.example.nutriaid_zapp_kotlin.models.shortRecipe.ShortRecipe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("mytag", "alarm!")
        //authentification
        val auth: FirebaseAuth = Firebase.auth
        val user = auth.currentUser
        val email = user?.email


        //reference
        val db = Firebase.firestore
        Log.d("mytag", "authentification done")

        db.collection("userSpecs").whereEqualTo("email", email.toString()).get()
            .addOnCompleteListener() { task ->

                if (task.isSuccessful) {
                    Log.d("mytag", "get userspecs")
                    val document = task.result
                    Log.d("mytag", "userSpecs: "+document.size())
                    if (document.size() != 0) {
                        for (doc in document) {
                            val userSpecs = UserSpecs(
                                diet = doc.data["diet"].toString(),
                                dietExtras = doc.data["dietExtras"].toString(),
                                intolerances = doc.data["intolerances"].toString()
                            )
                            Log.d("mytag", "got userspecs")
                            val get = Algorithm(userSpecs)
                            get.getRecipes(21, true)
                        }
                    }
                    else{
                        Log.d("mytag", "no userspecs found, get recipes without specification")
                        val userSpecs = UserSpecs(diet = null, intolerances = null, dietExtras = null)
                        val get = Algorithm(userSpecs)
                        get.getRecipes(21, true)
                    }
                }
                else{
                    Log.d("mytag", "getting userSpecs failed, get recipes without specification")
                    val userSpecs = UserSpecs(diet = null, intolerances = null, dietExtras = null)
                    val get = Algorithm(userSpecs)
                    get.getRecipes(21, true)
                }
                Log.d("mytag", "receiving alarm done")
            }
            .addOnFailureListener(){
                Log.d("mytag", "no response from firebase, get recipes without specification")
                val userSpecs = UserSpecs(diet = null, intolerances = null, dietExtras = null)
                val get = Algorithm(userSpecs)
                get.getRecipes(21, true)
            }

    }
}

