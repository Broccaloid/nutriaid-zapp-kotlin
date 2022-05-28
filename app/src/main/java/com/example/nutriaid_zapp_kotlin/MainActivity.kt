package com.example.nutriaid_zapp_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment()
    private val shoppingFragment = ShoppingFragment()
    private val chartFragment = ChartFragment()
    private val recipeFragment = RecipeFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(homeFragment)

        val bottom_nav: BottomNavigationView = findViewById(R.id.bottom_nav)

        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_profile -> replaceFragment(profileFragment)
                R.id.nav_chart -> replaceFragment(chartFragment)
                R.id.nav_home -> replaceFragment(homeFragment)
                R.id.nav_shopping -> replaceFragment(shoppingFragment)
                R.id.nav_recipe -> replaceFragment(recipeFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val fragManager: FragmentManager = supportFragmentManager
            val fragTransaction: FragmentTransaction = fragManager.beginTransaction()
            fragTransaction.replace(R.id.main_container, fragment)
            //fragTransaction.addToBackStack(null)
            fragTransaction.commit()
        }
    }
}
