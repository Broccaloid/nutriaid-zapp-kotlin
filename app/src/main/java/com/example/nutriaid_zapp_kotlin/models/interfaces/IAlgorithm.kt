package com.example.nutriaid_zapp_kotlin.models.interfaces

interface IAlgorithm {
    fun getRecipes(num : Int, firstRun : Boolean) //parameter: How many recipes do you need?
}