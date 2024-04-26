package com.ijs.abminder.dictionary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ijs.abminder.R
import com.ijs.abminder.dictionary.adapter.BookmarksAdapter
import com.ijs.abminder.dictionary.data.BookmarkedTerm
import com.ijs.abminder.dictionary.database.DictionaryDatabaseHelper

class BookmarkFragment : Fragment() {

    private lateinit var bookmarksRecyclerView : RecyclerView
    private lateinit var bookmarksAdapter : BookmarksAdapter
    private lateinit var dictionaryActivity : DictionaryActivity
    private lateinit var currentTerm : String
    private lateinit var definition : String
    private lateinit var example : String

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val rootView = inflater.inflate(R.layout.fragment_bookmark, container, false)
        bookmarksRecyclerView = rootView.findViewById(R.id.bookmarksRecyclerView)
        dictionaryActivity = activity as DictionaryActivity
        return rootView
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentTerm = dictionaryActivity.bookmarkedTerm

        // Query the database to get the definition and example for the searched term
        val dbHelper = DictionaryDatabaseHelper(requireContext())
        val db = dbHelper.readableDatabase

        val cursor = db.query(
            "dictionary_terms",
            arrayOf("definition", "example"),
            "term = ?",
            arrayOf(currentTerm),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            definition = cursor.getString(cursor.getColumnIndexOrThrow("definition"))
            example = cursor.getString(cursor.getColumnIndexOrThrow("example"))

            bookmarksAdapter = BookmarksAdapter(bookmarkedTerms(currentTerm, definition, example))
            bookmarksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            bookmarksRecyclerView.adapter = bookmarksAdapter
        }
        cursor.close()
    }

    private fun bookmarkedTerms(
        term : String,
        definition : String,
        example : String?,
    ) : List<BookmarkedTerm> {
        // Replace this with your actual bookmarked terms from the database
        return listOf(
            BookmarkedTerm(term, definition, example),
        )
    }

    override fun onDestroy() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        super.onDestroy()
    }

}
