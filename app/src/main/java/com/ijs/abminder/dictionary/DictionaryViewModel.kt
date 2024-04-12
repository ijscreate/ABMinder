package com.ijs.abminder.dictionary

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ijs.abminder.MyApplication
import com.ijs.abminder.dictionary.recent.RecentSearch

class DictionaryViewModel : ViewModel() {

    private val _recentSearches = MutableLiveData<List<RecentSearch>>()
    val recentSearches : LiveData<List<RecentSearch>> get() = _recentSearches

    init {
        // Initialize with data from SharedPreferences
        loadDataFromSharedPreferences()
    }

    fun addRecentSearch(search : RecentSearch) {
        val currentList = _recentSearches.value ?: emptyList()
        val updatedList = mutableListOf(search) + currentList.take(9)
        _recentSearches.value = updatedList
        saveDataToSharedPreferences(updatedList)
    }

    private fun saveDataToSharedPreferences(searches : List<RecentSearch>) {
        // Convert searches to a format that can be stored in SharedPreferences
        val searchesAsString = searches.map { it.toString() }

        // Save to SharedPreferences
        val sharedPreferences =
            MyApplication.instance.getSharedPreferences("recent", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putStringSet("searchHistory", searchesAsString.toSet())
        editor.apply()
    }

    private fun loadDataFromSharedPreferences() {
        // Load data from SharedPreferences and update the LiveData
        val sharedPreferences =
            MyApplication.instance.getSharedPreferences("recent", Context.MODE_PRIVATE)
        val searchesAsString = sharedPreferences.getStringSet("searchHistory", setOf()) ?: setOf()

        val searches = searchesAsString.map { RecentSearch.fromString(it) }
        _recentSearches.value = searches
    }
}
