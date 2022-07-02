package com.example.nutriaid_zapp_kotlin.models.dbResponse

data class DBRecipe(var id: Int,
                    var title: String,
                    var image: String,
                    var aggregateLikes: String,
                    var readyInMinutes: String)
