package com.example.nutriaid_zapp_kotlin.models.algorithm

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import com.example.nutriaid_zapp_kotlin.models.interfaces.IAlgorithm
import com.example.nutriaid_zapp_kotlin.models.algorithm.UserSpecs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val auth : FirebaseAuth = Firebase.auth
        val db = Firebase.firestore
        val user = auth.currentUser
        val email = user?.email
        db.collection("userSpecs").whereEqualTo("email", email.toString()).get().addOnCompleteListener(){ task ->
            if(task.isSuccessful){
                val doc = task.result
                if(doc != null){
                    for(i in doc){
                        val userSpecs = UserSpecs(diet = i.data["diet"].toString(), dietExtras = i.data["dietExtras"].toString(), intolerances = i.data["intolerances"].toString())
                        val get = Algorithm(userSpecs)
                        get.getRecipes(21, true)
                        Log.d("mytag", "alarm!")
                    }

                }
            }
        }
    }
}

