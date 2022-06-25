package com.example.nutriaid_zapp_kotlin.models.algorithm

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import com.example.nutriaid_zapp_kotlin.models.algorithm.Algorithm

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        IAlgorithm.getRecipes(21, 0)
    }
}

