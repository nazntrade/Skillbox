package com.becker.timer.timerCustomView

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.becker.timer.R

class CustomDigitalTimer
    @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int = 0
    ) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val viewHours: TextView
    private val viewMinutes: TextView
    private val viewSeconds: TextView

        private var timer = TimerState(0, false)
            set(value) {
                if (value == field) return
                field = value
            }

    init {
        val rootView = inflate(context, R.layout.view_digital_timer, this)
        viewHours = rootView.findViewById(R.id.view_hours)
        viewMinutes = rootView.findViewById(R.id.view_minutes)
        viewSeconds = rootView.findViewById(R.id.view_seconds)
        viewHours.text = resources.getString(R.string.doubleZero)
        viewMinutes.text = resources.getString(R.string.doubleZero)
        viewSeconds.text = resources.getString(R.string.doubleZero)
    }
}