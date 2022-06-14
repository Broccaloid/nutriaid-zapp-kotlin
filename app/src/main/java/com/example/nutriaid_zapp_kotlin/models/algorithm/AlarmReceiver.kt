package com.example.nutriaid_zapp_kotlin.models.algorithm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        algorithm(UserSpecs()) //start algorithm when alarm fires
    }
}

