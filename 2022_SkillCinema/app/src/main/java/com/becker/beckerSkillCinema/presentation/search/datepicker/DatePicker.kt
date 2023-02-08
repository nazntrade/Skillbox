package com.becker.beckerSkillCinema.presentation.search.datepicker

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.utils.dpToPx
import com.becker.beckerSkillCinema.utils.dpToSp
import javax.inject.Inject

typealias onDateClickListener = (data: Int) -> Unit

class DatePicker @Inject constructor(
    context: Context, attrs: AttributeSet? = null,
) : View(context, attrs) {

    private val setting = SettingsPicker()

    var setDateListener: onDateClickListener? = null

    init {
        attrs?.let { setAttribute(it) }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setting.setupField(w, h)
        Draw.createMatrix(setting)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Draw.drawArrow(canvas, setting)
        Draw.drawBorder(canvas, setting)
        Draw.drawMatrixDate(canvas, setting)
        Draw.drawRangeData(canvas, setting)
    }

    @SuppressLint("Recycle")
    private fun setAttribute(attrs: AttributeSet) {
        val typArray = context.obtainStyledAttributes(attrs, R.styleable.datepicker)
        setting.borderWidth = typArray.getDimension(
            R.styleable.datepicker_picker_border_width, context.dpToPx(
                DEFAULT_BORDER_WIDTH
            )
        )
        setting.borderColor = typArray.getColor(
            R.styleable.datepicker_picker_border_color, DEFAULT_BORDER_COLOR
        )
        setting.radius = typArray.getDimension(
            R.styleable.datepicker_picker_border_corner_radius, context.dpToPx(10)
        )
        setting.startDate = typArray.getInt(R.styleable.datepicker_picker_start_date, setting.startDate)
        setting.textSizeSP =
            typArray.getDimension(R.styleable.datepicker_picker_text_size, context.dpToSp(15))
        typArray.recycle()
        setting.paint.apply {
            color = Color.BLACK
            strokeWidth = 3f
            style = Paint.Style.STROKE
        }
        setting.textPaint.apply {
            color = setting.textColor
            style = Paint.Style.FILL
            textSize = setting.textSizeSP
        }
    }

    private val listener = TouchListener()

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        listener.onTouchEvent(event, setting, setDateListener) { invalidate() }
        return super.onTouchEvent(event)
    }

    companion object {
        const val DEFAULT_BORDER_WIDTH = 2
        const val DEFAULT_BORDER_COLOR = Color.BLACK
        const val DEFAULT_COLUMN = 3
        const val DEFAULT_ROW = 5
    }
}