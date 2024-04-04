package com.mtcdb.stem.mathtrix

import android.annotation.*
import android.app.*
import android.content.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.activity.*
import androidx.annotation.*
import androidx.appcompat.app.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.*
import androidx.core.view.*
import androidx.drawerlayout.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.*
import com.google.android.material.navigation.*
import com.mtcdb.stem.mathtrix.calculator.*
import com.mtcdb.stem.mathtrix.dictionary.*
import com.mtcdb.stem.mathtrix.dictionary.database.*
import com.mtcdb.stem.mathtrix.learn.subjects.*
import com.mtcdb.stem.mathtrix.quiz.*
import com.mtcdb.stem.mathtrix.quiz.database.*
import com.mtcdb.stem.mathtrix.settings.*

class MainActivity : AppCompatActivity() {

    lateinit var toolbar : Toolbar
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var layoutCalculator : LinearLayout
    private lateinit var dbHelper : QuizDatabaseHelper
    private lateinit var termDatabaseHelper : DictionaryDatabaseHelper
    private var backPressedTime : Long = 0
    private lateinit var navView : NavigationView

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        dbHelper = QuizDatabaseHelper(this)
        val quizDataPopulator = QuizDataPopulator(dbHelper)
        quizDataPopulator.populateQuizData()

        termDatabaseHelper = DictionaryDatabaseHelper(this)
        val insertTerms = DictionaryDataInsertion(this, termDatabaseHelper)
        insertTerms.insert()


        val fab = findViewById<FloatingActionButton>(com.mtcdb.stem.mathtrix.R.id.fab)
        drawerLayout = findViewById(R.id.drawer_layout)

        fab.setOnClickListener {
            showAddTermDialog()
        }

        // Initialize toolbar and other UI components
        initViews()

        // Set initial fragment
        setCurrentFragment(DictionaryFragment())

        val calcView = layoutInflater.inflate(
            R.layout.fragment_calculator_options, null
        )
        layoutCalculator = calcView.findViewById(R.id.calculator_layout)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close

        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView = findViewById(R.id.nav_view)
        navView.itemIconTintList = null // Disable tinting
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_item_calculator -> {
                    setCurrentFragment(CalculatorOptionsFragment())
                    toolbar.title = getString(R.string.calculator)
                }

                R.id.nav_item_dictionary -> {
                    setCurrentFragment(DictionaryFragment())
                    toolbar.title = getString(R.string.dictionary)
                }

                R.id.nav_item_learn -> {
                    setCurrentFragment(SubjectsFragment())
                    toolbar.title = "Learn"
                }

                /*
                R.id.nav_item_learn -> {
                supportFragmentManager.beginTransaction()
                .replace(
                R.id.fragment_container,
                LearnFragment.newInstance(),
                LearnFragment::class.java.simpleName
                )
                .addToBackStack(LearnFragment.TAG)
                .commit()
                drawerLayout.closeDrawer(GravityCompat.START)
                toolbar.title = getString(R.string.learn)
                }
                */


                R.id.nav_item_quiz -> {
                    val intent = Intent(this, DifficultyLevel::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    toolbar.title = getString(R.string.quiz)
                    navView.setCheckedItem(R.id.nav_item_dictionary)
                }
            }
            return@setNavigationItemSelectedListener true
        }
        navView.setCheckedItem(R.id.nav_item_dictionary)

        onBackPressedDispatcher.addCallback(this) {
            handleBackPressed()
        }
    }

    override fun onDestroy() {
        navView.setCheckedItem(R.id.nav_item_dictionary)
        super.onDestroy()
    }

    override fun onResume() {
        navView.setCheckedItem(R.id.nav_item_dictionary)
        super.onResume()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setCurrentFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
        drawerLayout.closeDrawer(GravityCompat.START)
        // Update FAB visibility based on the current fragment
        setFabVisibility(fragment)
    }


    private fun setFabVisibility(fragment : Fragment) {
        val fab = findViewById<FloatingActionButton>(com.mtcdb.stem.mathtrix.R.id.fab)
        fab.visibility = when (fragment) {
            is DictionaryFragment -> View.VISIBLE
            else -> View.GONE
        }
    }

    private fun showAddTermDialog() {
        val dialogView = LayoutInflater.from(this@MainActivity)
            .inflate(R.layout.dialog_add_term, null)
        val termEditText =
            dialogView.findViewById<EditText>(R.id.termEditText)
        val definitionEditText =
            dialogView.findViewById<EditText>(R.id.definitionEditText)
        val exampleEditText =
            dialogView.findViewById<EditText>(R.id.exampleEditText)

        val dialog =
            AlertDialog.Builder(this@MainActivity).setTitle("Add New Term").setView(dialogView)
                .setPositiveButton("Add") { _, _ ->
                    val term = termEditText.text.toString().trim()
                    val definition = definitionEditText.text.toString().trim()
                    val example = exampleEditText.text.toString().trim()
                    addTermToDatabase(term, definition, example)
                    clearInputFields(dialogView) // Clear input fields after adding the term
                }.setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }.create()

        dialog.show()
    }


    private fun addTermToDatabase(term : String, definition : String?, example : String?) {
        if (term.isEmpty() && definition.isNullOrBlank()) {
            // Inform the user that the term is required
            Toast.makeText(
                this@MainActivity,
                "Term and definition are required.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val dbHelper = DictionaryDatabaseHelper(this@MainActivity)
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put("term", term)
            definition?.let { put("definition", it) }
            example?.let { put("example", it) }
        }

        val newTerm = db.insert("dictionary_terms", null, values)

        if (newTerm != -1L) {
            // Term added successfully
            Toast.makeText(this@MainActivity, "Term added to the database.", Toast.LENGTH_SHORT)
                .show()
        } else {
            // Failed to add term
            Toast.makeText(
                this@MainActivity, "Failed to add term to the database.", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun clearInputFields(dialogView : View) {
        // Clear input fields after adding the term
        dialogView.findViewById<EditText>(R.id.termEditText).text = null
        dialogView.findViewById<EditText>(R.id.definitionEditText).text =
            null
        dialogView.findViewById<EditText>(R.id.exampleEditText).text = null
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

            R.id.action_progress -> {
                setCurrentFragment(QuizProgressFragment())
                toolbar.title = "Progress"
                true
            }

            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                val bundle = Bundle().apply {
                    ActivityOptions.makeCustomAnimation(
                        this@MainActivity,
                        R.transition.zoom_in,
                        R.transition.zoom_out
                    )
                }

                startActivity(intent, bundle)
                true
            }

            R.id.action_edit_term -> {
                // Navigate to the EditTermFragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, EditTermFragment())
                    .addToBackStack(null).commit()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
