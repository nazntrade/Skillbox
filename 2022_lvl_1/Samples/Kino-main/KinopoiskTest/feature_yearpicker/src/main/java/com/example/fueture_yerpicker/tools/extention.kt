package com.example.fueture_yerpicker.tools

import android.content.Context
import android.graphics.RectF
import android.util.Log
import com.example.fueture_yerpicker.data.Date
import com.example.fueture_yerpicker.data.SettingsPicker
import com.example.fueture_yerpicker.data.StateArrow
import com.example.fueture_yerpicker.engine.Draw

fun Context.dpToPx(dp: Int) = dp.toFloat() * resources.displayMetrics.density

fun Context.dpToSp(dp: Int) =
    dpToPx(dp) / resources.displayMetrics.scaledDensity

fun RectF.setOnClickListener(x: Float, y: Float, cellSizeW: Int): StateArrow {
    var position = StateArrow.NOT_NAVIGATE
    if (y > this.top && y < this.top + cellSizeW/2)
        if (x > this.right - cellSizeW && x < this.right - cellSizeW * 0.3) position =
            StateArrow.BACK
        else if (x > right - cellSizeW * 0.3 && x < this.right) position = StateArrow.NEXT
    return position
}

fun Array<Array<Date>>.setOnClickListener(x: Float, y: Float): Pair<Int, Int> {
    var position = Pair(0, 0)

    this.forEachIndexed { row, dates ->
        dates.forEachIndexed { col, date ->
            val shapeX = date.rect.left
            val shapeX1 = date.rect.right
            val shapeY = date.rect.top
            val shapeY1 = date.rect.bottom
            if (x > shapeX && x < shapeX1 && y > shapeY && y < shapeY1)
                position = Pair(row, col)
        }
    }
    return position
}

fun Array<Array<Date>>.newDate(navigate: StateArrow, setting: SettingsPicker, block:()->Unit) {
    Log.e("Kart", navigate.name)
    when (navigate) {
        StateArrow.NEXT -> {
            setting.startDate = this.last().last().date + 1
            Draw.createMatrix(setting)
            block()
        }
        StateArrow.BACK -> {
            setting.startDate =
                this[1].first().date - ((setting.rows - 1) * setting.columns)
            Draw.createMatrix(setting)
            block()
        }
        StateArrow.NOT_NAVIGATE -> {}
    }

}