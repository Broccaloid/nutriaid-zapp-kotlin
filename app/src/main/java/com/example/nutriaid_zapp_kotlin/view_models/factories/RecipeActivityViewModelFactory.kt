package com.example.nutriaid_zapp_kotlin.view_models.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nutriaid_zapp_kotlin.repositories.ApiRepository
import com.example.nutriaid_zapp_kotlin.view_models.RecipeActivityViewModel

class RecipeActivityViewModelFactory (private val apiRepository: ApiRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RecipeActivityViewModel::class.java)) {
            RecipeActivityViewModel(this.apiRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}