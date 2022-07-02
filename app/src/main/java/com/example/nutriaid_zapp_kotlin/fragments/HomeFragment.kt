package com.example.nutriaid_zapp_kotlin.fragments

import android.content.ContentValues
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutriaid_zapp_kotlin.MainActivity
import com.example.nutriaid_zapp_kotlin.R
import com.example.nutriaid_zapp_kotlin.adapters.RecipeAdapter
import com.example.nutriaid_zapp_kotlin.databinding.FragmentHomeBinding
import com.example.nutriaid_zapp_kotlin.models.dbResponse.DBRecipe
import com.example.nutriaid_zapp_kotlin.viewModels.HomeFragmentViewModel
import com.example.nutriaid_zapp_kotlin.viewModels.factories.HomeFragmentViewModelFactory
import com.example.nutriaid_zapp_kotlin.models.algorithm.AlarmReceiver
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.concurrent.schedule
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var day: Int = 0
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: HomeFragmentViewModel
    private val adapter = RecipeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(
            this, HomeFragmentViewModelFactory()
        ).get(HomeFragmentViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser == null) {
            (activity as MainActivity).replaceFragment(LoginFragment())
        }
        else{
            //set alarm if user is valid
            val calendar = Calendar.getInstance()
            val calendar2 = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            if(!calendar.before(calendar2)) { //so that the alarm doesnt fire instantly


                val REQUESTCODE = 1
                // Creating the pending intent to send to the BroadcastReceiver
                var alarmManager =
                    context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, AlarmReceiver::class.java)
                var pendingIntent = PendingIntent.getBroadcast(
                    context,
                    REQUESTCODE,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                // Setting the specific time for the alarm manager to trigger the intent

                //calendar.timeInMillis = System.currentTimeMillis()
                calendar.set(Calendar.HOUR_OF_DAY, 11) //fire alarm everyday at 12pm
                calendar.set(Calendar.MINUTE, 48)
                calendar.set(Calendar.SECOND, 6)
                // Starts the alarm manager
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    60000,
                    pendingIntent
                )
                Log.d("mytag", "alarm set")
            }
        }

        showRecipeRecommendations()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgBut = binding.homeImageButton

        imgBut.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                showMenu(v)
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showRecipeRecommendations() {
        val recyclerView = binding.homeRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        viewModel.recipesList.observe(viewLifecycleOwner) {
            adapter.setRecommendationList(it)
        }
        viewModel.errorMsg.observe(viewLifecycleOwner) {
        }
        viewModel.getRecommendationsForDay(day)
    }

    private fun showMenu(v: View?) {
        val popupMenu = PopupMenu(context, v)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.day_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { it: MenuItem? ->
            when (it!!.itemId) {
                R.id.day_0 -> {
                    day = 0
                    showRecipeRecommendations()
                }
                R.id.day_1 -> {
                    day = 1
                    showRecipeRecommendations()
                }
                R.id.day_2 -> {
                    day = 2
                    showRecipeRecommendations()
                }
                R.id.day_3 -> {
                    day = 3
                    showRecipeRecommendations()
                }
                R.id.day_4 -> {
                    day = 4
                    showRecipeRecommendations()
                }
                R.id.day_5 -> {
                    day = 5
                    showRecipeRecommendations()
                }
                R.id.day_6 -> {
                    day = 6
                    showRecipeRecommendations()
                }
            }
            true
        })
        popupMenu.show()
    }
}
