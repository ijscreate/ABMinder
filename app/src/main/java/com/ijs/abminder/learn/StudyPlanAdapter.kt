package com.ijs.abminder.learn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ijs.abminder.R

data class StudyPlan(
    val title : String,
    val startTime : String,
    val endTime : String,
)

class StudyPlanAdapter(
    private val studyPlans : MutableList<StudyPlan>,
    private val onDeleteStudyPlan : (StudyPlan) -> Unit,
) : ListAdapter<StudyPlan, StudyPlanAdapter.StudyPlanViewHolder>(StudyPlanDiffCallback()) {

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : StudyPlanViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_study_plan, parent, false)
        return StudyPlanViewHolder(itemView)
    }

    override fun onBindViewHolder(holder : StudyPlanViewHolder, position : Int) {
        val studyPlan = studyPlans[position]
        holder.bind(studyPlan)
        holder.deleteButton.setOnClickListener {
            onDeleteStudyPlan(studyPlan)
        }
    }

    override fun getItemCount() : Int = studyPlans.size

    class StudyPlanViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView : TextView = itemView.findViewById(R.id.study_plan_title)
        private val dateRangeTextView : TextView = itemView.findViewById(R.id.study_plan_date_range)
        val deleteButton : ImageButton = itemView.findViewById(R.id.delete_study_plan_button)

        fun bind(studyPlan : StudyPlan) {
            titleTextView.text = studyPlan.title
            dateRangeTextView.text = buildString {
                append(studyPlan.title)
                append(" ")
                append(studyPlan.startTime)
                append(" - ")
                append(studyPlan.endTime)
            }
        }
    }
}

class StudyPlanDiffCallback : DiffUtil.ItemCallback<StudyPlan>() {
    override fun areItemsTheSame(oldItem : StudyPlan, newItem : StudyPlan) : Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem : StudyPlan, newItem : StudyPlan) : Boolean {
        return oldItem == newItem
    }
}