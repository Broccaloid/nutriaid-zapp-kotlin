package com.example.nutriaid_zapp_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.nutriaid_zapp_kotlin.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment()
    private val shoppingFragment = ShoppingFragment()
    private val statisticFragment = StatisticFragment()
    private val recipeFragment = RecipeFragment()
    private val loginFragment = LoginFragment()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(homeFragment)

        val bottomNav : BottomNavigationView = findViewById(R.id.bottom_nav)
        val profileButton : ImageButton = findViewById(R.id.profile_button)

        profileButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                //TODO: gotto Profile Fragment/Activity
            }
        })

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> replaceFragment(homeFragment)
                R.id.nav_shopping -> replaceFragment(shoppingFragment)
                R.id.nav_statistic -> replaceFragment(statisticFragment)
                R.id.nav_recipe -> replaceFragment(recipeFragment)

            }
            true
        }
    }

    public fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, fragment)
            transaction.commit()
        }
    }
}
