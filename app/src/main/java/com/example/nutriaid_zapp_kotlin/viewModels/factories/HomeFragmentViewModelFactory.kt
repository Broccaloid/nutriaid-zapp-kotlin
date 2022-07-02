package com.example.nutriaid_zapp_kotlin.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nutriaid_zapp_kotlin.viewModels.HomeFragmentViewModel
import com.example.nutriaid_zapp_kotlin.viewModels.RecipeActivityViewModel

class HomeFragmentViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeFragmentViewModel::class.java)) {
            HomeFragmentViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}