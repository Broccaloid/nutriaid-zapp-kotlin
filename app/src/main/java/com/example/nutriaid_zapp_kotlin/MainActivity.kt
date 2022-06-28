package com.example.nutriaid_zapp_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.nutriaid_zapp_kotlin.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment()
    private val shoppingFragment = ShoppingFragment()
    private val chartFragment = ChartFragment()
    private val recipeListFragment = RecipeListFragment()
    private val profileFragment = ProfileFragment()
    private val loginFragment = LoginFragment()
    private val registerFragment = RegisterFragment()

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        val currentUser = auth.currentUser
        Firebase.auth.signOut()
        if(currentUser != null) {
            replaceFragment(homeFragment)
        } else {
            replaceFragment(loginFragment)
        }

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
            fragTransaction.commit()
        }
    }
}
