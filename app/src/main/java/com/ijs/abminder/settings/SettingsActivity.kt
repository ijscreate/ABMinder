package com.ijs.abminder.settings

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SeekBarPreference
import com.ijs.abminder.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var themePreference : String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        // Apply the initial theme
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        themePreference = preferences.getString("theme_preference", "system") ?: "system"
        applyTheme(themePreference)

        setContentView(R.layout.activity_settings)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.settings, SettingsFragment())
                .commit()
        }

        // Register the listener
        preferences.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == "theme_preference") {
                val newTheme = preferences.getString("theme_preference", "light") ?: "light"
                if (newTheme != themePreference) {
                    themePreference = newTheme
                    applyThemeWithAnimation()
                }
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
    }

    private fun applyThemeWithAnimation() {
        val options = ActivityOptions.makeSceneTransitionAnimation(this)
        startActivity(intent, options.toBundle())
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    // If there are fragments in the back stack, pop the fragment
                    supportFragmentManager.popBackStack()
                } else {
                    this.finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        @SuppressLint("DiscouragedApi")
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

            val creditPreference = findPreference<Preference>("credits")

            creditPreference?.setOnPreferenceClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.settings, CreditFragment()).addToBackStack(null).commit()
                true
            }

            val ttsSpeedPreference = findPreference<SeekBarPreference>("tts_speed_preference")

            ttsSpeedPreference?.setOnPreferenceChangeListener { preference, newValue ->
                if (preference is SeekBarPreference && newValue is Int) {
                    // Save the new TTS speed to SharedPreferences
                    context?.let {
                        PreferenceManager.getDefaultSharedPreferences(it)
                            .edit()
                            .putInt("tts_speed_preference", newValue)
                            .apply()
                    }
                }
                true
            }

        }
    }
}