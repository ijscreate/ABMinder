package com.ijs.abminder.learn.lessons

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Lesson(val name : String, val html : String) : Parcelable
