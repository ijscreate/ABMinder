package com.ijs.abminder.dictionary.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.ijs.abminder.BaseDrawerActivity
import com.ijs.abminder.R
import com.ijs.abminder.dictionary.adapter.DictionarySuggestionAdapter
import com.ijs.abminder.dictionary.database.DictionaryDatabaseHelper
import com.ijs.abminder.dictionary.recent.RecentSearch
import com.ijs.abminder.dictionary.recent.RecentSearchAdapter
import java.util.Locale

class DictionaryActivity : BaseDrawerActivity(), TextToSpeech.OnInitListener {

    private lateinit var searchView : SearchView
    private lateinit var termTextView : TextView
    private lateinit var termDefinitionTextView : TextView
    private lateinit var termExamplesListView : TextView
    private lateinit var layoutDictionary : LinearLayout
    private lateinit var recentSearchesRecyclerView : RecyclerView
    private lateinit var recentSearchesAdapter : RecentSearchAdapter
    private lateinit var viewModel : DictionaryViewModel
    private lateinit var toolbar : Toolbar
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var textToSpeech : TextToSpeech
    private lateinit var micButton : ImageButton
    private lateinit var voiceInput : ImageButton
    private lateinit var speechRecognizer : SpeechRecognizer
    private lateinit var recentSearch : LinearLayout
    private lateinit var shareButton : ImageButton
    private lateinit var speechRecognitionDialog : Dialog
    private lateinit var textRecognized : TextView
    private lateinit var bookmark : ImageButton

    var bookmarkedTerm : String = ""
    private val RECORD_AUDIO_PERMISSION_REQUEST_CODE = 101

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)

        // Initialize views and components
        initViews()

        // Check if the permission is already granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission is already granted, proceed with speech recognition
            voiceInput.setOnClickListener {
                startSpeechToText()
                showSpeechRecognitionDialog()
            }
        }

        TransitionManager.beginDelayedTransition(
            findViewById(android.R.id.content), AutoTransition()
        )

        // Initialize and set up the RecentSearchAdapter
        recentSearchesAdapter = RecentSearchAdapter(this, { clickedSearch ->
            Toast.makeText(this, clickedSearch.query, Toast.LENGTH_SHORT).show()
        }) {}

        recentSearchesRecyclerView.adapter = recentSearchesAdapter

        // Observe changes in recent searches
        viewModel.recentSearches.observe(this) { searches ->
            recentSearchesAdapter.submitList(searches)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query : String?) : Boolean {
                if (!query.isNullOrEmpty()) {
                    // Perform the search with the submitted query
                    searchView.setQuery(query, false)
                    searchView.clearFocus()
                    performSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText : String?) : Boolean {
                if (newText.isNullOrEmpty()) {
                    layoutDictionary.visibility = View.GONE
                    recentSearch.visibility = View.VISIBLE
                }

                // Update the cursor in the adapter as the user types
                val cursor = getSuggestionsCursor(newText)
                searchView.suggestionsAdapter = DictionarySuggestionAdapter(
                    this@DictionaryActivity, cursor
                )
                return true
            }
        })

        searchView.suggestionsAdapter = null
        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position : Int) : Boolean {
                return true
            }

            override fun onSuggestionClick(position : Int) : Boolean {
                val cursor = searchView.suggestionsAdapter?.getItem(position) as Cursor
                val term = cursor.getString(cursor.getColumnIndexOrThrow("term"))
                val definition = cursor.getString(cursor.getColumnIndexOrThrow("definition"))
                val example = cursor.getString(cursor.getColumnIndexOrThrow("example"))

                termTextView.text = term
                termDefinitionTextView.text = definition
                termExamplesListView.text = example
                searchView.setQuery(term, false)
                searchView.clearFocus()
                layoutDictionary.visibility = View.VISIBLE
                recentSearch.visibility = View.GONE
                // Adding the recent search
                handleRecentSearch(term, definition, example)
                return true
            }
        })

        onBackPressedDispatcher.addCallback(this) { finish() }
    }

    private fun initViews() {
        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.dicToolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val fab = findViewById<FloatingActionButton>(com.ijs.abminder.R.id.fab)

        fab.setOnClickListener {
            showAddTermDialog()
        }

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setCheckedItem(R.id.nav_item_dictionary)
        setupDrawer(toolbar)
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        searchView = findViewById(R.id.searchView)
        searchView.queryHint = "Search terms..."
        layoutDictionary = findViewById(R.id.dictionary_layout)
        layoutDictionary.visibility = View.GONE

        termTextView = findViewById(R.id.termTextView)
        termDefinitionTextView = findViewById(R.id.term_definition)
        termExamplesListView = findViewById(R.id.term_examples)
        recentSearchesRecyclerView = findViewById(R.id.recent_searches_list)

        shareButton = findViewById(R.id.shareImageButton)
        shareButton.setOnClickListener {
            val term = termTextView.text.toString()
            val definition = termDefinitionTextView.text.toString()
            val example = termExamplesListView.text.toString()
            shareTerm(term, definition, example)
        }

        recentSearch = findViewById(R.id.recentSearch)
        recentSearch.visibility = View.VISIBLE

        viewModel = DictionaryViewModel()

        bookmark = findViewById(R.id.bookmark)
        bookmark.setOnClickListener {
            bookmarkedTerm = termTextView.text.toString()
        }

        // Initialize Text-to-Speech engine
        textToSpeech = TextToSpeech(this, this)

        micButton = findViewById(R.id.speakButton)
        micButton.setOnClickListener {
            speakText(searchView.query.toString())
        }

        speechRecognitionDialog = Dialog(this)
        speechRecognitionDialog.setContentView(R.layout.dialog_speech_recognition)
        voiceInput = findViewById(R.id.voiceInput)
        textRecognized = speechRecognitionDialog.findViewById(R.id.textRecognized)

        // Initialize SpeechRecognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params : Bundle?) {}

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB : Float) {}

            override fun onBufferReceived(buffer : ByteArray?) {}

            override fun onEndOfSpeech() {}

            override fun onError(error : Int) {
                val errorMessage = when (error) {
                    SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
                    SpeechRecognizer.ERROR_CLIENT -> "Client side error"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
                    SpeechRecognizer.ERROR_NETWORK -> "Network error"
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
                    SpeechRecognizer.ERROR_NO_MATCH -> "No recognition result matched"
                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
                    SpeechRecognizer.ERROR_SERVER -> "Server error"
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
                    else -> "Unknown error"
                }
                Toast.makeText(
                    this@DictionaryActivity,
                    "An error occurred: $errorMessage",
                    Toast.LENGTH_SHORT
                ).show()
                speechRecognitionDialog.dismiss()
            }

            override fun onResults(results : Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    val query = matches[0]
                    searchView.setQuery(query, true)
                    textRecognized.text = query
                }
                speechRecognitionDialog.dismiss()
            }

            override fun onPartialResults(partialResults : Bundle?) {
                // Get the recognized text from partialResults bundle
                val matches =
                    partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    val recognizedText = matches[0]
                    // Update the textRecognized TextView with recognized text
                    textRecognized.text = recognizedText
                    Log.d(TAG, "Partial result: $recognizedText")
                }
            }

            override fun onEvent(eventType : Int, params : Bundle?) {}
        })

        // Retrieve the default SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        // Retrieve the TTS speed preference
        val ttsSpeed = sharedPreferences.getInt("tts_speed_preference", 100)

        // Set up TextToSpeech
        textToSpeech = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                // Set up TTS speed
                val speed = ttsSpeed.toFloat() / 100.0f
                textToSpeech.setSpeechRate(speed)
            }
        }

    }

    private fun startSpeechToText() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to search")
        speechRecognizer.startListening(intent)
    }

    private fun showSpeechRecognitionDialog() {
        speechRecognitionDialog.setCancelable(false)
        speechRecognitionDialog.show()
    }

    override fun onRequestPermissionsResult(
        requestCode : Int,
        permissions : Array<out String>,
        grantResults : IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RECORD_AUDIO_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, proceed with speech recognition
                    voiceInput.setOnClickListener {
                        startSpeechToText()
                        showSpeechRecognitionDialog()
                    }
                } else {
                    // Permission denied, inform the user
                    Toast.makeText(
                        this,
                        "Permission denied. Speech recognition cannot proceed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun shareTerm(term : String, definition : String, example : String?) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Dictionary Term: $term")
        val shareMessage = "Term: $term\nDefinition: $definition\nExample: $example"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "Share Term"))
    }


    private fun showAddTermDialog() {
        val dialogView =
            LayoutInflater.from(this).inflate(R.layout.dialog_add_term, null)
        val termEditText = dialogView.findViewById<EditText>(R.id.termEditText)
        val definitionEditText = dialogView.findViewById<EditText>(R.id.definitionEditText)
        val exampleEditText = dialogView.findViewById<EditText>(R.id.exampleEditText)

        val dialog =
            AlertDialog.Builder(this).setTitle("Add New Term").setView(dialogView)
                .setPositiveButton("Add") { _, _ ->
                    val term = termEditText.text.toString().trim()
                    val definition = definitionEditText.text.toString().trim()
                    val example = exampleEditText.text.toString().trim()
                    addTermToDatabase(term, definition, example)
                    clearInputFields(dialogView) // Clear input fields after adding the term
                }.setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }.create()

        dialog.show()
    }


    private fun addTermToDatabase(term : String, definition : String?, example : String?) {
        if (term.isEmpty() && definition.isNullOrBlank()) {
            // Inform the user that the term is required
            Toast.makeText(
                this, "Term and definition are required.", Toast.LENGTH_SHORT
            ).show()
            return
        }

        val dbHelper = DictionaryDatabaseHelper(this)
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put("term", term)
            definition?.let { put("definition", it) }
            example?.let { put("example", it) }
        }

        val newTerm = db.insert("dictionary_terms", null, values)

        if (newTerm != -1L) {
            // Term added successfully
            Toast.makeText(this, "Term added to the database.", Toast.LENGTH_SHORT)
                .show()
        } else {
            // Failed to add term
            Toast.makeText(
                this, "Failed to add term to the database.", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun clearInputFields(dialogView : View) {
        // Clear input fields after adding the term
        dialogView.findViewById<EditText>(R.id.termEditText).text = null
        dialogView.findViewById<EditText>(R.id.definitionEditText).text = null
        dialogView.findViewById<EditText>(R.id.exampleEditText).text = null
    }

    override fun onCreateOptionsMenu(menu : Menu?) : Boolean {
        menuInflater.inflate(R.menu.menu_dictionary, menu)
        return super.onCreateOptionsMenu(menu)
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

            R.id.action_bookmark -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.dictionary_container, BookmarkFragment()).commit()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun getSuggestionsCursor(query : String?) : Cursor? {
        val db = DictionaryDatabaseHelper(this).readableDatabase
        val selection = "term LIKE ?"
        val selectionArgs = arrayOf("$query%")
        val columns = arrayOf("_id", "term", "definition", "example")

        return db.query(
            "dictionary_terms", columns, selection, selectionArgs, null, null, "term ASC"
        )
    }

    private fun handleRecentSearch(query : String, definition : String, example : String) {
        val sharedPreferences = getSharedPreferences("SearchHistory", Context.MODE_PRIVATE)

        // Retrieve existing search history
        val searchHistory =
            sharedPreferences.getStringSet("searchHistory", HashSet())?.toMutableList()
                ?: mutableListOf()

        // Add the new search
        val newSearch = RecentSearch(query, definition, example)
        viewModel.addRecentSearch(newSearch)
        searchHistory.add(0, newSearch.toString()) // Convert to string for storage

        // Check if the query already exists in the recent search history
        val existingSearch = searchHistory.find { it.contains(query, ignoreCase = true) }
        if (existingSearch != null) {
            // Remove the existing search to avoid duplicates
            searchHistory.remove(existingSearch)
        }

        // Limit the search history to 10 items
        while (searchHistory.size > 10) {
            searchHistory.removeAt(searchHistory.size - 1)
        }

        // Save the updated search history
        sharedPreferences.edit().putStringSet("searchHistory", searchHistory.toSet()).apply()
    }


    private fun performSearch(query : String) {
        val cursor = getSuggestionsCursor(query)

        if (cursor != null && cursor.moveToFirst()) {
            // Match found, display the definition
            val term = cursor.getString(cursor.getColumnIndexOrThrow("term"))
            val definition = cursor.getString(cursor.getColumnIndexOrThrow("definition"))
            val example = cursor.getString(cursor.getColumnIndexOrThrow("example"))

            termTextView.text = term
            termDefinitionTextView.text = definition
            termExamplesListView.text = example
            searchView.clearFocus()
            searchView.setQuery(term, false)
            layoutDictionary.visibility = View.VISIBLE
            recentSearch.visibility = View.GONE

            // Adding the recent search
            handleRecentSearch(term, definition, example)
        } else {
            // No match found, show a message
            Toast.makeText(this, "No match found", Toast.LENGTH_SHORT).show()
            searchView.setQuery("", false)
        }
    }

    override fun onResume() {
        super.onResume()
        toolbar.title = getString(R.string.dictionary)
        navView.setCheckedItem(R.id.nav_item_dictionary)
    }

    override fun onInit(status : Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Set language for Text-to-Speech
            val result = textToSpeech.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(TAG, "Language not supported")
            } else {
                Log.i(TAG, "Text-to-Speech engine initialized")
            }
        } else {
            Log.e(TAG, "Initialization failed")
        }
    }

    private fun speakText(text : String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onDestroy() {
        // Release Text-to-Speech engine
        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onDestroy()
    }

}
