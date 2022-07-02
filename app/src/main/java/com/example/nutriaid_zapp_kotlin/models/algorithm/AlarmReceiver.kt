package com.example.nutriaid_zapp_kotlin.models.algorithm

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import com.example.nutriaid_zapp_kotlin.models.interfaces.IAlgorithm
import com.example.nutriaid_zapp_kotlin.models.algorithm.UserSpecs

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val userSpecs = UserSpecs()
        val get = Algorithm(userSpecs)
        get.getRecipes(21, true)
        Log.d("mytag", "alarm!")
    }
}

