package com.strink.themoviedb.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.strink.themoviedb.R
import com.strink.themoviedb.databinding.ActivityHomeBinding
import com.strink.themoviedb.fragment.FavoritesFragment
import com.strink.themoviedb.fragment.HomeFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private var previousMenuItem: MenuItem? = null
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        setupToolbar()

        setupActionBarToggle()

        displayHome()

        navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            if (previousMenuItem != null)
                previousMenuItem?.isChecked = false

            item.isCheckable = true
            item.isChecked = true

            previousMenuItem = item



            val fragmentTransaction = supportFragmentManager.beginTransaction()

            when(item.itemId) {
                R.id.home -> {
                    val homeFragment = HomeFragment()
                    fragmentTransaction.replace(R.id.frame, homeFragment)
                    fragmentTransaction.commit()
                    supportActionBar?.title = "Popular Movies"
                    drawerLayout.closeDrawers()
                }
                R.id.favorites -> {
                    val favFragment = FavoritesFragment()
                    fragmentTransaction.replace(R.id.frame, favFragment)
                    fragmentTransaction.commit()
                    supportActionBar?.title = "Favorites"
                    drawerLayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    private fun displayHome() {
        val fragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
        supportActionBar?.title = "All Movies"
        navigationView.setCheckedItem(R.id.home)
    }

    private fun setupActionBarToggle() {
        actionBarDrawerToggle =
            object : ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.openDrawer,
                R.string.closeDrawer
            ) {
                override fun onDrawerStateChanged(newState: Int) {
                    super.onDrawerStateChanged(newState)
                    val pendingRunnable = Runnable {
                        val inputMethodManager =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                    }
                    Handler().postDelayed(pendingRunnable, 50)
                }
            }

        drawerLayout.addDrawerListener(actionBarDrawerToggle)

        actionBarDrawerToggle.syncState()

    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    private fun init() {
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigation_view)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openHome () {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, HomeFragment())
            .commit()
        supportActionBar?.title = "All Movies"
        navigationView.setCheckedItem(R.id.home)
    }

    override fun onBackPressed() {
        when(supportFragmentManager.findFragmentById(R.id.frame)) {
            !is HomeFragment -> openHome()
            else -> super.onBackPressed()
        }
    }
}