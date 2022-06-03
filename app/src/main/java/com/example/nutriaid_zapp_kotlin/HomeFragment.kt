package com.example.nutriaid_zapp_kotlin

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupMenu
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

    private var day: Int = 0;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val img_but = binding.homeImageButton

        img_but.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v:View?) {
                showMenu(v)
            }
        })

        showRecipeRecommendations()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showRecipeRecommendations() {
        val recipeList = getRecipeList(day)

        val recyclerView = binding.homeRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = RecipeAdapter(recipeList)
    }

    private fun showMenu(v:View?) {
        val popupMenu = PopupMenu(context,v)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.day_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { it: MenuItem? ->
            when(it!!.itemId) {
                R.id.day_0 -> {
                    day=0
                    showRecipeRecommendations()
                }
                R.id.day_1 -> {
                    day=1
                    showRecipeRecommendations()
                }
                R.id.day_2 -> {
                    day=2
                    showRecipeRecommendations()
                }
                R.id.day_3 -> {
                    day=3
                    showRecipeRecommendations()
                }
                R.id.day_4 -> {
                    day=4
                    showRecipeRecommendations()
                }
                R.id.day_5 -> {
                    day=5
                    showRecipeRecommendations()
                }
                R.id.day_6 -> {
                    day=6
                    showRecipeRecommendations()
                }
            }
            true
        })
        popupMenu.show()
    }

    private fun getRecipeList(day: Int): List<Recipe> {

        /*
            the recent code in getRecipeList() is just for debugging
         */
        val recipe1 = Recipe()
        recipe1.title = "day: " + day + " Recipe 1"
        val recipe2 = Recipe()
        recipe2.title = "day: " + day + " Recipe 2"
        val recipe3 = Recipe()
        recipe3.title = "day: " + day + " Recipe 3"
        recipe3.image = R.drawable.ic_baseline_android_24

        var recipeList: List<Recipe> =  listOf(recipe1, recipe2, recipe3)

        return recipeList
    }
}
