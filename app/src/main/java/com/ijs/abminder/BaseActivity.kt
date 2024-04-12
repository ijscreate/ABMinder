package com.ijs.abminder

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.ijs.abminder.calculator.CalculatorOptionsActivity
import com.ijs.abminder.dictionary.DictionaryActivity
import com.ijs.abminder.learn.subjects.SubjectsActivity
import com.ijs.abminder.quiz.DifficultyLevel

open class BaseDrawerActivity : AppCompatActivity() {

    private lateinit var drawerLayout : DrawerLayout
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var navView : NavigationView

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_layout)

        navView = findViewById(R.id.nav_view)
        navView.itemIconTintList = null

        // Set up back press dispatcher
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    finish()
                }
            }
        })
    }

    protected fun setupDrawer(toolbar : Toolbar) {
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.itemIconTintList = null
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_item_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.nav_item_calculator -> {
                    val intent = Intent(this, CalculatorOptionsActivity::class.java)
                    startActivity(intent)
                    toolbar.title = getString(R.string.calculator)
                    navView.setCheckedItem(R.id.nav_item_calculator)
                    true
                }

                R.id.nav_item_dictionary -> {
                    startActivity(Intent(this, DictionaryActivity::class.java))
                    toolbar.title = getString(R.string.dictionary)
                    navView.setCheckedItem(R.id.nav_item_dictionary)
                    true
                }

                R.id.nav_item_learn -> {
                    val intent = Intent(this, SubjectsActivity::class.java)
                    startActivity(intent)
                    toolbar.title = "Learn"
                    navView.setCheckedItem(R.id.nav_item_learn)
                    true
                }

                R.id.nav_item_quiz -> {
                    val intent = Intent(this, DifficultyLevel::class.java)
                    startActivity(intent)
                    toolbar.title = getString(R.string.quiz)
                    navView.setCheckedItem(R.id.nav_item_dictionary)
                    true
                }
                // Handle other navigation items similarly
                else -> false
            }
        }
    }
}
