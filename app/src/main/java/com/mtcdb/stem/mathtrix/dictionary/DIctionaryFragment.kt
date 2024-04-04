package com.mtcdb.stem.mathtrix.dictionary

import android.annotation.*
import android.content.*
import android.database.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.*
import androidx.lifecycle.*
import androidx.recyclerview.widget.*
import androidx.transition.*
import com.mtcdb.stem.mathtrix.R
import com.mtcdb.stem.mathtrix.dictionary.database.*
import com.mtcdb.stem.mathtrix.dictionary.recent.*

class DictionaryFragment : Fragment() {

    private lateinit var searchView : SearchView
    private lateinit var termTextView : TextView
    private lateinit var termDefinitionTextView : TextView
    private lateinit var termExamplesListView : TextView
    private lateinit var layoutDictionary : LinearLayout
    private lateinit var recentSearchesRecyclerView : RecyclerView
    private lateinit var recentSearchesAdapter : RecentSearchAdapter
    private lateinit var viewModel : DictionaryViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val rootView = inflater.inflate(R.layout.fragment_dictionary, container, false)

        searchView = rootView.findViewById(R.id.searchView)
        searchView.queryHint = "Search terms..."
        layoutDictionary = rootView.findViewById(R.id.dictionary_layout)
        layoutDictionary.visibility = View.GONE

        termTextView = rootView.findViewById(R.id.termTextView)
        termDefinitionTextView = rootView.findViewById(R.id.term_definition)
        termExamplesListView = rootView.findViewById(R.id.term_examples)
        recentSearchesRecyclerView = rootView.findViewById(R.id.recent_searches_list)
        recentSearchesRecyclerView.visibility = View.VISIBLE
        val recentText : TextView = rootView.findViewById(R.id.recentText)
        recentText.visibility = View.GONE

        TransitionManager.beginDelayedTransition(container!!, AutoTransition())

        viewModel = ViewModelProvider(this)[DictionaryViewModel::class.java]

        // Initialize and set up the RecentSearchAdapter
        recentSearchesAdapter = RecentSearchAdapter(requireContext(), { clickedSearch ->
            Toast.makeText(requireContext(), clickedSearch.query, Toast.LENGTH_SHORT)
                .show()
        }) {
        }

        recentSearchesRecyclerView.adapter = recentSearchesAdapter

        // Observe changes in recent searches
        viewModel.recentSearches.observe(viewLifecycleOwner) { searches ->
            recentSearchesAdapter.submitList(searches)
        }

        /**
        // Observe changes in suggestions
        if (recentSearchesAdapter.itemCount == 0) {
        recentText.visibility = View.VISIBLE
        recentSearchesRecyclerView.visibility = View.GONE
        recent.visibility = View.GONE
        } else {
        recentText.visibility = View.GONE
        recentSearchesRecyclerView.visibility = View.VISIBLE
        recent.visibility = View.VISIBLE
        } */

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
                searchView.suggestionsAdapter = context?.let {
                    DictionarySuggestionAdapter(
                        it,
                        cursor
                    )
                }
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

        return rootView
    }

    private fun getSuggestionsCursor(query : String?) : Cursor? {
        val db = context?.let { DictionaryDatabaseHelper(it).readableDatabase }
        val selection = "term LIKE ?"
        val selectionArgs = arrayOf("$query%")
        val columns = arrayOf("_id", "term", "definition", "example")

        return db?.query(
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
            requireContext().getSharedPreferences("SearchHistory", Context.MODE_PRIVATE)

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
            Toast.makeText(requireContext(), "No match found", Toast.LENGTH_SHORT).show()
            searchView.setQuery("", false)
        }
    }

}
