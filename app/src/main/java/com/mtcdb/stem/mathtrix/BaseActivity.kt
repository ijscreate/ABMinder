package com.mtcdb.stem.mathtrix

import android.content.*
import android.os.*
import androidx.activity.*
import androidx.appcompat.app.*
import androidx.appcompat.widget.*
import androidx.drawerlayout.widget.*
import com.google.android.material.navigation.*
import com.mtcdb.stem.mathtrix.calculator.*
import com.mtcdb.stem.mathtrix.dictionary.*
import com.mtcdb.stem.mathtrix.learn.subjects.*
import com.mtcdb.stem.mathtrix.quiz.*

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
            override fun handleOnBackPressed () {

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
