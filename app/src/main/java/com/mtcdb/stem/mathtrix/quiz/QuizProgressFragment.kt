package com.mtcdb.stem.mathtrix.quiz

import android.os.*
import android.view.*
import android.widget.*
import androidx.core.content.*
import androidx.fragment.app.*
import com.github.mikephil.charting.charts.*
import com.github.mikephil.charting.data.*
import com.mtcdb.stem.mathtrix.*
import com.mtcdb.stem.mathtrix.quiz.QuizProgressStorage.calculateTotalValues
import com.mtcdb.stem.mathtrix.quiz.QuizProgressStorage.loadProgress
import java.util.*

class QuizProgressFragment : Fragment() {

    private lateinit var tvTotalQuestions : TextView
    private lateinit var tvTotalCorrectAnswers : TextView
    private lateinit var tvTotalWrongAnswers : TextView
    private lateinit var tvAverageScoreEasy : TextView
    private lateinit var tvAverageScoreMedium : TextView
    private lateinit var tvAverageScoreHard : TextView

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        return inflater.inflate(R.layout.fragment_quiz_progress, container, false)
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize TextViews
        tvTotalQuestions = view.findViewById(R.id.tvTotalQuestions)
        tvTotalCorrectAnswers = view.findViewById(R.id.tvTotalCorrectAnswers)
        tvTotalWrongAnswers = view.findViewById(R.id.tvTotalWrongAnswers)
        tvAverageScoreEasy = view.findViewById(R.id.tvAverageScoreEasy)
        tvAverageScoreMedium = view.findViewById(R.id.tvAverageScoreMedium)
        tvAverageScoreHard = view.findViewById(R.id.tvAverageScoreHard)
        val tvCurrentStreak : TextView = view.findViewById(R.id.tvCurrentStreak)
        val tvHighestStreak : TextView = view.findViewById(R.id.tvHighestStreak)
        val tvAverageTimeEasy : TextView = view.findViewById(R.id.tvAverageTimeEasy)
        val tvAverageTimeMedium : TextView = view.findViewById(R.id.tvAverageTimeMedium)
        val tvAverageTimeHard : TextView = view.findViewById(R.id.tvAverageTimeHard)
        val tvFastestTimeEasy : TextView = view.findViewById(R.id.tvFastestTimeEasy)
        val tvFastestTimeMedium : TextView = view.findViewById(R.id.tvFastestTimeMedium)
        val tvFastestTimeHard : TextView = view.findViewById(R.id.tvFastestTimeHard)


        val averageScores = QuizProgressStorage.loadAverageScores(requireContext())

        // Update the UI with the loaded progress data
        val progressDataMap = loadProgress(requireContext())
        val totalValues = calculateTotalValues(progressDataMap)

        // Update the streak data
        progressDataMap.values.forEach { data ->
            val currentStreak = data.getOrNull(3) ?: 0
            val highestStreak = data.getOrNull(4) ?: 0
            tvCurrentStreak.text = currentStreak.toString()
            tvHighestStreak.text = highestStreak.toString()
        }

        tvTotalQuestions.text = totalValues[0].toString()
        // tvTotalCorrectAnswers.text = totalValues[1].toString()
        // tvTotalWrongAnswers.text = totalValues[2].toString()

        // Update average scores for each difficulty level
        averageScores.let { scores ->
            tvAverageScoreEasy.text = scores["Easy"]?.toString() ?: "N/A"
            tvAverageScoreMedium.text = scores["Medium"]?.toString() ?: "N/A"
            tvAverageScoreHard.text = scores["Hard"]?.toString() ?: "N/A"
        }

        // Calculate and update the time-based data
        progressDataMap.forEach { (difficulty, data) ->
            val totalQuestions = data.getOrNull(0) ?: 0
            val totalTimeTaken = data.getOrNull(5) ?: 0L
            val levelTimeTaken = data.getOrNull(6) ?: 0L

            if (totalQuestions > 0) {
                val averageTime = (levelTimeTaken.toDouble() / totalQuestions).toLong()
                val formattedAverageTime = formatTime(averageTime)

                when (difficulty) {
                    "Easy" -> tvAverageTimeEasy.text = formattedAverageTime
                    "Medium" -> tvAverageTimeMedium.text = formattedAverageTime
                    "Hard" -> tvAverageTimeHard.text = formattedAverageTime
                }
            }

            val fastestTime = if (totalTimeTaken != 0L) totalTimeTaken else Long.MAX_VALUE
            val formattedFastestTime = formatTime(fastestTime)

            when (difficulty) {
                "Easy" -> tvFastestTimeEasy.text = formattedFastestTime
                "Medium" -> tvFastestTimeMedium.text = formattedFastestTime
                "Hard" -> tvFastestTimeHard.text = formattedFastestTime
            }
        }

        progressDataMap.values.forEach { data ->
            val currentStreak = data.getOrNull(3) ?: 0
            val highestStreak = data.getOrNull(4) ?: 0
            tvCurrentStreak.text = currentStreak.toString()
            tvHighestStreak.text = highestStreak.toString()

            val averageTimeEasy = formatTime(data.getOrNull(6)?.toLong() ?: 0L)
            val averageTimeMedium = formatTime(data.getOrNull(7)?.toLong() ?: 0L)
            val averageTimeHard = formatTime(data.getOrNull(8)?.toLong() ?: 0L)
            tvAverageTimeEasy.text = averageTimeEasy
            tvAverageTimeMedium.text = averageTimeMedium
            tvAverageTimeHard.text = averageTimeHard

            val fastestTimeEasy = formatTime(data.getOrNull(9)?.toLong() ?: Long.MAX_VALUE)
            val fastestTimeMedium = formatTime(data.getOrNull(10)?.toLong() ?: Long.MAX_VALUE)
            val fastestTimeHard = formatTime(data.getOrNull(11)?.toLong() ?: Long.MAX_VALUE)
            tvFastestTimeEasy.text = fastestTimeEasy
            tvFastestTimeMedium.text = fastestTimeMedium
            tvFastestTimeHard.text = fastestTimeHard
        }

        // Set up the PieChart
        val pieChart : PieChart = view.findViewById(R.id.pieChart)
        setupPieChart(pieChart, totalValues[1], totalValues[2])
    }

    private fun formatTime(millis : Number) : String {
        val minutes = millis.toInt() / 60000
        val seconds = (millis.toInt() % 60000) / 1000
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }

    private fun setupPieChart(pieChart : PieChart, totalCorrect : Int, totalWrong : Int) {
        val entries = listOf(
            PieEntry(totalCorrect.toFloat(), ""), PieEntry(totalWrong.toFloat(), "")
        )

        val dataSet = PieDataSet(entries, "")
        dataSet.setColors(
            ContextCompat.getColor(requireContext(), R.color.green),
            ContextCompat.getColor(requireContext(), R.color.incorrectOptionColor)
        )
        dataSet.valueTextSize = 16f

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.legend.isEnabled = false
        pieChart.description.isEnabled = false
        pieChart.setEntryLabelColor(ContextCompat.getColor(requireContext(), R.color.black))
        pieChart.animateY(1000)
    }

    override fun onDestroy() {
        (activity as? MainActivity)?.supportActionBar?.title =
            getString(R.string.app_name)
        super.onDestroy()
    }
}
