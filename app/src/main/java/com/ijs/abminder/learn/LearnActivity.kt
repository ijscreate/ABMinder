package com.ijs.abminder.learn

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.ijs.abminder.BaseDrawerActivity
import com.ijs.abminder.R
import com.ijs.abminder.learn.subjects.SubjectsFragment

class LearnActivity : BaseDrawerActivity() {
    lateinit var toolbar : Toolbar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)

        // Set up the toolbar and drawer
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupDrawer(toolbar)

        TransitionManager.beginDelayedTransition(
            findViewById(android.R.id.content),
            AutoTransition()
        )

        supportFragmentManager.beginTransaction().replace(R.id.content_frame, SubjectsFragment())
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

    override fun onResume() {
        super.onResume()
        toolbar.title = "Learn"
        navView.setCheckedItem(R.id.nav_item_learn)
    }
}