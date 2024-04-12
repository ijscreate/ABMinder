package com.ijs.abminder.calculator

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.ijs.abminder.R

class CalculatorFragmentLifecycleObserver(private val toolbar: Toolbar) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        toolbar.title = toolbar.context.getString(R.string.calculator)
    }
}
