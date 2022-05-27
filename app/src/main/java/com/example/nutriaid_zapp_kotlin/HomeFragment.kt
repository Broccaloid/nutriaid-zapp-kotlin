package com.example.nutriaid_zapp_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutriaid_zapp_kotlin.databinding.FragmentHomeBinding

/*
    todo: getRecipeList(): get real recipe recommendations
 */

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeList = getRecipeList()

        val recyclerView = binding.homeRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(getContext())
        recyclerView.adapter = RecipeAdapter(recipeList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getRecipeList(): List<Recipe> {
        val recipe1 = Recipe()
        recipe1.title = "Recipe 1"
        val recipe2 = Recipe()
        recipe2.title = "Recipe 2"
        val recipe3 = Recipe()
        recipe3.title = "Recipe 3"
        recipe3.image = R.drawable.ic_baseline_star_24

        var recipeList: List<Recipe> =  listOf(recipe1, recipe2, recipe3)

        return recipeList
    }
}
