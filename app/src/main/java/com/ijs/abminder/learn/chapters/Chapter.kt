package com.ijs.abminder.learn.chapters

import com.ijs.abminder.learn.lessons.Lesson

data class Chapter(
    val name : String,
    val lessons : List<Lesson>,
)