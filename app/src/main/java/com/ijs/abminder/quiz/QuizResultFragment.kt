package com.ijs.abminder.quiz

import android.annotation.*
import android.content.*
import android.graphics.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.core.content.*
import androidx.fragment.app.*
import antonkozyriatskyi.circularprogressindicator.*

@Suppress("DEPRECATION")
class QuizResultFragment : Fragment() {

    private lateinit var finishButton : Button
    private lateinit var difficulty : String
    private lateinit var replay : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_quiz_result, container, false)

        // Retrieve the quiz questions and score from arguments
        val questions =
            arguments?.getParcelableArrayList<QuizEntity>("quizQuestions")
        val score = arguments?.getInt("quizScore") ?: 0
        difficulty = arguments?.getString("difficulty").toString()

        val progressBar : CircularProgressIndicator = view.findViewById(R.id.progressBar2)
        val difficultyTextView : TextView = view.findViewById(R.id.difficulty)

        difficultyTextView.text = difficulty
        questions?.size?.let { progressBar.setProgress(score.toDouble(), it.toDouble()) }
        progressBar.progressColor = Color.GREEN
        progressBar.progressBackgroundColor = Color.RED
        progressBar.setProgressStrokeWidthDp(10)
        progressBar.setProgressBackgroundStrokeWidthDp(10)
        progressBar.setShouldDrawDot(false)
        progressBar.setTextSizeSp(32)

        val belowThresholdColor = ContextCompat.getColor(requireActivity(), R.color.Red)
        val aboveThresholdColor = ContextCompat.getColor(requireActivity(), R.color.green)
        val thresholdScore = 7

        if (score >= thresholdScore) {
            progressBar.progressColor = aboveThresholdColor
            progressBar.progressBackgroundColor =
                ContextCompat.getColor(requireActivity(), R.color.lightGreen)
            progressBar.textColor = aboveThresholdColor
        } else {
            progressBar.progressColor = belowThresholdColor
            progressBar.textColor = belowThresholdColor
            progressBar.progressBackgroundColor =
                ContextCompat.getColor(requireActivity(), R.color.lightRed)
        }

        val timeTakenTextView : TextView = view.findViewById(R.id.timeTakenTextView)

        val difficulty = arguments?.getString("difficulty")
        val subject = arguments?.getString("subject")
        val timeTaken = arguments?.getString("timeTaken")
        timeTakenTextView.text = timeTaken

        replay = view.findViewById(R.id.replay)
        replay.setOnClickListener {

            val quizFragment = QuizFragment()
            val bundle = Bundle()
            bundle.putString("difficultyLevel", difficulty)
            bundle.putString("subject", subject)
            quizFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.quiz_container, quizFragment).commit()

            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        }

        finishButton = view.findViewById(R.id.finishButton)
        finishButton.setOnClickListener {
            val intent = Intent(requireActivity(), DifficultyLevel::class.java)
            startActivity(intent)
        }

        val resultMessage = when {
            score == questions?.size -> getString(R.string.quiz_result_perfect_score)
            score >= 7 -> getString(R.string.quiz_result_pass)
            score == 1 -> "At least you got 1! 💪"
            else -> getString(R.string.quiz_result_fail)
        }

        // Display the result message in a TextView
        val resultTextView : TextView = view.findViewById(R.id.resultTextView)
        resultTextView.text = resultMessage

        val subjectTV : TextView = view.findViewById(R.id.subject)
        subjectTV.text = subject
        return view
    }
}
