package com.ijs.abminder.quiz

import android.content.Context
import android.util.Pair
import java.io.File

object QuizProgressStorage {
    private const val PROGRESS_FILE_NAME = "quiz_progress.txt"

    fun loadProgress(context : Context) : MutableMap<String, List<Int>> {
        val file = File(context.getExternalFilesDir(null), PROGRESS_FILE_NAME)
        val progressDataMap = mutableMapOf<String, List<Int>>()

        if (file.exists()) {
            file.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    val dataParts = line.split(":")
                    val difficulty = dataParts[0]
                    val values = dataParts[1].split(",")
                        .map { it.trim().toInt() } // Trim whitespace before parsing
                    progressDataMap[difficulty] = values
                }
            }
        }
        return progressDataMap
    }

    fun loadAverageScores(context : Context) : Map<String, Double> {
        val progressDataMap = loadProgress(context)
        val averageScores = mutableMapOf<String, Double>()

        // Map to store the total correct answers and total games played for each difficulty
        val difficultyDataMap = mutableMapOf<String, Pair<Int, Int>>()

        for ((difficulty, data) in progressDataMap) {
            val totalCorrectAnswers = data.getOrNull(1) ?: 0
            val totalQuestionsAnswered = data.getOrNull(0) ?: 1 // Default to 1 if missing
            val totalGamesPlayed = if (totalQuestionsAnswered > 0) 1 else 0

            val currentData = difficultyDataMap[difficulty] ?: Pair(0, 0)
            val updatedTotalCorrectAnswers = currentData.first + totalCorrectAnswers
            val updatedTotalGamesPlayed = currentData.second + totalGamesPlayed

            difficultyDataMap[difficulty] =
                Pair(updatedTotalCorrectAnswers, updatedTotalGamesPlayed)
        }

        for ((difficulty, data) in difficultyDataMap) {
            val totalCorrectAnswers = data.first
            val totalGamesPlayed = data.second
            val averageScore =
                if (totalGamesPlayed != 0) totalCorrectAnswers.toDouble() / totalGamesPlayed else 0.0
            averageScores[difficulty] = averageScore
        }

        return averageScores
    }

    // Calculate the total values across all difficulties
    fun calculateTotalValues(progressDataMap : Map<String, List<Int>>) : List<Int> {
        var totalQuestions = 0
        var totalCorrectAnswers = 0
        var totalWrongAnswers = 0

        progressDataMap.values.forEach { data ->
            totalQuestions += data.getOrNull(0) ?: 0
            totalCorrectAnswers += data.getOrNull(1) ?: 0
            totalWrongAnswers += data.getOrNull(2) ?: 0
        }

        return listOf(totalQuestions, totalCorrectAnswers, totalWrongAnswers)
    }
}