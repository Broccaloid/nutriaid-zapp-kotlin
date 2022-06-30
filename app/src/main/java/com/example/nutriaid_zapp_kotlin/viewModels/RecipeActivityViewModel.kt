package com.example.nutriaid_zapp_kotlin.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nutriaid_zapp_kotlin.models.fullRecipe.RecipeFullData
import com.example.nutriaid_zapp_kotlin.models.requests.FullRecipeParameters
import com.example.nutriaid_zapp_kotlin.repositories.ApiRepository
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class RecipeActivityViewModel(private val apiRepository: ApiRepository) : ViewModel(){
    val recipe = MutableLiveData<RecipeFullData>()
    val errorMsg = MutableLiveData<String>()

    fun getFullRecipeById(parameters: FullRecipeParameters) {
        val response = apiRepository.getFullRecipeById(parameters)
        response.enqueue(object : Callback<RecipeFullData> {
            override fun onResponse(call: Call<RecipeFullData>, response: Response<RecipeFullData>) {
                val returnedRecipe = response.body()
                recipe.postValue(returnedRecipe)
            }
            override fun onFailure(call: Call<RecipeFullData>, t: Throwable) {
                errorMsg.postValue(t.message)
            }
        })
    }
}
