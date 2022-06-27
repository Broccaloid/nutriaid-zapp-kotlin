package com.example.nutriaid_zapp_kotlin

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.nutriaid_zapp_kotlin.databinding.ActivityMainBinding
import com.example.nutriaid_zapp_kotlin.models.algorithm.AlarmReceiver
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment()
    private val shoppingFragment = ShoppingFragment()
    private val chartFragment = ChartFragment()
    private val recipeListFragment = RecipeListFragment()
    private val profileFragment = ProfileFragment()
    private val loginFragment = LoginFragment()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(homeFragment)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav)

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_profile -> replaceFragment(profileFragment)
                R.id.nav_chart -> replaceFragment(chartFragment)
                R.id.nav_home -> replaceFragment(homeFragment)
                R.id.nav_shopping -> replaceFragment(shoppingFragment)
                R.id.nav_recipeList -> replaceFragment(recipeListFragment)
            }
            true
        }

        val REQUESTCODE = 1

        // Creating the pending intent to send to the BroadcastReceiver
        var alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        var pendingIntent = PendingIntent.getBroadcast(this, REQUESTCODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Setting the specific time for the alarm manager to trigger the intent
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 0) //fire alarm everyday at 12pm
        calendar.set(Calendar.MINUTE, 0)

        // Starts the alarm manager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    public fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val fragManager: FragmentManager = supportFragmentManager
            val fragTransaction: FragmentTransaction = fragManager.beginTransaction()
            fragTransaction.replace(R.id.main_container, fragment)
            //fragTransaction.addToBackStack(null)
            fragTransaction.commit()
        }
    }
}
