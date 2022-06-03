package com.example.nutriaid_zapp_kotlin.api_services

import com.example.nutriaid_zapp_kotlin.models.full_recipe.RecipeFullData
import com.example.nutriaid_zapp_kotlin.models.short_recipe.RecipeShortData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularService {
    @GET("recipes/complexSearch")
    fun getListShortRecipes(
        @Query("number") number: Int,
        @Query("cuisine") cuisine: String,
        @Query("diet") diet: String,
        @Query("query") name: String,
        @Query("apiKey") key: String
    ): RecipeShortData

    @GET("recipes/{id}/information")
    fun getFullRecipeById(
        @Path("id") id: Int,
        @Query("includeNutrition") includeNutrition: Boolean,
        @Query("apiKey") key: String
    ) : RecipeFullData

    companion object {
        var service: SpoonacularService? = null

        fun getInstance() : SpoonacularService {
            if (service == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.spoonacular.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                service = retrofit.create(SpoonacularService::class.java)
            }
            return service!!
        }
    }
}