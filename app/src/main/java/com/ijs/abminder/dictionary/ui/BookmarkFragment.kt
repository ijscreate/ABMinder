package com.ijs.abminder.dictionary.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ijs.abminder.R
import com.ijs.abminder.dictionary.adapter.BookmarkData
import com.ijs.abminder.dictionary.adapter.BookmarksAdapter
import com.ijs.abminder.dictionary.database.DictionaryDatabaseHelper

class BookmarkFragment : Fragment() {

    private lateinit var bookmarksRecyclerView: RecyclerView
    private lateinit var bookmarksAdapter: BookmarksAdapter

    companion object {
        private const val ARG_BOOKMARKED_TERM = "bookmarked_term"

        fun newInstance(term: String, context: Context): Fragment {
            val fragment = BookmarkFragment()
            val args = Bundle().apply {
                putString(ARG_BOOKMARKED_TERM, term)
            }
            fragment.arguments = args

            // Save the bookmarked term using SharedPreferences
            val sharedPreferences = context.getSharedPreferences("Bookmarks", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(term, "")
            editor.apply()
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_bookmark, container, false)
        bookmarksRecyclerView = rootView.findViewById(R.id.bookmarksRecyclerView)

        // Retrieve bookmarked terms from SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("Bookmarks", Context.MODE_PRIVATE)
        val bookmarkedTerms = mutableListOf<BookmarkData>()

        val db = DictionaryDatabaseHelper(requireContext()).readableDatabase

        sharedPreferences.all.keys.forEach { term ->
            val cursor = db.query(
                "dictionary_terms",
                arrayOf("definition", "example"),
                "term = ?",
                arrayOf(term),
                null,
                null,
                null
            )
            if (cursor.moveToFirst()) {
                val definition = cursor.getString(cursor.getColumnIndexOrThrow("definition"))
                val example = cursor.getString(cursor.getColumnIndexOrThrow("example"))
                bookmarkedTerms.add(BookmarkData(term, definition, example))
            }
            cursor.close()
        }


        // Set up RecyclerView with retrieved bookmarked terms
        bookmarksAdapter = BookmarksAdapter(bookmarkedTerms, ::onItemClick)
        bookmarksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookmarksRecyclerView.adapter = bookmarksAdapter

        return rootView
    }

    private fun onItemClick(term: String, definition: String, example: String) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.dictionary_container, ViewBookmarkFragment.newInstance(term, definition, example))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        super.onDestroy()
    }
}