package com.mtcdb.stem.mathtrix.quiz

import android.annotation.*
import android.content.*
import android.database.sqlite.*
import android.graphics.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import androidx.transition.*
import com.akexorcist.roundcornerprogressbar.*
import com.google.android.material.radiobutton.*
import com.mtcdb.stem.mathtrix.R
import com.mtcdb.stem.mathtrix.quiz.database.*
import java.util.*
import kotlin.collections.set

class QuizFragment : Fragment() {

    private lateinit var questionTextView : TextView
    private lateinit var optionsRadioGroup : RadioGroup
    private lateinit var dbHelper : QuizDatabaseHelper
    private lateinit var tVQuestions : TextView
    private lateinit var database : SQLiteDatabase
    private var questions : MutableList<QuizEntity> = mutableListOf()
    private var currentQuestionIndex = 0
    private lateinit var timerTextView : TextView
    private lateinit var timerProgressBar : RoundCornerProgressBar
    private var selectedOptionIndex = -1
    private var countDownTimer : CountDownTimer? = null
    private var timeLeftMillis : Long = 180000 // 3 minutes in milliseconds
    private var totalTimeMillis : Long = 180000 // 3 minutes in milliseconds
    private var totalQuestionsPerGame = 10
    private var quizStartTimeMillis : Long = 0
    private var totalQuestionsAnswered = 0
    private var totalCorrectAnswers = 0
    private var totalWrongAnswers = 0

    private var currentStreak = 0
    private var highestStreak = 0
    private var totalTimeTaken = 0L // Total time taken to answer all questions (in milliseconds)
    private val timeTakenForLevel =
        mutableMapOf<String, Long>() // Time taken for each difficulty level
    private var questionStartTime = 0L

    private lateinit var selectedDifficulty : String
    private lateinit var subject : String

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        val view =
            inflater.inflate(R.layout.fragment_quiz, container, false)

        selectedDifficulty = arguments?.getString("difficultyLevel") ?: "Easy" // Default difficulty
        subject = arguments?.getString("subject") ?: "Business Mathematics" // Default subject

        TransitionManager.beginDelayedTransition(container!!, AutoTransition())

        if (selectedDifficulty == "Medium") {
            timeLeftMillis = 300000 // 5 minutes in milliseconds for Medium difficulty
            totalTimeMillis = 300000 // 5 minutes in milliseconds for Medium difficulty
        } else if (selectedDifficulty == "Hard") {
            timeLeftMillis = 1200000 // 20 minutes in milliseconds for Hard difficulty
            totalTimeMillis = 1200000 // 20 minutes in milliseconds for Hard difficulty
        }

        timerTextView = view.findViewById(R.id.timerTextView)
        questionTextView = view.findViewById(R.id.questionTextView)
        optionsRadioGroup = view.findViewById(R.id.optionsRadioGroup)
        timerProgressBar = view.findViewById(R.id.timerProgress)
        tVQuestions = view.findViewById(R.id.tv_questions)

        // Display the first question
        fetchQuestions()
        displayQuestion()

        dbHelper = QuizDatabaseHelper(requireContext())
        database = dbHelper.writableDatabase

        val totalQuestions = questions.size
        val currentQuestionNumber = currentQuestionIndex + 1
        quizStartTimeMillis = System.currentTimeMillis()

        view?.findViewById<TextView>(R.id.tv_progress)?.text = buildString {
            append("Question ")
            append(currentQuestionNumber)
        }
        tVQuestions.text = buildString {
            append("out of ")
            append(totalQuestions)
        }

        startTimer()

        return view
    }

    private fun setUnlockStateForLevel(level : String, isUnlocked : Boolean) {
        val sharedPreferences =
            requireContext().getSharedPreferences("UnlockState", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(level, isUnlocked)
        editor.apply()
    }

    private fun increaseTimeForDifficulty() {
        // Check the condition for increasing time
        if (totalCorrectAnswers >= 5) { // You can change this condition as per your requirement
            // Increase time for each difficulty level
            when (selectedDifficulty) {
                "Easy" -> {
                    timeLeftMillis += 60000 // Increase time by 1 minute (60,000 milliseconds)
                    totalTimeMillis += 60000
                }

                "Medium" -> {
                    timeLeftMillis += 60000 // Increase time by 1 minute (60,000 milliseconds)
                    totalTimeMillis += 60000 // Increase total time by 1 minute (60,000 milliseconds)
                }

                "Hard" -> {
                    timeLeftMillis += 600000 // Increase time by 10 minutes (600,000 milliseconds)
                    totalTimeMillis += 600000 // Increase total time by 10 minutes (600,000 milliseconds)
                }
            }
            // Restart the timer with the updated time
            startTimer()
            // Reset the condition so that the time doesn't keep increasing
            totalCorrectAnswers = 0
        }
    }


    private fun displayQuestion() {

        increaseTimeForDifficulty()

        questionStartTime = System.currentTimeMillis()
        // Check if questions are not empty before accessing their elements
        if (questions.isNotEmpty() && currentQuestionIndex < questions.size) {
            val currentQuestion = questions[currentQuestionIndex]

            questionTextView.text = currentQuestion.question
            optionsRadioGroup.removeAllViews()

            for ((index, option) in currentQuestion.options.withIndex()) {
                val radioButton = context?.let { MaterialRadioButton(it) }
                radioButton?.text = option
                radioButton?.id = index
                radioButton?.setBackgroundResource(R.drawable.option_border_bg)
                radioButton?.setTextColor(Color.parseColor("#FF000000"))
                radioButton?.setPadding(32, 16, 32, 16)

                val linearLayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )

                linearLayoutParams.setMargins(0, 0, 0, 32)

                radioButton?.layoutParams = linearLayoutParams
                optionsRadioGroup.addView(radioButton)

                // Set a listener for each RadioButton
                radioButton?.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        // Highlight the selected option when the RadioButton is checked
                        selectedOptionIndex = index
                    } else {
                        radioButton.setBackgroundResource(R.drawable.option_border_bg)
                    }
                }

                radioButton?.setOnClickListener {
                    checkAnswer(index)
                }
            }

            // Update the question number display
            updateQuestionNumber()
        } else {
            // Handle the case where questions are empty or all questions have been displayed
        }
    }

    // Function to update the question number display
    private fun updateQuestionNumber() {
        val currentQuestionNumber = currentQuestionIndex + 1

        view?.findViewById<TextView>(R.id.tv_progress)?.text = buildString {
            append("Question ")
            append(currentQuestionNumber)
        }
    }

    private fun fetchQuestions() {
        dbHelper = QuizDatabaseHelper(requireContext())
        database = dbHelper.writableDatabase

        // Fetch questions based on the selected difficulty level
        // Fetch questions based on the selected difficulty level and subject
        val cursor = database.rawQuery(
            "SELECT * FROM ${QuizDatabaseHelper.TABLE_QUIZ} WHERE " + "${QuizDatabaseHelper.COLUMN_DIFFICULTY_LEVEL} = ? AND " + "${QuizDatabaseHelper.COLUMN_SUBJECT} = ? LIMIT ?",
            arrayOf(selectedDifficulty, subject, totalQuestionsPerGame.toString())
        )

        if (cursor.moveToFirst()) {
            // If there are questions in the cursor, convert them to a list of QuizEntity
            questions = mutableListOf()
            do {
                val idColumnIndex = cursor.getColumnIndex(QuizDatabaseHelper.COLUMN_ID)
                val questionColumnIndex = cursor.getColumnIndex(QuizDatabaseHelper.COLUMN_QUESTION)
                val optionsColumnIndex = cursor.getColumnIndex(QuizDatabaseHelper.COLUMN_OPTIONS)
                val correctAnswerColumnIndex =
                    cursor.getColumnIndex(QuizDatabaseHelper.COLUMN_CORRECT_ANSWER_INDEX)
                val difficultyLevelColumnIndex =
                    cursor.getColumnIndex(QuizDatabaseHelper.COLUMN_DIFFICULTY_LEVEL)

                if (idColumnIndex >= 0 && questionColumnIndex >= 0 && optionsColumnIndex >= 0 && correctAnswerColumnIndex >= 0 && difficultyLevelColumnIndex >= 0) {
                    val id = cursor.getLong(idColumnIndex)
                    val question = cursor.getString(questionColumnIndex)
                    val options = cursor.getString(optionsColumnIndex).split(",")
                    val correctAnswerIndex = cursor.getInt(correctAnswerColumnIndex)
                    val difficultyLevel = cursor.getString(difficultyLevelColumnIndex)

                    val quizEntity = QuizEntity(
                        id, question, options, correctAnswerIndex, difficultyLevel
                    )
                    questions.add(quizEntity)
                } else {
                    // Handle the case where one or more columns are not found
                }
            } while (cursor.moveToNext())
            questions.shuffle()

            // Display the first question
            displayQuestion()
            updateQuestionNumber()
        } else {
            // Handle the case where there are no questions in the cursor
        }
        updateQuestionNumber()
        cursor.close()
    }

    private fun checkAnswer(selectedOptionIndex : Int) {
        if (selectedOptionIndex != -1) {
            if (questions.isNotEmpty() && currentQuestionIndex < questions.size) {
                questions[currentQuestionIndex].selectedAnswerIndex = selectedOptionIndex

                val currentQuestion = questions[currentQuestionIndex]
                val selectedDifficulty = currentQuestion.difficultyLevel

                // Record the time taken for the current question
                val currentTimeTaken = System.currentTimeMillis() - questionStartTime
                totalTimeTaken += currentTimeTaken

                // Update the time taken for the current difficulty level
                val previousTimeTaken = timeTakenForLevel[selectedDifficulty] ?: 0L
                timeTakenForLevel[selectedDifficulty] = previousTimeTaken + currentTimeTaken

                val correctAnswerIndex = questions[currentQuestionIndex].correctAnswerIndex

                if (selectedOptionIndex == correctAnswerIndex) {
                    // Mark option as correct
                    markOptionAsCorrect(selectedOptionIndex)

                    // Increment total correct answers
                    totalCorrectAnswers++

                    // Update current streak
                    currentStreak++
                    if (currentStreak > highestStreak) {
                        highestStreak = currentStreak
                    }

                } else {
                    // Mark option as incorrect
                    val correctOptionIndex = questions[currentQuestionIndex].correctAnswerIndex
                    markOptionAsIncorrect(selectedOptionIndex, correctOptionIndex)

                    // Increment total wrong answers
                    totalWrongAnswers++

                    // Reset current streak
                    currentStreak = 0
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    if (currentQuestionIndex < questions.size - 1) {
                        // Move to the next question
                        currentQuestionIndex++
                        totalQuestionsAnswered++ // Increment totalQuestionsAnswered
                        optionsRadioGroup.clearCheck()

                        // Clear and display the next question
                        clearOptionBackgrounds()
                        displayQuestion()

                    } else {
                        // End of the quiz
                        val score = calculateScore()
                        navigateToQuizResult(score)
                    }
                }, 1000) // Delay for 1 second before moving to the next question
            }
        } else {
            Toast.makeText(context, "Please select an option.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearOptionBackgrounds() {
        for (i in 0 until optionsRadioGroup.childCount) {
            optionsRadioGroup.getChildAt(i)?.background = null
        }
    }

    private fun markOptionAsCorrect(optionIndex : Int) {
        optionsRadioGroup.getChildAt(optionIndex)?.setBackgroundResource(R.drawable.option_correct)
    }

    private fun markOptionAsIncorrect(selectedIndex : Int, correctIndex : Int) {
        val correctColor = R.drawable.option_correct
        val incorrectColor = R.drawable.option_wrong
        optionsRadioGroup.getChildAt(selectedIndex)?.setBackgroundResource(incorrectColor)
        optionsRadioGroup.getChildAt(correctIndex)?.setBackgroundResource(correctColor)
    }

    private fun calculateScore() : Int {
        var score = 0
        for (question in questions) {
            if (question.isAnswerCorrect) {
                score++
            }
        }
        return score
    }

    /**
    private fun moveNextOrEndQuiz() {
    clearOptionBackgrounds()
    optionsRadioGroup.clearCheck()
    if (questionsAnswered < totalQuestionsPerGame) {
    // Move to the next question
    currentQuestionIndex++
    questionsAnswered++
    displayQuestion()
    val selectedDifficulty = this.arguments?.getString("difficultyLevel")
    // Save the progress data after the quiz is completed
    saveProgressData(
    selectedDifficulty!!,
    1,
    )

    } else {
    // End of the quiz
    val score = calculateScore()
    navigateToQuizResult(score)
    }
    } */

    private fun startTimer() {
        // Cancel any existing timer to avoid overlapping
        countDownTimer?.cancel()

        // Create a new CountDownTimer
        countDownTimer = object : CountDownTimer(timeLeftMillis, 1000) {
            override fun onTick(millisUntilFinished : Long) {
                // Update the timer text
                timerTextView.text = buildString {
                    append("Time left: ")
                    append(formatTime(millisUntilFinished))
                }
                // Update the timer ProgressBar
                timerProgressBar.setProgress(((totalTimeMillis - millisUntilFinished) / 1000).toInt())
            }

            override fun onFinish() {
                // Time's up, directly end the quiz
                val score = calculateScore()
                navigateToQuizResult(score)
            }

        }

        // Start the timer
        countDownTimer?.start()
    }

    private fun formatTime(millis : Long) : String {
        val minutes = millis / 60000
        val seconds = (millis % 60000) / 1000
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }

    private fun navigateToQuizResult(score : Int) {
        val resultFragment = QuizResultFragment()
        val difficulty = this.arguments?.getString("difficultyLevel")

        // Calculate time taken
        val quizEndTimeMillis = System.currentTimeMillis()
        val timeTakenMillis = quizEndTimeMillis - quizStartTimeMillis

        // Convert time taken to a formatted string (e.g., "03:30" for 3 minutes and 30 seconds)
        val formattedTimeTaken = formatTime(timeTakenMillis)

        // Check if the user has met the unlocking conditions
        if (difficulty == "Easy" && score >= 8) { //condition: Score of 8 or higher in Easy level
            setUnlockStateForLevel("Medium", true)
        } else if (difficulty == "Medium" && score >= 6) { // condition: Score of 6 or higher in Medium level
            setUnlockStateForLevel("Hard", true)
        }

        // Pass the questions, difficulty, time taken, and score as arguments to the fragment
        val bundle = Bundle()
        bundle.putParcelableArrayList("quizQuestions", ArrayList(questions))
        bundle.putInt("quizScore", score)
        bundle.putString("difficulty", difficulty)
        bundle.putString("timeTaken", formattedTimeTaken)
        bundle.putString("subject", subject)
        resultFragment.arguments = bundle

        // Perform the fragment transaction
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.quiz_container, resultFragment).commit()
    }

}