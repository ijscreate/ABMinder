package com.mtcdb.stem.mathtrix.learn.lessons

import android.annotation.*
import android.view.*
import android.widget.*
import androidx.appcompat.app.*
import androidx.recyclerview.widget.*
import com.mtcdb.stem.mathtrix.*

class LessonsAdapter(
    private val lessonsList : List<Lesson>,
    private val onLessonSelected : (Lesson) -> Unit,
) : RecyclerView.Adapter<LessonsAdapter.LessonViewHolder>() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : LessonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lesson, parent, false)

        val arrow = view.findViewById<ImageView>(R.id.image_view_arrow)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            arrow.setColorFilter(R.color.black)
        } else {
            arrow.setColorFilter(R.color.white)
        }
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder : LessonViewHolder, position : Int) {
        val lesson = lessonsList[position]
        holder.bind(lesson)
        holder.itemView.setOnClickListener { onLessonSelected(lesson) }
    }

    override fun getItemCount() : Int = lessonsList.size

    class LessonViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val lessonTextView : TextView = itemView.findViewById(R.id.lessonTextView)

        fun bind(lesson : Lesson) {
            lessonTextView.text = lesson.name
        }
    }
}
