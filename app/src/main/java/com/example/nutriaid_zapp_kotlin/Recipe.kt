package com.example.nutriaid_zapp_kotlin

import android.provider.Settings.Global.getString

/*
    test class for RecipeAdapter
    todo: change to real recipe class
 */

class Recipe {
    var title: String = "Recipe 1"
    var image: Int = R.drawable.ic_baseline_restaurant_menu_24
    var aggregateLikes: String = "3,5/5 (100)"
    var readyInMinutes: String = "20"
}
