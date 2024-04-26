package com.ijs.abminder.dictionary.ui

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ijs.abminder.R
import com.ijs.abminder.dictionary.adapter.TermAdapter
import com.ijs.abminder.dictionary.database.DictionaryDatabaseHelper

class EditTermFragment : Fragment(), TermAdapter.OnItemClickListener {

    private lateinit var termEditText : EditText
    private lateinit var definitionEditText : EditText
    private lateinit var exampleEditText : EditText
    private lateinit var saveButton : Button
    private lateinit var cancelButton : Button
    private lateinit var termsRecyclerView : RecyclerView
    private lateinit var termListAdapter : TermAdapter

    private var isEditing = false
    private var termId : Long = -1

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val rootView = inflater.inflate(R.layout.edit_term_fragment, container, false)
        termEditText = rootView.findViewById(R.id.termEditText)
        definitionEditText = rootView.findViewById(R.id.definitionEditText)
        exampleEditText = rootView.findViewById(R.id.exampleEditText)
        saveButton = rootView.findViewById(R.id.saveButton)
        cancelButton = rootView.findViewById(R.id.cancelButton)
        termsRecyclerView = rootView.findViewById(R.id.termsRecyclerView)

        saveButton.setOnClickListener {
            saveTerm()
        }

        cancelButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        // Check if it's an edit or add operation
        val args = arguments
        if (args != null && args.containsKey(ARG_EDIT_TERM_ID)) {
            isEditing = true
            termId = args.getLong(ARG_EDIT_TERM_ID)
            val term = args.getString(ARG_EDIT_TERM) ?: ""
            val definition = args.getString(ARG_EDIT_DEFINITION) ?: ""
            val example = args.getString(ARG_EDIT_EXAMPLE) ?: ""

            termEditText.setText(term)
            definitionEditText.setText(definition)
            exampleEditText.setText(example)
        }

        setupRecyclerView()

        return rootView
    }

    private fun setupRecyclerView() {
        val dbHelper = DictionaryDatabaseHelper(requireContext())
        val db : SQLiteDatabase = dbHelper.readableDatabase
        val cursor : Cursor = db.rawQuery("SELECT * FROM dictionary_terms", null)

        termListAdapter = TermAdapter(cursor, this)
        termsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = termListAdapter
        }
    }

    override fun onItemClick(term : String, definition : String, example : String) {
        termEditText.setText(term)
        definitionEditText.setText(definition)
        exampleEditText.setText(example)
    }

    private fun saveTerm() {
        val term = termEditText.text.toString().trim()
        val definition = definitionEditText.text.toString().trim()
        val example = exampleEditText.text.toString().trim()

        if (term.isBlank()) {
            Toast.makeText(requireContext(), "Term cannot be empty.", Toast.LENGTH_SHORT).show()
            return
        }

        val dbHelper = DictionaryDatabaseHelper(requireContext())
        val db : SQLiteDatabase = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put("term", term)
            put("definition", definition)
            put("example", example)
        }

        if (isEditing) {
            // Update existing term
            val selection = "_id=?"
            val selectionArgs = arrayOf(termId.toString())
            val count = db.update("dictionary_terms", values, selection, selectionArgs)
            if (count > 0) {
                Toast.makeText(requireContext(), "Term updated.", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "Failed to update term.", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            // Add new term
            val newRowId = db.insert("dictionary_terms", null, values)
            if (newRowId != -1L) {
                Toast.makeText(requireContext(), "Term added.", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "Failed to add term.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // Update the cursor in the adapter
        termListAdapter.swapCursor(db.rawQuery("SELECT * FROM dictionary_terms", null))
    }


    companion object {
        const val ARG_EDIT_TERM_ID = "edit_term_id"
        const val ARG_EDIT_TERM = "edit_term"
        const val ARG_EDIT_DEFINITION = "edit_definition"
        const val ARG_EDIT_EXAMPLE = "edit_example"
    }
}
