package com.example.nutriaid_zapp_kotlin.models.algorithm

interface IAlgorithm {
    fun getRecipeIDList(num : Int, firstRun : Boolean):List<Int> //parameter: How many recipes do you need?
}