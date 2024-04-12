package com.ijs.abminder.learn.chapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.RecyclerView
import com.ijs.abminder.R

class ChaptersAdapter(
    private val chaptersList : List<String>,
    private val onChapterSelected : (String) -> Unit,
) : RecyclerView.Adapter<ChaptersAdapter.ChapterViewHolder>() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ChapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chapter, parent, false)
        val arrow = view.findViewById<ImageView>(R.id.image_view_arrow)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            arrow.setColorFilter(R.color.black)
        } else {
            arrow.setColorFilter(R.color.white)
        }
        return ChapterViewHolder(view)
    }

    override fun onBindViewHolder(holder : ChapterViewHolder, position : Int) {
        val chapter = chaptersList[position]
        holder.bind(chapter)
        holder.itemView.setOnClickListener { onChapterSelected(chapter) }
    }

    override fun getItemCount() : Int = chaptersList.size

    class ChapterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val chapterTextView : TextView = itemView.findViewById(R.id.chapterTextView)

        fun bind(chapter : String) {
            chapterTextView.text = chapter
        }
    }
}
