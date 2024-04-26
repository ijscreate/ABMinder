package com.ijs.abminder.quiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.ijs.abminder.BaseDrawerActivity
import com.ijs.abminder.R
import com.ijs.abminder.quiz.progress.ProgressFragment

@Suppress("DEPRECATION")
class QuizActivity : BaseDrawerActivity() {

    private lateinit var toolbar : Toolbar

    @SuppressLint("MissingInflatedId", "RestrictedApi")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        toolbar = findViewById(R.id.quiz_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)

        setupDrawer(toolbar) // Setup the drawer layout

        supportFragmentManager.beginTransaction().replace(R.id.quiz_container, DifficultyFragment())
            .commit()

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setCheckedItem(R.id.nav_item_quiz)

        onBackPressedDispatcher.addCallback(this) {
            showExitQuizDialog()
        }
    }

    override fun onCreateOptionsMenu(menu : Menu?) : Boolean {
        menuInflater.inflate(R.menu.menu_quiz, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                showExitQuizDialog()
            }

            R.id.action_progress -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.quiz_container, ProgressFragment())
                    .commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        toolbar.title = getString(R.string.quiz)
        navView.setCheckedItem(R.id.nav_item_quiz)
    }

    private fun showExitQuizDialog() {
        val quizFragment =
            supportFragmentManager.findFragmentById(R.id.quiz_container) as? QuizFragment
        if (quizFragment != null && quizFragment.isVisible) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Leave QUIZ?")
                .setMessage(
                    "Are you sure you want to leave the QUIZ? \n" +
                            "Any progress will be lost."
                )
                .setNegativeButton("Yes") { _, _ ->
                    supportFragmentManager.beginTransaction().remove(quizFragment).commit()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.quiz_container, DifficultyFragment())
                        .commit()
                }
                .setPositiveButton("Cancel", null)
                .show()
        } else {
            finish()
        }
    }
}