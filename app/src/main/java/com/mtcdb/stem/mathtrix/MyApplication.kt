package com.mtcdb.stem.mathtrix

import android.app.*
import androidx.appcompat.app.*
import androidx.preference.*

class MyApplication : Application() {
    companion object {
        lateinit var instance : MyApplication
            private set
    }

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
    }

    private fun applyTheme(theme : String) {
        when (theme) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}
