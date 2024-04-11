package com.mtcdb.stem.mathtrix.learn

import android.annotation.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.mtcdb.stem.mathtrix.R
import com.mtcdb.stem.mathtrix.learn.chapters.*
import com.mtcdb.stem.mathtrix.learn.subjects.*

class ContainerFragment : Fragment() {

    private val subjectsList = listOf(
        "Business Mathematics",
        "Business Finance",
        "Business Ethics",
        "Applied Economics",
        "Fundamentals of ABM I",
        "Fundamentals of ABM II",
        "Principles of Marketing",
        "Organization and Management"
    )
    private lateinit var subjectsAdapter : SubjectsAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_container, container, false)

        // Initialize RecyclerView
        val recyclerView : RecyclerView = view.findViewById(R.id.recyclerViewSubjects)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        subjectsAdapter = SubjectsAdapter(subjectsList, ::onSubjectSelected)
        recyclerView.adapter = subjectsAdapter

        return view
    }

    private fun onSubjectSelected(subject : String) {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame, ChaptersFragment.newInstance(subject))
        transaction.addToBackStack(null)
        transaction.commit()
    }
}