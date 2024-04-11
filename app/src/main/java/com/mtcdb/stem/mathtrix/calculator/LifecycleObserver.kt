package com.mtcdb.stem.mathtrix.calculator

import androidx.appcompat.widget.*
import androidx.lifecycle.*
import com.mtcdb.stem.mathtrix.R

class CalculatorFragmentLifecycleObserver(private val toolbar: Toolbar) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        toolbar.title = toolbar.context.getString(R.string.calculator)
    }
}
