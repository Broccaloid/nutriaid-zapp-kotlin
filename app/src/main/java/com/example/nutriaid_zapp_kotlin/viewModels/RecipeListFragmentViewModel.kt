package com.example.nutriaid_zapp_kotlin.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nutriaid_zapp_kotlin.models.requests.SearchParameters
import com.example.nutriaid_zapp_kotlin.models.shortRecipe.RecipeShortData
import com.example.nutriaid_zapp_kotlin.models.shortRecipe.ShortRecipe
import com.example.nutriaid_zapp_kotlin.repositories.ApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeListFragmentViewModel(private val apiRepository: ApiRepository) : ViewModel(){
    val recipeList = MutableLiveData<List<ShortRecipe>>()
    val errorMessage = MutableLiveData<String>()

    fun getListShortRecipes(parameters: SearchParameters) {
        val response = apiRepository.getListShortRecipes(parameters)
        response.enqueue(object : Callback<RecipeShortData> {
            override fun onResponse(call: Call<RecipeShortData>, response: Response<RecipeShortData>) {
                val recipes = response.body()?.results
                recipeList.postValue(recipes)
            }
            override fun onFailure(call: Call<RecipeShortData>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}
