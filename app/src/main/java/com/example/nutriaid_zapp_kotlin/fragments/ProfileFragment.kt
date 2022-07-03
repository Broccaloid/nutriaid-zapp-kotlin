package com.example.nutriaid_zapp_kotlin.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nutriaid_zapp_kotlin.MainActivity
import com.example.nutriaid_zapp_kotlin.R
import com.example.nutriaid_zapp_kotlin.apiServices.SpoonacularService
import com.example.nutriaid_zapp_kotlin.databinding.FragmentProfileBinding
import com.example.nutriaid_zapp_kotlin.models.algorithm.Algorithm
import com.example.nutriaid_zapp_kotlin.models.algorithm.UserSpecs
import com.example.nutriaid_zapp_kotlin.models.requests.SearchParameters
import com.example.nutriaid_zapp_kotlin.repositories.ApiRepository
import com.example.nutriaid_zapp_kotlin.viewModels.RecipeListFragmentViewModel
import com.example.nutriaid_zapp_kotlin.viewModels.factories.RecipeFragmentViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass.
 */
//class ProfileFragment : Fragment(R.layout.fragment_profile) {

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    val currentUser = auth.currentUser
    val email = currentUser?.email
    private val db = Firebase.firestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        if (currentUser == null) {
            (activity as MainActivity).replaceFragment(LoginFragment())
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val intolerances = resources.getStringArray(R.array.intolerances)
        val intolerancesAdapter =
            ArrayAdapter(requireContext(), R.layout.drop_down_item, intolerances)
        binding.intolerance.setAdapter(intolerancesAdapter)

        val dietExtras = resources.getStringArray(R.array.diet_extra)
        val dietExtrasAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, dietExtras)
        binding.dietExtras.setAdapter(dietExtrasAdapter)

        val diets = resources.getStringArray(R.array.diets)
        val dietsAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, diets)
        binding.diet.setAdapter(dietsAdapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = auth.currentUser
        val logout_button = binding.logoutButton

        fun updateUI(user: FirebaseUser?) {
            if (user != null) {
                (activity as MainActivity).replaceFragment(HomeFragment())
            }
        }

        binding.applyFiltersButton.setOnClickListener {
            var diet: String? = null
            var dietExtras: String? = null
            var intolerance: String? = null
            if (binding.diet.text.toString() != "None") {
                diet = binding.diet.text.toString()
            }
            if (binding.dietExtras.text.toString() != "None") {
                dietExtras = binding.dietExtras.text.toString()
            }
            if (binding.intolerance.text.toString() != "None") {
                intolerance = binding.intolerance.text.toString()
            }
            var specs = UserSpecs(diet, dietExtras, intolerance)
            var alg = Algorithm(specs)
            alg.getRecipes(21, true)
            val doc = hashMapOf<String, String>(
                "email" to email.toString(),
                "diet" to diet!!,
                "dietExtras" to dietExtras!!,
                "intolerances" to intolerance!!
            )
            db.collection("userSpecs").add(doc).addOnSuccessListener { Log.d("success", "UserSpecs added to Firebase") }
        }

        logout_button.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                Firebase.auth.signOut()
                updateUI(currentUser)
            }
        })
    }

}
