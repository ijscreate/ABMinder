package com.ijs.abminder.quiz

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizEntity(
    val id : Long,
    val question : String,
    val options : List<String>,
    val correctAnswerIndex : Int,
    val difficultyLevel : String,
    var selectedAnswerIndex : Int = -1,
) : Parcelable {
    val isAnswerCorrect : Boolean
        get() = selectedAnswerIndex == correctAnswerIndex
}
