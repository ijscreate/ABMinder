package com.ijs.abminder.quiz.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream

class ProgressDatabaseHelper(context : Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "progress.db"
        private const val TABLE_PROGRESS = "progress_table"

        // Column names
        private const val COLUMN_ID = "_id"
        private const val COLUMN_TOTAL_QUESTIONS_ANSWERED = "total_questions_answered"
        private const val COLUMN_TOTAL_CORRECT_ANSWERS = "total_correct_answers"
        private const val COLUMN_TOTAL_WRONG_ANSWERS = "total_wrong_answers"
        private const val COLUMN_TOTAL_TIME_TAKEN = "total_time_taken"
        private const val COLUMN_HIGHEST_STREAK = "highest_streak"
        private const val COLUMN_TIME_TAKEN_FOR_LEVEL = "time_taken_for_level"
    }

    override fun onCreate(db : SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_PROGRESS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_TOTAL_QUESTIONS_ANSWERED INTEGER," +
                "$COLUMN_TOTAL_CORRECT_ANSWERS INTEGER," +
                "$COLUMN_TOTAL_WRONG_ANSWERS INTEGER," +
                "$COLUMN_TOTAL_TIME_TAKEN INTEGER," +
                "$COLUMN_HIGHEST_STREAK INTEGER," +
                "$COLUMN_TIME_TAKEN_FOR_LEVEL TEXT)"

        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db : SQLiteDatabase?, oldVersion : Int, newVersion : Int) {
        // Drop older table if existed and create fresh table
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PROGRESS")
        onCreate(db)
    }

    // Function to insert progress data into the database
    fun insertProgressData(
        totalQuestionsAnswered : Int,
        totalCorrectAnswers : Int,
        totalWrongAnswers : Int,
        totalTimeTaken : Long,
        highestStreak : Int,
        timeTakenForLevel : HashMap<*, *>,
    ) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_TOTAL_QUESTIONS_ANSWERED, totalQuestionsAnswered)
            put(COLUMN_TOTAL_CORRECT_ANSWERS, totalCorrectAnswers)
            put(COLUMN_TOTAL_WRONG_ANSWERS, totalWrongAnswers)
            put(COLUMN_TOTAL_TIME_TAKEN, totalTimeTaken)
            put(COLUMN_HIGHEST_STREAK, highestStreak)
            put(COLUMN_TIME_TAKEN_FOR_LEVEL, serializeHashMap(timeTakenForLevel))
        }
        db.insert(TABLE_PROGRESS, null, contentValues)
        db.close()
        db.endTransaction()
    }

    // Function to serialize a HashMap to a String
    private fun serializeHashMap(map : HashMap<*, *>) : String {
        val outputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(outputStream)
        objectOutputStream.writeObject(map)
        objectOutputStream.close()
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }
}
