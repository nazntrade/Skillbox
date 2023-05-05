package com.becker.timer.timerCustomView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


class CustomAnalogTimer : View {

    private val timerListeners = mutableSetOf<(TimerState) -> Unit>()

    var timer = TimerState(0L, false)
        set(value) {
            if (value == field) return
            field = value
            timerListeners.forEach { it(value) }
        }

    private val mClockHoursMap = mapOf(
        1 to "1",
        2 to "2",
        3 to "3",
        4 to "4",
        5 to "5",
        6 to "6",
        7 to "7",
        8 to "8",
        9 to "9",
        10 to "10",
        11 to "11",
        12 to "12"
    )

    private var mHeight = 0
    private var mWight = 0

    private var mPadding = 0
    private val mNumeralSpacing = 0

    private var mHandTruncation = 0
    private var mHourHandTruncation = 0

    private var mRadius = 0
    private lateinit var mPaint: Paint
    private var mRect = Rect()
    private var isInit = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttrs: Int) : super(
        context,
        attrs,
        defStyleAttrs
    )

    override fun onDraw(canvas: Canvas?) {
        if (!isInit) {
            mPaint = Paint()
            mHeight = height
            mWight = width
            mPadding = mNumeralSpacing + 50
            val minAttr = min(mHeight, mWight)
            mRadius = minAttr / 2 - mPadding

            mHandTruncation = minAttr / 20
            mHourHandTruncation = minAttr / 17

            isInit = true
        }
        canvas?.drawColor(Color.BLACK)

        mPaint.reset()
        mPaint.apply {
            color = Color.WHITE
            style = Paint.Style.STROKE
            strokeWidth = 7f
            isAntiAlias = true
        }
        canvas?.drawCircle(
            (mWight / 2).toFloat(),
            (mHeight / 2).toFloat(),
            (mRadius + mPadding - 10).toFloat(), mPaint
        )

        val fontSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15f, resources.displayMetrics)
        mPaint.textSize = fontSize

        for (hour in mClockHoursMap.keys) {
            val tmp = mClockHoursMap[hour]
            mPaint.getTextBounds(tmp, 0, tmp!!.length, mRect)

            val angle = Math.PI / 6 * (hour - 3)
            val x = (mWight / 2 + cos(angle) * mRadius - mRect.width() / 2)
            val y = (mHeight / 2 + sin(angle) * mRadius + mRect.height() / 2)

            canvas?.drawText(tmp, x.toFloat(), y.toFloat(), mPaint)
        }

        var hour = ((timer.time) / (1000 * 3600)).toInt()
        hour = if (hour > 12) hour - 12 else hour

        drawLine(
            canvas!!,
            (hour + (timer.time / (1000 * 60)) / 60) * 5f,
            isHour = true,
            isSecond = false
        )
        drawLine(
            canvas,
            (timer.time / (1000 * 60)).toFloat(),
            isHour = false,
            isSecond = false
        )
        drawLine(
            canvas,
            (timer.time / 1000).toFloat(),
            isHour = false,
            isSecond = true
        )
    }

    private fun drawLine(canvas: Canvas, moment: Float, isHour: Boolean, isSecond: Boolean) {
        val angle = Math.PI * moment / 30 - Math.PI / 2
        val handRadius =
            if (isHour) mRadius - mHandTruncation - mHourHandTruncation else mRadius - mHandTruncation
        if (isSecond) mPaint.color = Color.GREEN
        canvas.drawLine(
            (mWight / 2).toFloat(),
            (mHeight / 2).toFloat(),
            (mWight / 2 + cos(angle) * handRadius).toFloat(),
            (mHeight / 2 + sin(angle) * handRadius).toFloat(),
            mPaint
        )
    }
}