package com.ijs.abminder.dictionary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ijs.abminder.R
import com.ijs.abminder.dictionary.data.BookmarkedTerm

class BookmarksAdapter(private val bookmarkedTerms : List<BookmarkedTerm>) :
    RecyclerView.Adapter<BookmarksAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val termTextView : TextView = itemView.findViewById(R.id.termTextView)
        private val definitionTextView : TextView = itemView.findViewById(R.id.definitionTextView)
        private val exampleTextView : TextView = itemView.findViewById(R.id.exampleTextView)

        fun bind(bookmarkedTerm : BookmarkedTerm) {
            termTextView.text = bookmarkedTerm.term
            definitionTextView.text = bookmarkedTerm.definition ?: ""
            exampleTextView.text = bookmarkedTerm.example ?: ""
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bookmark, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        holder.bind(bookmarkedTerms[position])
    }

    override fun getItemCount() : Int {
        return bookmarkedTerms.size
    }
}
