package com.example.nutriaid_zapp_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.nutriaid_zapp_kotlin.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

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
