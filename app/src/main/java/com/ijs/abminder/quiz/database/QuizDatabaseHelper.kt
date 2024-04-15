package com.ijs.abminder.quiz.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class QuizDatabaseHelper(context : Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 6
        private const val DATABASE_NAME = "quiz_db.db"

        const val TABLE_QUIZ = "quiz"
        const val COLUMN_ID = "id"
        const val COLUMN_SUBJECT = "subject"
        const val COLUMN_QUESTION = "question"
        const val COLUMN_OPTIONS = "options"
        const val COLUMN_CORRECT_ANSWER_INDEX = "correct_answer_index"
        const val COLUMN_DIFFICULTY_LEVEL = "difficulty_level"
    }

    override fun onCreate(db : SQLiteDatabase) {
        @Suppress("LocalVariableName") val CREATE_QUIZ_TABLE = "CREATE TABLE $TABLE_QUIZ (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_SUBJECT TEXT NOT NULL, " +
                "$COLUMN_QUESTION TEXT NOT NULL, " +
                "$COLUMN_OPTIONS TEXT NOT NULL, " +
                "$COLUMN_CORRECT_ANSWER_INDEX INTEGER NOT NULL, " +
                "$COLUMN_DIFFICULTY_LEVEL TEXT NOT NULL)"

        db.execSQL(CREATE_QUIZ_TABLE)
    }

    override fun onUpgrade(db : SQLiteDatabase, oldVersion : Int, newVersion : Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_QUIZ")
        onCreate(db)

    }

    open fun insertQuizData(
        subject : String,
        question : String,
        options : String,
        correctAnswerIndex : Int,
        difficultyLevel : String,
    ) {
        val values = ContentValues().apply {
            put(COLUMN_SUBJECT, subject)
            put(COLUMN_QUESTION, question)
            put(COLUMN_OPTIONS, options)
            put(COLUMN_CORRECT_ANSWER_INDEX, correctAnswerIndex)
            put(COLUMN_DIFFICULTY_LEVEL, difficultyLevel)
        }
        val db = writableDatabase

        db.insert(TABLE_QUIZ, null, values)
    }

    open fun deleteDuplicateQuestions() {
        val db = writableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_ID, $COLUMN_QUESTION FROM $TABLE_QUIZ", null)

        if (cursor.moveToFirst()) {
            val questionIndex = cursor.getColumnIndex(COLUMN_QUESTION)
            val idIndex = cursor.getColumnIndex(COLUMN_ID)
            val questionsSet = HashSet<String>()

            do {
                val question = cursor.getString(questionIndex).trim()
                val first5Words = question.split(" ").take(5).joinToString(" ")

                if (questionsSet.contains(first5Words)) {
                    // Delete the duplicate entry
                    val id = cursor.getInt(idIndex)
                    db.delete(TABLE_QUIZ, "$COLUMN_ID=?", arrayOf(id.toString()))
                } else {
                    questionsSet.add(first5Words)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
    }
}
