package com.mtcdb.stem.mathtrix.learn.lessons

import android.os.*
import kotlinx.parcelize.*

@Parcelize
data class Lesson(val name : String, val html : String) : Parcelable
