package com.example.nutriaid_zapp_kotlin.fragments

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nutriaid_zapp_kotlin.MainActivity
import com.example.nutriaid_zapp_kotlin.databinding.FragmentChartBinding
import com.example.nutriaid_zapp_kotlin.models.fullRecipe.Nutrient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.LegendRenderer
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint

class ChartFragment : Fragment() {
    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!
    private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private var nutrients = mutableListOf<Nutrient>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChartBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        if(auth.currentUser == null){
            (activity as MainActivity).replaceFragment(LoginFragment())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nutrients.clear()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showChart() {
        var amountCalories = 0.0
        var amountCarbohydrates = 0.0
        var amountFat = 0.0
        var amountProtein = 0.0

        for(n in nutrients) {
            if (n.name == "Calories") {
                amountCalories += n.amount
            } else if(n.name == "Carbohydrates") {
                amountCarbohydrates += n.amount
            } else if(n.name == "Fat") {
                amountFat += n.amount
            } else if(n.name == "Protein") {
                amountProtein += n.amount
            }
        }

        val graph: GraphView = binding.graph

        val series0 = BarGraphSeries(arrayOf(DataPoint(0.0, 0.0)))
        val series1 = BarGraphSeries(arrayOf(DataPoint(0.5, amountCalories)))
        val series2 = BarGraphSeries(arrayOf(DataPoint(1.0, amountCarbohydrates)))
        val series3 = BarGraphSeries(arrayOf(DataPoint(1.5, amountFat)))
        val series4 = BarGraphSeries(arrayOf(DataPoint(2.0, amountProtein)))
        val series = BarGraphSeries(arrayOf(DataPoint(2.5, 0.0)))

        graph.addSeries(series0)
        graph.addSeries(series1)
        graph.addSeries(series2)
        graph.addSeries(series3)
        graph.addSeries(series4)
        graph.addSeries(series)

        series1.title = "Calories [kcal]"
        series1.isDrawValuesOnTop = true
        series1.valuesOnTopColor = Color.BLACK
        series1.color = Color.GREEN
        series2.title = "Carbohydrates [g]"
        series2.isDrawValuesOnTop = true
        series2.valuesOnTopColor = Color.BLACK
        series2.color = Color.BLUE
        series3.title = "Fat [g]"
        series3.isDrawValuesOnTop = true
        series3.valuesOnTopColor = Color.BLACK
        series3.color = Color.MAGENTA
        series4.title = "Protein[g]"
        series4.isDrawValuesOnTop = true
        series4.valuesOnTopColor = Color.BLACK
        series4.color = Color.YELLOW

        graph.gridLabelRenderer.horizontalAxisTitle = "Nutrient"
        graph.gridLabelRenderer.isHorizontalLabelsVisible = false


        graph.title = "Your Nutrition today"
        graph.viewport.isScalable = true // enables horizontal zooming and scrolling
        graph.viewport.setScalableY(true) // enables vertical zooming and scrolling
        graph.legendRenderer.isVisible = true
        graph.legendRenderer.align = LegendRenderer.LegendAlign.TOP

    }
}
