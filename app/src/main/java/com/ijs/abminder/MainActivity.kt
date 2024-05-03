package com.ijs.abminder

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.ijs.abminder.calculator.CalculatorOptionsActivity
import com.ijs.abminder.dictionary.ui.DictionaryActivity
import com.ijs.abminder.learn.LearnActivity
import com.ijs.abminder.quiz.QuizActivity
import com.ijs.abminder.settings.SettingsActivity
import kotlin.random.Random

class MainActivity : BaseDrawerActivity() {


    lateinit var toolbar : Toolbar
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var layoutCalculator : LinearLayout
    private var backPressedTime : Long = 0
    private lateinit var sharedPreferences : SharedPreferences

    // List of random tips or motivations
    private val tipsList = listOf(
        "Stay focused and never give up!",
        "Take breaks to recharge your energy.",
        "Set achievable goals and celebrate your progress.",
        "Keep learning and expanding your skills.",
        "Believe in yourself and your abilities.",
        "Stay positive and optimistic.",
        "Surround yourself with supportive people.",
        "Stay organized and prioritize your tasks.",
        "Embrace challenges as opportunities for growth.",
        "Stay persistent and persevere through obstacles.",
        "Practice time management and avoid procrastination.",
        "Maintain a healthy work-life balance.",
        "Learn from your mistakes and use them as learning experiences.",
        "Stay curious and open-minded to new perspectives.",
        "Develop a growth mindset and embrace continuous improvement.",
        "Celebrate small wins and acknowledge your progress.",
        "Stay disciplined and consistent in your efforts.",
        "Seek feedback and use it constructively.",
        "Find inspiration in others' success stories.",
        "Practice self-care and prioritize your well-being.",
        "Stay humble and always strive to learn more.",
        "Embrace new challenges and step out of your comfort zone.",
        "Cultivate a positive attitude and spread positivity.",
        "Stay resilient and bounce back from setbacks.",
        "Develop strong time management skills.",
        "Practice mindfulness and stay present in the moment.",
        "Celebrate diversity and embrace different perspectives.",
        "Stay accountable and take responsibility for your actions.",
        "Develop effective communication skills.",
        "Practice gratitude and appreciate the little things.",
        "Stay determined and never lose sight of your goals.",
        "Embrace change and adapt to new situations.",
        "Stay focused on continuous self-improvement.",
        "Cultivate a sense of purpose and passion for what you do.",
        "Stay motivated and inspired by your dreams and aspirations.",
        "Learn about different accounting principles and practices.",
        "Stay updated with the latest business trends and technologies.",
        "Develop critical thinking and problem-solving skills.",
        "Network and build professional connections.",
        "Seek internships or practical experience in your field.",
        "Learn effective marketing and business strategies.",
        "Develop strong leadership and teamwork skills.",
        "Stay organized and detail-oriented in your work.",
        "Embrace ethical practices and integrity in business.",
        "Develop financial literacy and investment knowledge.",
        "Stay adaptable and open to learning new business models.",
        "Cultivate entrepreneurial skills and innovative thinking.",
        "Learn to analyze and interpret financial statements.",
        "Stay informed about relevant laws and regulations.",
        "Develop effective communication skills for presentations and meetings.",
        "Embrace continuous learning and professional development.",
        "Stay motivated by the potential for career growth and advancement.",
        "Learn to manage resources and budgets effectively.",
        "Cultivate strategic thinking and decision-making skills.",
        "Stay passionate about your chosen field and its impact.",
        "Believe in yourself and your abilities.",
        "Embrace challenges as opportunities to grow.",
        "Find inspiration in those around you.",
        "Practice mindfulness and stay present.",
        "Surround yourself with positive influences.",
        "Celebrate small wins along the way.",
        "Failure is a stepping stone to success.",
        "Consistency is key to achieving your goals.",
        "Stay curious and ask questions.",
        "Embrace your unique strengths and talents.",
        "Seek out new experiences and perspectives.",
        "Prioritize self-care and well-being.",
        "Collaborate and learn from others.",
        "Embrace change and adapt with flexibility.",
        "Perseverance pays off in the long run.",
        "Celebrate diversity and inclusivity.",
        "Focus on solutions, not problems.",
        "Take calculated risks and step out of your comfort zone.",
        "Cultivate gratitude for the present moment.",
        "Continuously challenge yourself to grow.",
        "Seek feedback and use it to improve.",
        "Practice patience and resilience.",
        "Embrace lifelong learning and curiosity.",
        "Surround yourself with supportive individuals.",
        "Find joy in the journey, not just the destination.",
        "Trust in your abilities and believe in yourself.",
        "Develop a growth mindset and embrace learning from mistakes.",
        "Cultivate resilience and bounce back from setbacks.",
        "Nurture your passions and pursue your dreams.",
        "Simplify your life and focus on what truly matters.",
        "Embrace diversity and learn from different perspectives.",
        "Practice self-compassion and be kind to yourself.",
        "Find balance and prioritize your well-being.",
        "Celebrate progress, no matter how small.",
        "Embrace change as an opportunity for growth.",
        "Surround yourself with positivity and uplifting influences.",
        "Seek out mentors and learn from their experiences.",
        "Cultivate patience and allow things to unfold naturally.",
        "Practice mindfulness and live in the present moment.",
        "Embrace challenges as opportunities to develop resilience.",
        "Celebrate your uniqueness and individuality.",
        "Find joy in the simple pleasures of life.",
        "Cultivate a positive mindset and outlook on life.",
        "Embrace lifelong learning and continuous self-improvement.",
        "Surround yourself with supportive individuals who uplift you.",
        "Celebrate your progress and acknowledge your achievements.",
        "Embrace change as a catalyst for personal growth."

    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("InflateParams", "MissingInflatedId")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize toolbar and other UI components
        initViews()
        displayRandomTip()

        val calcView = layoutInflater.inflate(
            R.layout.activity_calculator, null
        )
        layoutCalculator = calcView.findViewById(R.id.calculator_layout)

        toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        onBackPressedDispatcher.addCallback(this) {
            handleBackPressed()
        }

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        if (isFirstTimeUser()) {
            // showUnderDevelopmentMessage()
            markFirstTimeUser()
        }
    }

    private fun isFirstTimeUser() : Boolean {
        return sharedPreferences.getBoolean("firstTimeUser", true)
    }

    private fun markFirstTimeUser() {
        sharedPreferences.edit().putBoolean("firstTimeUser", false).apply()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupDrawer(toolbar)

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setCheckedItem(R.id.nav_item_home)

        findViewById<CardView>(R.id.dictionary_card).setOnClickListener {
            startActivity(Intent(this, DictionaryActivity::class.java))

        }

        findViewById<CardView>(R.id.calculator_card).setOnClickListener {
            val intent = Intent(this, CalculatorOptionsActivity::class.java)
            startActivity(intent)

        }

        findViewById<CardView>(R.id.learn_card).setOnClickListener {
            val intent = Intent(this, LearnActivity::class.java)
            startActivity(intent)

        }

        findViewById<CardView>(R.id.quiz_card).setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayRandomTip() {
        // Get a random index within the range of the tipsList
        val randomIndex = Random.nextInt(tipsList.size)

        // Get the random tip using the random index
        val randomTip = tipsList[randomIndex]
        val textViewRandomTip = findViewById<TextView>(R.id.randomTips)

        // Display the random tip on the TextView
        textViewRandomTip?.text = randomTip
    }

    private fun handleBackPressed() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        // Check if the navigation drawer is open
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // Close the navigation drawer
            drawerLayout.closeDrawer(GravityCompat.START)
        } else if (supportFragmentManager.backStackEntryCount > 0) {
            // If there are fragments in the back stack, pop the fragment
            supportFragmentManager.popBackStack()
        } else {
            // If there are no fragments in the back stack
            toolbar.title = getString(R.string.app_name)
            handleDoubleBackToExit()
        }
    }

    private fun handleDoubleBackToExit() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            // If the user presses the back button twice within 2 seconds, finish the hosting activity
            finishAndRemoveTask()
        } else {
            //doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
            backPressedTime = System.currentTimeMillis()
        }
    }

    override fun onCreateOptionsMenu(menu : Menu?) : Boolean {
        menuInflater.inflate(R.menu.menu_app_bar, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu : Menu) : Boolean {
        toolbar.navigationIcon = ContextCompat.getDrawable(
            this, R.drawable.menu
        )
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Open or close the navigation drawer
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
                true
            }

            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                val bundle = Bundle().apply {
                    ActivityOptions.makeCustomAnimation(
                        this@MainActivity, R.transition.zoom_in, R.transition.zoom_out
                    )
                }

                startActivity(intent, bundle)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        toolbar.title = getString(R.string.app_name)
        navView.setCheckedItem(R.id.nav_item_home)
    }
}
