package com.ijs.abminder

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.ijs.abminder.dictionary.EditTermFragment
import com.ijs.abminder.settings.SettingsActivity

class MainActivity : BaseDrawerActivity() {

    lateinit var toolbar : Toolbar
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var layoutCalculator : LinearLayout
    private var backPressedTime : Long = 0
    private lateinit var sharedPreferences : SharedPreferences

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("InflateParams", "MissingInflatedId")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupDrawer(toolbar)

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setCheckedItem(R.id.nav_item_home)

        setCurrentFragment(DashboardFragment())

        // Display a random tip or motivation when the activity is created
        //displayRandomTip()

        // Initialize toolbar and other UI components
        initViews()

        val calcView = layoutInflater.inflate(
            R.layout.fragment_calculator_options, null
        )
        layoutCalculator = calcView.findViewById(R.id.calculator_layout)

        toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        onBackPressedDispatcher.addCallback(this) {
            handleBackPressed()
        }

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        if (isFirstTimeUser()) {
            showUnderDevelopmentMessage()
            markFirstTimeUser()
        }
    }

    private fun isFirstTimeUser() : Boolean {
        return sharedPreferences.getBoolean("firstTimeUser", true)
    }

    private fun markFirstTimeUser() {
        sharedPreferences.edit().putBoolean("firstTimeUser", false).apply()
    }

    private fun showUnderDevelopmentMessage() {
        val message = """
            This app is still under development and may encounter the following:
            
            - Bugs or unexpected behavior.
            - Incomplete features or missing functionality.
            - Changes in the user interface or navigation.
            - Performance issues or slow response times.
            
            Your feedback is valuable to us as we work to improve the app. Thank you for your patience and understanding!
        """.trimIndent()

        AlertDialog.Builder(this).setTitle("UNDER DEVELOPMENT").setMessage(message)
            .setCancelable(false).setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setCurrentFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .addToBackStack(null).commit()
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun handleBackPressed() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        // Check if the navigation drawer is open
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // Close the navigation drawer
            drawerLayout.closeDrawer(GravityCompat.START)
        } else if (supportFragmentManager.backStackEntryCount > 0) {
            // If there are fragments in the back stack, pop the fragment
            toolbar.title = getString(R.string.app_name)
            supportFragmentManager.popBackStack()
        } else {
            // If there are no fragments in the back stack
            toolbar.title = getString(R.string.app_name)
            handleDoubleBackToExit()
        }
    }

    private fun handleDoubleBackToExit() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            // If the user presses the back button twice within 2 seconds, finish the hosting activity
            finish()
        } else {
            //doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
            backPressedTime = System.currentTimeMillis()
        }
    }


    override fun onCreateOptionsMenu(menu : Menu?) : Boolean {
        menuInflater.inflate(R.menu.menu_app_bar, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu : Menu) : Boolean {
        toolbar.navigationIcon = ContextCompat.getDrawable(
            this, R.drawable.menu
        )
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Open or close the navigation drawer
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
                true
            }

            /*
            R.id.action_progress -> {
                setCurrentFragment(QuizProgressFragment())
                toolbar.title = "Progress"
                true
            } */

            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                val bundle = Bundle().apply {
                    ActivityOptions.makeCustomAnimation(
                        this@MainActivity, R.transition.zoom_in, R.transition.zoom_out
                    )
                }

                startActivity(intent, bundle)
                true
            }

            R.id.action_edit_term -> {
                // Navigate to the EditTermFragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, EditTermFragment()).addToBackStack(null)
                    .commit()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}