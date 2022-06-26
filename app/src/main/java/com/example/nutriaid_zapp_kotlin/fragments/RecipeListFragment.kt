package com.example.nutriaid_zapp_kotlin.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nutriaid_zapp_kotlin.R
import com.example.nutriaid_zapp_kotlin.RecipeActivity
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
    val adapter = ShortRecipeListAdapter{ position -> onRecipeClick(position) }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = RecipeListFragment()
    }

    fun onRecipeClick(position: Int) {
        val intent = Intent(activity, RecipeActivity::class.java)
        viewModel.recipeList.value?.get(position)?.let { intent.putExtra("recipeId", it.id) }
        startActivity(intent)
    }
}
