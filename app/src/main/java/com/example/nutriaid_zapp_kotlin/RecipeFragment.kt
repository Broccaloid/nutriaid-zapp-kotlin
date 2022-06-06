package com.example.nutriaid_zapp_kotlin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nutriaid_zapp_kotlin.adapters.ShortRecipeListAdapter
import com.example.nutriaid_zapp_kotlin.api_services.SpoonacularService
import com.example.nutriaid_zapp_kotlin.databinding.FragmentRecipeBinding
import com.example.nutriaid_zapp_kotlin.models.requests.SearchParameters
import com.example.nutriaid_zapp_kotlin.repositories.ApiRepository
import com.example.nutriaid_zapp_kotlin.view_models.RecipeFragmentViewModel
import com.example.nutriaid_zapp_kotlin.view_models.factories.RecipeFragmentViewModelFactory

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private lateinit var binding: FragmentRecipeBinding
    lateinit var viewModel: RecipeFragmentViewModel
    private val spoonacularService = SpoonacularService.getInstance()
    val adapter = ShortRecipeListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRecipeBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this, RecipeFragmentViewModelFactory(ApiRepository(spoonacularService)))
            .get(RecipeFragmentViewModel::class.java)
        binding.recyclerview.adapter = adapter
        viewModel.recipeList.observe(viewLifecycleOwner, Observer {
            adapter.setRecipeList(it)
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
        })
        viewModel.getListShortRecipes(SearchParameters())
        return binding.root
    }

    companion object {
        fun newInstance() = RecipeFragment()
    }
}