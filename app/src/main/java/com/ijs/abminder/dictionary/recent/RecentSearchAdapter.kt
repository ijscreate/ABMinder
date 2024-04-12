package com.ijs.abminder.dictionary.recent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ijs.abminder.R

class RecentSearchAdapter(
    val context : Context,
    private val onItemClick : (RecentSearch) -> Unit,
    private val onItemLongClick : (Int) -> Unit,
) : ListAdapter<RecentSearch, RecentSearchAdapter.RecentSearchViewHolder>(RecentSearchDiffCallback()) {

    private val inflater : LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : RecentSearchViewHolder {
        val itemView = inflater.inflate(R.layout.list_item_recent_search, parent, false)
        return RecentSearchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder : RecentSearchViewHolder, position : Int) {
        val currentSearch = getItem(position)
        holder.bind(currentSearch)
    }

    inner class RecentSearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val termTextView : TextView = itemView.findViewById(R.id.recentSearchTerm)
        private val definitionTextView : TextView =
            itemView.findViewById(R.id.recentSearchDefinition)
        private val exampleTextView : TextView = itemView.findViewById(R.id.recentSearchExample)

        fun bind(search : RecentSearch) {
            termTextView.text = search.query
            definitionTextView.text = search.definition
            exampleTextView.text = context.getString(R.string.exa, search.example)

            itemView.setOnClickListener {
                onItemClick(search)
            }

            itemView.setOnLongClickListener {
                onItemLongClick(adapterPosition)
                true
            }
        }
    }

    private class RecentSearchDiffCallback : DiffUtil.ItemCallback<RecentSearch>() {
        override fun areItemsTheSame(oldItem : RecentSearch, newItem : RecentSearch) : Boolean {
            return oldItem.query == newItem.query
        }

        override fun areContentsTheSame(oldItem : RecentSearch, newItem : RecentSearch) : Boolean {
            return oldItem == newItem
        }
    }
}
