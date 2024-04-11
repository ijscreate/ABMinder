package com.mtcdb.stem.mathtrix

import android.annotation.*
import android.content.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.annotation.*
import androidx.appcompat.app.*
import com.mtcdb.stem.mathtrix.dictionary.database.*
import com.mtcdb.stem.mathtrix.quiz.database.*

@Suppress("DEPRECATION")
class WelcomeActivity : AppCompatActivity() {

    private lateinit var startButton : Button
    private lateinit var dbHelper : QuizDatabaseHelper
    private lateinit var termDatabaseHelper : DictionaryDatabaseHelper

    @SuppressLint("InflateParams")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val layout = layoutInflater.inflate(R.layout.activity_welcome, null)
        layout.visibility = View.VISIBLE

        dbHelper = QuizDatabaseHelper(this)
        val quizDataPopulator = QuizDataPopulator(dbHelper)
        quizDataPopulator.populateQuizData()

        termDatabaseHelper = DictionaryDatabaseHelper(this)
        val insertTerms = DictionaryDataInsertion(this, termDatabaseHelper)
        insertTerms.insert()

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
