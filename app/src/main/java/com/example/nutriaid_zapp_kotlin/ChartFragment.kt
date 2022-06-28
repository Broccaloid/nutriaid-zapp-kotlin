package com.example.nutriaid_zapp_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nutriaid_zapp_kotlin.databinding.FragmentChartBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class ChartFragment : Fragment() {
    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chart: LineChart = binding.chart
        var calories = mutableListOf<Entry>()
        var carbohydrates = mutableListOf<Entry>()
        var fat = mutableListOf<Entry>()
        var protein = mutableListOf<Entry>()

        calories.add(Entry(0.0f,10f))
        calories.add(Entry(0.5f, 2f))
        calories.add(Entry(1.0f, 8f))
        calories.add(Entry(1.5f, 3f))
        calories.add(Entry(2.0f, 6f))

        carbohydrates.add(Entry(0f,2f))
        carbohydrates.add(Entry(0.5f,4f))
        carbohydrates.add(Entry(1.0f,1f))
        carbohydrates.add(Entry(1.5f,10f))
        carbohydrates.add(Entry(2.0f,6f))

        fat.add(Entry(0f,2f))
        fat.add(Entry(0.5f,3f))
        fat.add(Entry(1.0f,5f))
        fat.add(Entry(1.5f,4f))
        fat.add(Entry(2.0f,2f))

        protein.add(Entry(0f,1f))
        protein.add(Entry(0.5f,2f))
        protein.add(Entry(1.0f,12f))
        protein.add(Entry(1.5f,6f))
        protein.add(Entry(2.0f,0f))

        var dataSetCalories = LineDataSet(calories, "Calories")
        dataSetCalories.axisDependency = YAxis.AxisDependency.LEFT
        var dataSetCarbohydrate = LineDataSet(calories, "Carbohydrate")
        dataSetCalories.axisDependency = YAxis.AxisDependency.LEFT
        var dataSetFat = LineDataSet(fat, "Fat")
        dataSetCalories.axisDependency = YAxis.AxisDependency.LEFT
        var dataSetProtein = LineDataSet(protein, "Protein")
        dataSetCalories.axisDependency = YAxis.AxisDependency.LEFT

        var dataSets = mutableListOf<ILineDataSet>()
        dataSets.add(dataSetCalories)
        dataSets.add(dataSetCarbohydrate)
        dataSets.add(dataSetFat)
        dataSets.add(dataSetProtein)

        var data = LineData(dataSets)
        chart.data = data
        chart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
