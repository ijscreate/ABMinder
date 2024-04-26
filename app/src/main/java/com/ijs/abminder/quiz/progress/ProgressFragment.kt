package com.ijs.abminder.quiz.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ijs.abminder.R
import com.ijs.abminder.quiz.QuizFragment
import com.ijs.abminder.quiz.database.ProgressDatabaseHelper

class ProgressFragment : Fragment() {

    private lateinit var dbHelper : ProgressDatabaseHelper
    private lateinit var quizFragment : QuizFragment

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progress, container, false)
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve progress data from QuizFragment
        quizFragment = QuizFragment()
        val progressData = QuizFragment().getProgressData()

        // Store progress data in SQLite database
        storeProgressData(progressData)
    }

    private fun storeProgressData(progressData : Bundle) {
        dbHelper = ProgressDatabaseHelper(requireContext())
        val totalQuestionsAnswered = progressData.getInt("totalQuestionsAnswered")
        val totalCorrectAnswers = progressData.getInt("totalCorrectAnswers")
        val totalWrongAnswers = progressData.getInt("totalWrongAnswers")
        val totalTimeTaken = progressData.getLong("totalTimeTaken")
        val highestStreak = progressData.getInt("highestStreak")
        val timeTakenForLevel = progressData.getSerializable("timeTakenForLevel") as HashMap<*, *>

        dbHelper.insertProgressData(
            totalQuestionsAnswered,
            totalCorrectAnswers,
            totalWrongAnswers,
            totalTimeTaken,
            highestStreak,
            timeTakenForLevel
        )
    }
}
