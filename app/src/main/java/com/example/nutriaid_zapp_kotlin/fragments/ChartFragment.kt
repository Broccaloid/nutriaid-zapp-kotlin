package com.example.nutriaid_zapp_kotlin.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nutriaid_zapp_kotlin.MainActivity
import com.example.nutriaid_zapp_kotlin.databinding.FragmentChartBinding
import com.example.nutriaid_zapp_kotlin.models.fullRecipe.Nutrient
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChartFragment : Fragment() {
    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!
    private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private var nutrients = mutableListOf<Nutrient>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        auth = Firebase.auth
        _binding = FragmentChartBinding.inflate(inflater, container, false)
        if(auth.currentUser == null){
            (activity as MainActivity).replaceFragment(LoginFragment())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = auth.currentUser?.email
        db.collection("trackValues")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")

                    nutrients.add(Nutrient(document.data["amount"] as Double, document.data["name"] as String, document.data["dailyNeed"] as Double, document.data["unit"] as String))
                }
                showChart()
            }
            .addOnFailureListener{ exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

    }
    private fun showChart() {
        val chart: BarChart = binding.chart
        var entries = mutableListOf<BarEntry>()
        var amountCalories: Double = 0.0
        var amountCarbohydrates: Double = 0.0
        var amountFat: Double = 0.0
        var amountProtein: Double = 0.0

        for(n in nutrients) {
            if(n.name == "Calories") {
                amountCalories += n.amount
            } else if(n.name == "Carbohydrates") {
                amountCarbohydrates += n.amount
            } else if(n.name == "Fat") {
                amountFat += n.amount
            } else if(n.name == "Protein") {
                amountCalories += n.amount
            }
        }

        println("Calories: ${amountCalories}")
        println("Carbohydrates: ${amountCarbohydrates}")
        println("Fat: ${amountFat}")
        println("Protein: ${amountProtein}")



       /* entries.add(BarEntry(0f,amountCalories))
        entries.add(BarEntry(0f,amountCarbohydrates))
        entries.add(BarEntry(0f,amountFat))
        entries.add(BarEntry(0f,amountProtein)) */

        var set = BarDataSet(entries, "BarDataSet")
        var data = BarData(set)
        data.barWidth = 0.8f
        chart.data = data
        chart.setFitBars(true)
        chart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
