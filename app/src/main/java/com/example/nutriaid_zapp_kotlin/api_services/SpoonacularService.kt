package com.example.nutriaid_zapp_kotlin.api_services

import com.example.nutriaid_zapp_kotlin.models.full_recipe.RecipeFullData
import com.example.nutriaid_zapp_kotlin.models.requests.SearchParameters
import com.example.nutriaid_zapp_kotlin.models.short_recipe.RecipeShortData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface SpoonacularService {
    @GET("recipes/complexSearch")
    fun getListShortRecipes(
        @QueryMap searchParameters: Map<String, String>
    ): Call<RecipeShortData>

    @GET("recipes/{id}/information")
    fun getFullRecipeById(
        @Path("id") id: Int,
        @QueryMap searchParameters: Map<String, String>
    ) : Call<RecipeFullData>

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