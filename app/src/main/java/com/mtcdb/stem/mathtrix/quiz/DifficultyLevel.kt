package com.mtcdb.stem.mathtrix.quiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.mtcdb.stem.mathtrix.BaseDrawerActivity
import com.mtcdb.stem.mathtrix.R

@Suppress("DEPRECATION")
class DifficultyLevel : BaseDrawerActivity() {

    private lateinit var toolbar: Toolbar

    @SuppressLint("MissingInflatedId", "RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty_level)

        toolbar = findViewById(R.id.quiz_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupDrawer(toolbar) // Setup the drawer layout

        supportFragmentManager.beginTransaction().replace(R.id.quiz_container, Difficulty())
            .commit()

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setCheckedItem(R.id.nav_item_quiz)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                showExitQuizDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        showExitQuizDialog()
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
                        .replace(R.id.quiz_container, Difficulty())
                        .commit()
                }
                .setPositiveButton("Cancel", null)
                .show()
        } else {
            super.onBackPressed()
        }
    }

}