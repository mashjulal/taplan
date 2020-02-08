package com.mashjulal.android.taplan.presentation.view.weekdayselector

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import kotlin.math.max

class WeekdayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private val activePaint: Paint = Paint().apply {
        color = Color.YELLOW
        isAntiAlias = true
    }
    private val inactivePaint: Paint = Paint().apply {
        color = Color.RED
        isAntiAlias = true
    }

    private var strokeWidth = 2
    var active: Boolean = false
    set(value) {
        field = value
        invalidate()
    }

    init {
        gravity = Gravity.CENTER
        includeFontPadding = false
    }

    override fun onDraw(canvas: Canvas) {
        val diameter = max(width, height)
        val radius = diameter.toFloat() / 2

        height = diameter
        width = diameter

        val primaryPaint = if (active) activePaint else inactivePaint
        val secondaryPaint = if (active) inactivePaint else activePaint
        canvas.drawCircle(radius, radius, radius, primaryPaint)
        canvas.drawCircle(radius, radius, radius-strokeWidth, secondaryPaint)
        setTextColor(primaryPaint.color)

        super.onDraw(canvas)
    }
}