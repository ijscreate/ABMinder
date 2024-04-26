package com.ijs.abminder

import android.app.Application
import android.hardware.display.DisplayManager
import android.os.Build
import androidx.annotation.RequiresApi
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

        var supportsHighRefreshRate = false
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate() {
        super.onCreate()
        instance = this

        // Apply the initial theme
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        preferences.getString("theme_preference", "light")?.let { applyTheme(it) }

        // Register the listener
        preferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == "theme_preference") {
                sharedPreferences.getString("theme_preference", "light")?.let { applyTheme(it) }
            }
        }

        val displayManager = getSystemService(DISPLAY_SERVICE) as DisplayManager
        val display = displayManager.displays[0]
        val supportedRefreshRates =
            display.supportedModes.map { it.refreshRate }.distinct().sorted()

        supportsHighRefreshRate = supportedRefreshRates.any { it > 60.0f }

        populateDatabases()
    }

    private fun applyTheme(theme : String) {
        when (theme) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    private fun populateDatabases() {
        val dbHelper = QuizDatabaseHelper(this)
        val quizDataPopulator = QuizDataPopulator(dbHelper)
        Thread {
            quizDataPopulator.populateQuizData()
        }.start()

        val termDatabaseHelper = DictionaryDatabaseHelper(this)
        val insertTerms = DictionaryDataInsertion(this, termDatabaseHelper)
        Thread {
            insertTerms.insert()
        }.start()
    }

}
