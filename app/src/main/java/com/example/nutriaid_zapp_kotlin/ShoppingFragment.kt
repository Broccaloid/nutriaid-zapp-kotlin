package com.example.nutriaid_zapp_kotlin

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutriaid_zapp_kotlin.adapters.RecipeAdapter
import com.example.nutriaid_zapp_kotlin.adapters.ShoppingListAdapter
import com.example.nutriaid_zapp_kotlin.databinding.FragmentShoppingBinding
import com.example.nutriaid_zapp_kotlin.models.full_recipe.Ingredient
import com.example.nutriaid_zapp_kotlin.models.full_recipe.Nutrient
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ShoppingFragment : Fragment() {

    private var _binding: FragmentShoppingBinding? = null
    private val binding get() = _binding!!
    private var shoppingList = mutableListOf<Ingredient>()
    private lateinit var adapter: ShoppingListAdapter
    private val db = Firebase.firestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentShoppingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deleteAllItemsButton = binding.buttonDeleteShoppingitems
        val deleteSelecteditemsButton = binding.buttonDeleteSelecteditems

        // TODO: get data from database and put them in shoppinglist
        db.collection("ingredients")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")

                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        

        deleteAllItemsButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                //TODO: update database
                shoppingList.clear()
                showData()
            }
        })
        deleteSelecteditemsButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val deleteItems: List<Int> = adapter.selectedItems
                val newShoppingList = shoppingList.toList()

                //TODO: update database
                for(id in deleteItems) {
                    for(item in newShoppingList) {
                        if(id == item.id) {
                            shoppingList.remove(item)
                        }
                    }
                }
                showData()
            }
        })
        showData()
    }
    private fun showData() {
        val recyclerView = binding.shoppingListRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ShoppingListAdapter(shoppingList)
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
