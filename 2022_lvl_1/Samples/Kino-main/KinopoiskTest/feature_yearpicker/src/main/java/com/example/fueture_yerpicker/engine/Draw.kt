package com.example.fueture_yerpicker.engine

import android.graphics.Canvas
import android.graphics.RectF
import com.example.fueture_yerpicker.data.SettingsPicker
import com.example.fueture_yerpicker.data.State

object Draw {

    fun createMatrix(setting: SettingsPicker) {

        for (row in 1 until setting.rows) {
            val cellHeight = setting.cellSizeH * row
            for (col in 0 until setting.columns) {

                setting.datePicker[row][col].apply {
                    rect.left = ((setting.cellSizeW * col).toFloat())
                    rect.top = cellHeight.toFloat()
                    rect.right = rect.left + setting.cellSizeW
                    rect.bottom = rect.top + setting.cellSizeH
                    date = setting.startDate++
                    status = State.NOT_SELECT
                }
            }
        }
    }

    fun drawMatrixDate(canvas: Canvas?, setting: SettingsPicker) {
        setting.datePicker.forEach { dates ->
            dates.forEach { date ->
                setting.textPaint.color =
                    if (date.status == State.NOT_SELECT) setting.textColor
                    else setting.textColorSelect
                canvas?.drawText(
                    date.date.toString(), date.rect.centerX(),
                    date.rect.centerY(), setting.textPaint
                )
            }
        }
    }

    fun drawRangeData(canvas: Canvas?, setting: SettingsPicker) {
        val rect = RectF(
            setting.borderWidth,
            setting.borderWidth,
            setting.borderWidth + setting.cellSizeW,
            setting.borderWidth + setting.cellSizeH
        )
        setting.textPaint.color = setting.textColorSelect
        canvas?.drawText(
            "${setting.datePicker[1].first().date} - ${
                setting.datePicker.last().last().date
            }",
            (rect.centerX() + rect.centerX() * 0.2).toFloat(),
            (rect.centerY() + rect.centerY() * 0.2).toFloat(),
            setting.textPaint
        )
    }

    fun drawArrow(canvas: Canvas?, setting: SettingsPicker) {
        setting.textPaint.color = setting.textColor
        canvas?.drawPath(setting.arrowPath, setting.paintArrow)
    }

    fun drawBorder(canvas: Canvas?, setting: SettingsPicker) {
        canvas?.drawRoundRect(
            setting.viewRectF,
            setting.radius,
            setting.radius,
            setting.paint
        )
    }

}