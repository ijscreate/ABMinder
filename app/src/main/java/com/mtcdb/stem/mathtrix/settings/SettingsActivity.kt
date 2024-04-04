package com.mtcdb.stem.mathtrix.settings

import android.annotation.*
import android.content.*
import android.os.*
import android.view.*
import androidx.appcompat.app.*
import androidx.preference.*
import com.mtcdb.stem.mathtrix.R

class SettingsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)


        // Apply the initial theme
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        preferences.getString("theme_preference", "system")?.let { applyTheme(it) }

        setContentView(R.layout.settings_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.settings, SettingsFragment())
                .commit()
        }

        // Register the listener
        preferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == "theme_preference") {
                sharedPreferences.getString("theme_preference", "system")?.let { applyTheme(it) }
            }
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.title = "Settings"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun applyTheme(theme : String) {
        when (theme) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
        this.recreate()
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                val intent =
                    Intent(this@SettingsActivity, com.mtcdb.stem.mathtrix.MainActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState : Bundle?, rootKey : String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val usPreference = findPreference<Preference>("about_us")

            usPreference?.setOnPreferenceClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.settings, AboutUsFragment()).addToBackStack(null).commit()
                true
            }

            val aboutAbminderPreference = findPreference<Preference>("about_app")

            aboutAbminderPreference?.setOnPreferenceClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.settings, AboutAbminderFragment()).addToBackStack(null).commit()
                true
            }

        }

    }
}



