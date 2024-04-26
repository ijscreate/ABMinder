package com.ijs.abminder

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class WelcomeActivity : AppCompatActivity() {

    private lateinit var startButton : Button

    @SuppressLint("InflateParams")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_welcome)

        val displayManager = this.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val display = displayManager.displays[0]
        val supportedRefreshRates =
            display.supportedModes.map { it.refreshRate }.distinct().sorted()

        // Check if any refresh rate greater than 60 Hz is supported
        val supportsHighRefreshRate = supportedRefreshRates.any { it > 60.0f }
        if (supportsHighRefreshRate) {
            val desiredMode = display.supportedModes.find { it.refreshRate == 90.0f }
            if (desiredMode != null) {
                window.attributes = window.attributes.apply {
                    this.preferredDisplayModeId = desiredMode.modeId
                }
            }
        }

        val layout = layoutInflater.inflate(R.layout.activity_welcome, null)
        layout.visibility = View.VISIBLE

        // Check if the welcome screen has been shown before
        if (!isWelcomeScreenShown()) {
            // If not shown, display the welcome screen
            layout.visibility = View.VISIBLE
            setWelcomeScreenShown()
        } else {
            // If already shown, navigate to the main activity or any other desired screen
            navigateToMainActivity()
        }

        startButton = findViewById(R.id.startButton)
        startButton.setOnClickListener {
            navigateToMainActivity()
        }

        setFullScreen()

    }

    private fun isWelcomeScreenShown() : Boolean {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isWelcomeScreenShown", false)
    }

    private fun setWelcomeScreenShown() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isWelcomeScreenShown", true)
        editor.apply()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For devices with Android 12 (API level 31) and above
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else
        // For devices with Android versions between 21 (LOLLIPOP) and 30 (R)
            try {
                // Use reflection to check if getWindowInsetsController() method exists
                val method = Window::class.java.getMethod("getInsetsController")
                val insetsController = method.invoke(window) as WindowInsetsController?
                insetsController?.let {
                    it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                    it.systemBarsBehavior =
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            } catch (e : Exception) {
                // Handle exceptions or fallback to other methods
                e.printStackTrace()
                // For devices with Android versions below 30, you can use other methods to achieve fullscreen mode
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            }
    }

}
