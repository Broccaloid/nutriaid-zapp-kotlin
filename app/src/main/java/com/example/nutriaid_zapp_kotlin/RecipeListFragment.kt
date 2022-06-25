package com.example.nutriaid_zapp_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nutriaid_zapp_kotlin.adapters.ShortRecipeListAdapter
import com.example.nutriaid_zapp_kotlin.apiServices.SpoonacularService
import com.example.nutriaid_zapp_kotlin.databinding.FragmentRecipeListBinding
import com.example.nutriaid_zapp_kotlin.models.requests.SearchParameters
import com.example.nutriaid_zapp_kotlin.repositories.ApiRepository
import com.example.nutriaid_zapp_kotlin.viewModels.RecipeListFragmentViewModel
import com.example.nutriaid_zapp_kotlin.viewModels.factories.RecipeFragmentViewModelFactory


/**
 * A simple [Fragment] subclass.
 */
class RecipeListFragment : Fragment(R.layout.fragment_recipe_list) {
    private lateinit var binding: FragmentRecipeListBinding
    lateinit var viewModel: RecipeListFragmentViewModel
    private val spoonacularService = SpoonacularService.getInstance()
    val adapter = ShortRecipeListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRecipeListBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(
            this,
            RecipeFragmentViewModelFactory(ApiRepository(spoonacularService))
        )
            .get(RecipeListFragmentViewModel::class.java)
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
        fun newInstance() = RecipeListFragment()
    }
}
