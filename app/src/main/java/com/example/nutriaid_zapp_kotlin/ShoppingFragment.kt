package com.example.nutriaid_zapp_kotlin

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutriaid_zapp_kotlin.adapters.ShoppingListAdapter
import com.example.nutriaid_zapp_kotlin.databinding.FragmentShoppingBinding
import com.example.nutriaid_zapp_kotlin.models.db_response.DBIngredient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ShoppingFragment : Fragment() {

    private var _binding: FragmentShoppingBinding? = null
    private val binding get() = _binding!!
    private var shoppingList = mutableListOf<DBIngredient>()
    private var documentList = mutableListOf<DocumentSnapshot>()
    private lateinit var adapter: ShoppingListAdapter
    private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentShoppingBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        if(auth.currentUser == null){
            (activity as MainActivity).replaceFragment(LoginFragment())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deleteAllItemsButton = binding.buttonDeleteShoppingitems
        val deleteSelectedItemsButton = binding.buttonDeleteSelecteditems
        documentList.clear()
        shoppingList.clear()

        loadData()
        /*
            OnClickListener
         */
        deleteAllItemsButton.setOnClickListener {
            for(i in 0 until documentList.size) {
                db.collection("ingredients").document(documentList[i].id).delete()
            }
            shoppingList.clear()
            showData()
        }
        deleteSelectedItemsButton.setOnClickListener {
            val deleteItems: List<Long> = adapter.selectedItems
            val list = shoppingList.toList()

            for (i in 0 until deleteItems.size) {
                for (j in 0 until list.size) {
                    if (deleteItems[i] == list[j].ingredientId) {
                        shoppingList.remove(list[j])
                    }
                }
            }
            val docList = documentList
            for (i in 0 until deleteItems.size) {
                for (j in 0 until docList.size) {
                    if (deleteItems[i] == (docList[j].data?.get("ingredientId") as Long)) {
                        db.collection("ingredients").document(documentList[j].id).delete()
                    }
                }
            }
            showData()
        }
    }

    private fun loadData() {
        val email = auth.currentUser?.email
        db.collection("ingredients")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                var data: DBIngredient
                val list = mutableListOf<DBIngredient>()
                for(document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    documentList.add(document)
                    val amount = document.data["amount"] as Double
                    val unit = document.data["unit"] as String
                    val name = document.data["name"] as String
                    val ingredientId = document.data["ingredientId"] as Long
                    data = DBIngredient( ingredientId, amount, unit, name)
                    list.add(data)
                }
                var firstRun = true
                for(item in list) {
                    if(firstRun) {
                        shoppingList.add(item)
                        firstRun = false
                    } else {
                        var addedItem = false
                        for((index, it) in shoppingList.withIndex()) {
                            if((item.ingredientId == it.ingredientId) && (item.unit == it.unit)) {
                                shoppingList[index] = DBIngredient(item.ingredientId, item.amount + it.amount, item.unit, item.name)
                                addedItem = true
                            }
                        }
                        if(!addedItem) {
                            shoppingList.add(item)
                        }
                    }
                }
                if(auth.currentUser != null) {
                    showData()
                }
            }
            .addOnFailureListener{ exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
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
