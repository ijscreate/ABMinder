package com.ijs.abminder.quiz

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import com.ijs.abminder.R
import com.skydoves.powerspinner.PowerSpinnerView

class Difficulty : Fragment() {

    private lateinit var easyButton : AppCompatButton
    private lateinit var mediumButton : AppCompatButton
    private lateinit var hardButton : AppCompatButton
    private lateinit var quizFragment : QuizFragment
    private lateinit var subjectSpinner : PowerSpinnerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val rootView = layoutInflater.inflate(R.layout.difficulty, container, false)

        // Initialize UI elements
        easyButton = rootView.findViewById(R.id.easyButton)
        mediumButton = rootView.findViewById(R.id.mediumButton)
        hardButton = rootView.findViewById(R.id.hardButton)
        subjectSpinner = rootView.findViewById(R.id.spinner)

        val description = rootView.findViewById<TextView>(R.id.description)
        val desc = """
            Welcome to the ABMinder Quiz! This quiz covers a wide range of topics in Business Mathematics, FABM 1, Applied Economics, and Business Finance.
        """.trimIndent()
        description.text = desc

        val textView2 = rootView.findViewById<TextView>(R.id.textView2)
        val text = """
            Read each question carefully and select the option that you believe best answers the question.
            You must answer at least 8 out of 10 questions correctly to unlock the medium difficulty level. To unlock the hard difficulty level, you need to score at least 7 out of 10 questions correctly on the medium difficulty level.
            Difficulty levels are unlocked progressively, so start with the easy level and work your way up.
            Don't worry if you don't unlock higher difficulty levels on your first try! Use each attempt as an opportunity to learn and improve your understanding of ABM concepts.
            After completing the quiz, your score and feedback will be displayed, allowing you to track your progress and identify areas for improvement.
            
            Are you ready to test your ABM knowledge and unlock new challenges? Let's begin!
        """.trimIndent()
        textView2.text = text

        // Initialize subject options
        val subjects =
            listOf(
                "Business Mathematics",
                "Business Finance",
                "Business Ethics",
                "FABM 1",
                "FABM 2",
                "Applied Economics"
            )

        // adapter for subject spinner
        /*
        val subjectAdapter =
            ArrayAdapter(requireContext(), R.layout.custom_spinner, subjects)
        subjectAdapter.setDropDownViewResource(R.layout.dropdown)
         */

        // Set adapter for subject spinner
        subjectSpinner.setItems(subjects)
        subjectSpinner.hint = "Choose a subject"

        var subject = ""

        // Set up button click listeners
        subjectSpinner.setOnSpinnerItemSelectedListener<String> { _, _, _, newItem ->
           subject = newItem
        }
        easyButton.setOnClickListener {
            showCountdownDialog("Easy", subject)
        }
        mediumButton.setOnClickListener {
            showCountdownDialog("Medium", subject)
        }
        hardButton.setOnClickListener {
            showCountdownDialog("Hard", subject)
        }

        // Check the unlocking conditions
        val isMediumUnlocked = getUnlockStateForLevel("Medium")
        val isHardUnlocked = getUnlockStateForLevel("Hard")

        // Enable or disable the buttons based on the unlocking state
        mediumButton.isEnabled = isMediumUnlocked
        hardButton.isEnabled = isHardUnlocked

        TooltipCompat.setTooltipText(mediumButton, "Score 8 or higher in Easy level to unlock")
        TooltipCompat.setTooltipText(hardButton, "Score 6 or higher in Medium level to unlock")

        return rootView
    }

    private fun getUnlockStateForLevel(level : String) : Boolean {
        val sharedPreferences =
            requireContext().getSharedPreferences("UnlockState", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(level, false)
    }

    @SuppressLint("MissingInflatedId")
    private fun showCountdownDialog(d : String, s : String) {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_coundown, null)
        val countdownTextView = dialogView.findViewById<TextView>(R.id.textCountdown)
        val subject = dialogView.findViewById<TextView>(R.id.subjectName)
        val difficulty = dialogView.findViewById<TextView>(R.id.difficultyName)

        subject.text = s
        difficulty.text = d

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()
        dialog.show()

        object : CountDownTimer(4000, 1000) { // 4 seconds countdown with 1-second intervals
            override fun onTick(millisUntilFinished : Long) {
                val seconds = (millisUntilFinished / 1000).toInt()
                countdownTextView.text = seconds.toString()
            }

            override fun onFinish() {
                dialog.dismiss()
                // Start the quiz after the countdown finishes
                navigateToQuizFragment(d, s)
            }
        }.start()
    }

    @SuppressLint("InflateParams")
    private fun navigateToQuizFragment(difficultyLevel : String, subject : String) {
        quizFragment = QuizFragment()
        val bundle = Bundle()
        bundle.putString("difficultyLevel", difficultyLevel)
        bundle.putString("subject", subject)
        quizFragment.arguments = bundle

        val difficulty = layoutInflater.inflate(R.layout.difficulty, null)
        difficulty.visibility = View.GONE
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.quiz_container, quizFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}