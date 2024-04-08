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
            Welcome to the ABMinder Quiz! This quiz covers a wide range of topics in Business Mathematics, FABM 1, Applied Economics, and Business Finance. Test your knowledge across these subjects and unlock new difficulty levels as you progress. With three levels of difficulty—easy, medium, and hard—this quiz offers a challenging yet rewarding experience for students and professionals alike. Can you conquer all the levels and become a master of ABM concepts?
        """.trimIndent()
        description.text = desc

        val textView2 = rootView.findViewById<TextView>(R.id.textView2)
        val text = """
            Choose your subject and difficulty level before starting the quiz.
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