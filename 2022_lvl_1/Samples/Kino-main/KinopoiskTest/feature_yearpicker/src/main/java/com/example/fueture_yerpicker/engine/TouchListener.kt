package com.example.fueture_yerpicker.engine

import android.view.MotionEvent
import com.example.fueture_yerpicker.data.SettingsPicker
import com.example.fueture_yerpicker.data.State
import com.example.fueture_yerpicker.tools.newDate
import com.example.fueture_yerpicker.tools.setOnClickListener
import com.example.fueture_yerpicker.view.onDateClickListener

class TouchListener {

    private var datePosition = Pair(0, 0)
    private var oldDatePosition: Pair<Int, Int>? = null

    fun onTouchEvent(event: MotionEvent?, setting: SettingsPicker, setDateListener: onDateClickListener?, block: () -> Unit) {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                datePosition =setting.datePicker.setOnClickListener(event.x, event.y)
                setting.datePicker.newDate(
                        setting.viewRectF
                        .setOnClickListener(event.x, event.y, setting.cellSizeW),setting
                ) { block()}
                if (datePosition != Pair(0, 0)) {
                    if (oldDatePosition != null)
                        setting.datePicker[oldDatePosition!!.first][oldDatePosition!!.second].status =
                            State.NOT_SELECT
                    setting.  datePicker[datePosition.first][datePosition.second].status =
                        State.SELECT
                    setDateListener?.invoke(setting.datePicker[datePosition.first][datePosition.second].date)
                    block()
                    oldDatePosition = datePosition
                }
            }
        }
    }
}