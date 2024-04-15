package com.ijs.abminder

import android.app.ActivityOptions
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.ijs.abminder.dictionary.database.DictionaryDataInsertion
import com.ijs.abminder.dictionary.database.DictionaryDatabaseHelper
import com.ijs.abminder.quiz.database.QuizDataPopulator
import com.ijs.abminder.quiz.database.QuizDatabaseHelper

class MyApplication : Application() {
    companion object {
        lateinit var instance : MyApplication
            private set
    }

    private lateinit var dbHelper : QuizDatabaseHelper
    private lateinit var termDatabaseHelper : DictionaryDatabaseHelper

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Apply the initial theme
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        preferences.getString("theme_preference", "system")?.let { applyTheme(it) }

        // Register the listener
        preferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == "theme_preference") {
                sharedPreferences.getString("theme_preference", "system")?.let { applyTheme(it) }
            }
        }

        dbHelper = QuizDatabaseHelper(this)
        val quizDataPopulator = QuizDataPopulator(dbHelper)
        quizDataPopulator.populateQuizData()

        termDatabaseHelper = DictionaryDatabaseHelper(this)
        val insertTerms = DictionaryDataInsertion(this, termDatabaseHelper)
        insertTerms.insert()
    }

    private fun applyTheme(theme : String) {
        when (theme) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}
