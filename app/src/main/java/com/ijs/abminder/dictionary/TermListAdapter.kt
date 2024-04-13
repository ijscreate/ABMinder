package com.ijs.abminder.dictionary

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ijs.abminder.R

class TermAdapter(
    private var cursor : Cursor?,
    private val itemClickListener : OnItemClickListener,
) :
    RecyclerView.Adapter<TermAdapter.TermViewHolder>() {

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : TermViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.lis_item_term, parent, false)
        return TermViewHolder(itemView)
    }

    override fun onBindViewHolder(holder : TermViewHolder, position : Int) {
        cursor?.moveToPosition(position)
        val term = cursor?.getString(cursor!!.getColumnIndexOrThrow("term")) ?: ""
        val definition = cursor?.getString(cursor!!.getColumnIndexOrThrow("definition")) ?: ""
        val example = cursor?.getString(cursor!!.getColumnIndexOrThrow("example")) ?: ""
        holder.bind(term, definition, example)
    }

    override fun getItemCount() : Int {
        return cursor?.count ?: 0
    }

    inner class TermViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val termTextView : TextView = itemView.findViewById(R.id.termTextView)
        private val definitionTextView : TextView = itemView.findViewById(R.id.definitionTextView)
        private val exampleTextView : TextView = itemView.findViewById(R.id.exampleTextView)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(term : String, definition : String, example : String) {
            termTextView.text = term
            definitionTextView.text = definition
            exampleTextView.text = example
        }

        override fun onClick(v : View?) {
            val position = adapterPosition
            cursor?.moveToPosition(position)
            val term = cursor?.getString(cursor!!.getColumnIndexOrThrow("term")) ?: ""
            val definition = cursor?.getString(cursor!!.getColumnIndexOrThrow("definition")) ?: ""
            val example = cursor?.getString(cursor!!.getColumnIndexOrThrow("example")) ?: ""
            itemClickListener.onItemClick(term, definition, example)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun swapCursor(newCursor : Cursor?) {
        cursor?.close()
        cursor = newCursor
        this.notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(term : String, definition : String, example : String)
    }
}
