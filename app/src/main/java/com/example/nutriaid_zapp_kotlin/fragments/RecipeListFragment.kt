package com.example.nutriaid_zapp_kotlin.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
    val adapter = ShortRecipeListAdapter { position -> onRecipeClick(position) }

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

    override fun onResume() {
        super.onResume()
        val intolerances = resources.getStringArray(R.array.intolerances)
        val intolerancesAdapter =
            ArrayAdapter(requireContext(), R.layout.drop_down_item, intolerances)
        binding.intolerance.setAdapter(intolerancesAdapter)

        val cuisines = resources.getStringArray(R.array.cuisines)
        val cuisinesAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, cuisines)
        binding.cuisine.setAdapter(cuisinesAdapter)

        val diets = resources.getStringArray(R.array.diets)
        val dietsAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, diets)
        binding.diet.setAdapter(dietsAdapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.applyFiltersButton.setOnClickListener {
            var diet: String? = null
            var cuisine: String? = null
            var intolerance: String? = null
            if (binding.diet.text.toString() != "None") {
                diet = binding.diet.text.toString()
            }
            if (binding.cuisine.text.toString() != "None") {
                cuisine = binding.cuisine.text.toString()
            }
            if (binding.intolerance.text.toString() != "None") {
                intolerance = binding.intolerance.text.toString()
            }
            viewModel.getListShortRecipes(
                SearchParameters(
                    diet = diet,
                    cuisine = cuisine,
                    intolerances = intolerance
                )
            )
        }
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
