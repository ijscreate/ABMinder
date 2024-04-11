package com.mtcdb.stem.mathtrix.dictionary

import android.annotation.*
import android.content.*
import android.database.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.appcompat.app.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.*
import androidx.drawerlayout.widget.*
import androidx.recyclerview.widget.*
import androidx.transition.*
import com.mtcdb.stem.mathtrix.*
import com.mtcdb.stem.mathtrix.R
import com.mtcdb.stem.mathtrix.dictionary.database.*
import com.mtcdb.stem.mathtrix.dictionary.recent.*

class DictionaryActivity : BaseDrawerActivity() {

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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)

        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.dicToolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

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
        recentSearchesRecyclerView.visibility = View.VISIBLE
        val recentText : TextView = findViewById(R.id.recentText)
        recentText.visibility = View.GONE

        TransitionManager.beginDelayedTransition(
            findViewById(android.R.id.content),
            AutoTransition()
        )

        viewModel = DictionaryViewModel()

        // Initialize and set up the RecentSearchAdapter
        recentSearchesAdapter = RecentSearchAdapter(this, { clickedSearch ->
            Toast.makeText(this, clickedSearch.query, Toast.LENGTH_SHORT)
                .show()
        }) {
        }

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
                    recentSearchesRecyclerView.visibility = View.VISIBLE
                }

                // Update the cursor in the adapter as the user types
                val cursor = getSuggestionsCursor(newText)
                searchView.suggestionsAdapter = DictionarySuggestionAdapter(
                    this@DictionaryActivity,
                    cursor
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
                searchView.clearFocus()
                searchView.setQuery(term, false)
                layoutDictionary.visibility = View.VISIBLE
                recentSearchesRecyclerView.visibility = View.GONE

                // Adding the recent search
                handleRecentSearch(term, definition, example)

                return true
            }
        })
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
            "dictionary_terms",
            columns,
            selection,
            selectionArgs,
            null,
            null,
            "term ASC"
        )
    }

    private fun handleRecentSearch(query : String, definition : String, example : String) {
        val sharedPreferences =
            getSharedPreferences("SearchHistory", Context.MODE_PRIVATE)

        // Retrieve existing search history
        val searchHistory =
            sharedPreferences.getStringSet("searchHistory", HashSet())?.toMutableList()
                ?: mutableListOf()

        // Add the new search
        val newSearch = RecentSearch(query, definition, example)
        viewModel.addRecentSearch(newSearch)
        searchHistory.add(0, newSearch.toString()) // Convert to string for storage

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
            recentSearchesRecyclerView.visibility = View.GONE

            // Adding the recent search
            handleRecentSearch(term, definition, example)
        } else {
            // No match found, show a message
            Toast.makeText(this, "No match found", Toast.LENGTH_SHORT).show()
            searchView.setQuery("", false)
        }
    }
}
