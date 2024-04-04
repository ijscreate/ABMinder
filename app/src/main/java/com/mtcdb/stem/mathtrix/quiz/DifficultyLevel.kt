package com.mtcdb.stem.mathtrix.quiz

import android.annotation.*
import android.os.*
import android.view.*
import androidx.appcompat.app.*
import androidx.appcompat.widget.*
import com.google.android.material.dialog.*
import com.mtcdb.stem.mathtrix.*

@Suppress("DEPRECATION")
class DifficultyLevel : AppCompatActivity() {
    private lateinit var toolbar : Toolbar

    @SuppressLint("MissingInflatedId", "RestrictedApi")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty_level)

        toolbar = findViewById(R.id.quiz_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction().replace(R.id.quiz_container, Difficulty())
            .commit()

    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
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

    @SuppressLint("InflateParams")
    override fun onDestroy() {
        val mainActivity = layoutInflater.inflate(R.layout.activity_main, null)
        val toolbar = mainActivity.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "ABMinder"
        super.onDestroy()
    }
}