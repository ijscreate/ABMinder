package com.ijs.abminder.learn.subjects

import android.annotation.*
import android.os.*
import androidx.appcompat.widget.*
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.google.android.material.floatingactionbutton.*
import com.google.android.material.navigation.*
import com.ijs.abminder.*
import com.ijs.abminder.learn.*
import com.ijs.abminder.learn.chapters.*

class SubjectsActivity : BaseDrawerActivity() {
    lateinit var toolbar : Toolbar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subjects)

        // Set up the toolbar and drawer
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupDrawer(toolbar)

        TransitionManager.beginDelayedTransition(
            findViewById(android.R.id.content),
            AutoTransition()
        )

        supportFragmentManager.beginTransaction().replace(R.id.content_frame, ContainerFragment())
            .commit()

        val fab = findViewById<FloatingActionButton>(R.id.fab_study)
        fab.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, PersonalizedStudyPlanFragment())
                .addToBackStack(null)
                .commit()
        }

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setCheckedItem(R.id.nav_item_learn)
    }

    private fun onSubjectSelected(subject : String) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame, ChaptersFragment.newInstance(subject))
        transaction.addToBackStack(null)
        transaction.commit()
        supportActionBar?.title = subject
    }
}