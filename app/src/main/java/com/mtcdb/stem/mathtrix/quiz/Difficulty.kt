package com.mtcdb.stem.mathtrix.quiz

import android.annotation.*
import android.content.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.appcompat.app.*
import androidx.appcompat.widget.*
import androidx.fragment.app.*
import com.mtcdb.stem.mathtrix.*

class Difficulty : Fragment() {

    private lateinit var easyButton : AppCompatButton
    private lateinit var mediumButton : AppCompatButton
    private lateinit var hardButton : AppCompatButton
    private lateinit var quizFragment : QuizFragment
    private lateinit var subjectSpinner : AppCompatSpinner

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

        // Initialize subject options
        val subjects =
            arrayOf("Business Mathematics", "Business Finance", "FABM 1", "Applied Economics")

        // adapter for subject spinner
        val subjectAdapter =
            ArrayAdapter(requireContext(), R.layout.custom_spinner, subjects)
        subjectAdapter.setDropDownViewResource(R.layout.dropdown)

        // Set adapter for subject spinner
        subjectSpinner.adapter = subjectAdapter
        subjectSpinner.tag = "subject"
        subjectSpinner.prompt = "Select subject"
        subjectSpinner.dropDownVerticalOffset = 100

        // Set up button click listeners
        easyButton.setOnClickListener {
            val selectedSubject = subjectSpinner.selectedItem.toString()
            showCountdownDialog("Easy", selectedSubject)
        }

        mediumButton.setOnClickListener {
            val selectedSubject = subjectSpinner.selectedItem.toString()
            showCountdownDialog("Medium", selectedSubject)
        }

        hardButton.setOnClickListener {
            val selectedSubject = subjectSpinner.selectedItem.toString()
            showCountdownDialog("Hard", selectedSubject)
        }

        // Check the unlocking conditions
        val isMediumUnlocked = getUnlockStateForLevel("Medium")
        val isHardUnlocked = getUnlockStateForLevel("Hard")

        // Enable or disable the buttons based on the unlocking state
        mediumButton.isEnabled = isMediumUnlocked
        hardButton.isEnabled = isHardUnlocked


        /**
        // Set the Tooltip text for Medium and Hard levels
        if (!isMediumUnlocked) {
        showTooltip(
        mediumButton,
        "Score 8 or higher in Easy level to unlock",
        )
        }

        if (!isHardUnlocked) {
        showTooltip(
        hardButton,
        "Score 6 or higher in Medium level to unlock",
        )
        } */

        TooltipCompat.setTooltipText(mediumButton, "Score 8 or higher in Easy level to unlock")
        TooltipCompat.setTooltipText(hardButton, "Score 6 or higher in Medium level to unlock")

        return rootView
    }

    private fun getUnlockStateForLevel(level : String) : Boolean {
        val sharedPreferences =
            requireContext().getSharedPreferences("UnlockState", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(level, false)
    }

    private fun showCountdownDialog(d : String, s : String) {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_coundown, null)
        val countdownTextView = dialogView.findViewById<TextView>(R.id.textCountdown)

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