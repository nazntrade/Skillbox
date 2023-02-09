package com.becker.beckerSkillCinema.presentation.search.datepicker

import android.graphics.*

class SettingsPicker {

    var borderWidth = 0F
    var borderColor = DatePicker.DEFAULT_BORDER_COLOR
    var textColor = Color.GRAY
    var textColorSelect = Color.rgb(61, 59, 255)
    var radius = 0F
    var textSizeSP = 0F
    var columns = DatePicker.DEFAULT_COLUMN
    var rows = DatePicker.DEFAULT_ROW
    var cellSizeW = 0
    var cellSizeH = 0
    var startDate = 1

    val datePicker = Array(rows) { Array(columns) { Date(0, RectF()) } }

    private var shapeNavigateArrow = RectF()
    var arrowPath = Path()

    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.textSize = textSizeSP
        color = textColor
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
    }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = borderColor
        strokeWidth = borderWidth
        style = Paint.Style.STROKE
    }

    val paintArrow = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        strokeWidth = 5f
        style = Paint.Style.STROKE
        pathEffect = CornerPathEffect(0f)
    }

    var viewRectF = RectF()

    private fun createArrowPath(): Path {
        val path = Path()

        val cellSizeArrowW =
            (shapeNavigateArrow.right - shapeNavigateArrow.left) / 12
        val cellSizeArrowH =
            (shapeNavigateArrow.bottom - shapeNavigateArrow.top) / 2
        val stepHorizontal = cellSizeArrowW
        val stepVertical = cellSizeArrowH / 2
        path.moveTo(
            shapeNavigateArrow.left + stepHorizontal + cellSizeArrowW,
            shapeNavigateArrow.top + cellSizeArrowH
        )
        path.lineTo(
            shapeNavigateArrow.left + cellSizeArrowW,
            shapeNavigateArrow.top + cellSizeArrowH + stepVertical
        )
        path.lineTo(
            shapeNavigateArrow.left + cellSizeArrowW + stepHorizontal,
            shapeNavigateArrow.top + (cellSizeArrowH * 2)
        )

        path.moveTo(
            shapeNavigateArrow.right - stepHorizontal - cellSizeArrowW,
            shapeNavigateArrow.top + cellSizeArrowH
        )
        path.lineTo(
            shapeNavigateArrow.right - cellSizeArrowW,
            shapeNavigateArrow.top + cellSizeArrowH + stepVertical
        )
        path.lineTo(
            shapeNavigateArrow.right - cellSizeArrowW - stepHorizontal,
            shapeNavigateArrow.top + (cellSizeArrowH * 2)
        )
        return path
    }

    fun setupField(w: Int, h: Int) {
        cellSizeW = (((w - borderWidth) / columns).toInt())
        cellSizeH = (((h - borderWidth) / rows).toInt())
        viewRectF.apply {
            left = borderWidth
            top = borderWidth
            right = (cellSizeW * columns).toFloat()
            bottom = (cellSizeH * rows).toFloat()
        }
        shapeNavigateArrow = RectF(
            (viewRectF.right - cellSizeW * 0.6).toFloat(),
            (viewRectF.top + cellSizeH * 0.15).toFloat(),
            (viewRectF.right - cellSizeW * 0.1).toFloat(),
            (cellSizeH - cellSizeH * 0.35).toFloat()
        )
        arrowPath = createArrowPath()
    }
}