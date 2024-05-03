package com.ijs.abminder.dictionary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ijs.abminder.R

class BookmarksAdapter(
    private val bookmarkedTerms: List<BookmarkData>,
    private val onItemClick: (String, String, String) -> Unit,
) : RecyclerView.Adapter<BookmarksAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val termTextView: TextView = itemView.findViewById(R.id.termTV)
        private val definitionTextView: TextView = itemView.findViewById(R.id.definitionTV)
        private val exampleTextView: TextView = itemView.findViewById(R.id.exampleTV)

        fun bind(bookmarkedTerm: BookmarkData) {
            termTextView.text = bookmarkedTerm.term
            definitionTextView.text = bookmarkedTerm.definition
            exampleTextView.text = bookmarkedTerm.example

            // Handle item click
            itemView.setOnClickListener {
                onItemClick(
                    bookmarkedTerm.term,
                    bookmarkedTerm.definition,
                    bookmarkedTerm.example
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bookmark, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookmarkedTerms[position])
    }

    override fun getItemCount(): Int {
        return bookmarkedTerms.size
    }
}

data class BookmarkData(
    val term : String,
    val definition : String,
    val example : String
)
