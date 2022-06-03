package com.example.nutriaid_zapp_kotlin

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment()
    private val shoppingFragment = ShoppingFragment()
    private val statisticFragment = StatisticFragment()
    private val recipeFragment = RecipeFragment()
    private val loginFragment = LoginFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(homeFragment)

        val bottom_nav : BottomNavigationView = findViewById(R.id.bottom_nav)
        val profile_button : ImageButton = findViewById(R.id.profile_button)

        profile_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                //TODO: gotto Profile Fragment/Activity
            }
        })

        bottom_nav.setOnNavigationItemSelectedListener {
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
