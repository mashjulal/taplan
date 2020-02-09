package com.mashjulal.android.taplan.presentation.view.timeperiodseekbar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.mashjulal.android.taplan.R
import kotlin.math.max
import kotlin.math.min
import kotlin.math.abs
import kotlin.math.roundToInt
import java.lang.IllegalArgumentException


class TimePeriodSeekbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val thumbDrawable: Drawable = ContextCompat.getDrawable(context, R.drawable.trumb)
        ?: throw IllegalArgumentException("Thumb drawable must be specified")
    private val thumbSize = 20f.toPx().toInt()

    private var barMinValue: Int = 0
    private var barMaxValue: Int = 100
    private var barStepValue: Int = 1

    private var barStepMinValue: Int = barMinValue / barStepValue
    private var barStepMaxValue: Int = barMaxValue / barStepValue

    private var thumbMinValue: Int = barMinValue
    private var thumbMaxValue: Int = barMaxValue

    private val rect: RectF = RectF()
    private val paint: Paint = Paint()

    private var activePointerId: Int = 0
    private var downMotionX: Float = 0f
    private var pressedThumb: Thumb? = null
    private var dragging = false

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // draw seek bar background line
        rect.left = progressToPx(barMinValue)
        rect.top = paddingTop.toFloat()
        rect.right = progressToPx(barMaxValue)
        rect.bottom = height.toFloat() - paddingBottom.toFloat()

        paint.isAntiAlias = true
        paint.color = Color.LTGRAY
        canvas.drawRect(rect, paint)

        // draw seek bar active line
        rect.left = progressToPx(thumbMinValue)
        rect.right = progressToPx(thumbMaxValue)

        paint.color = Color.GREEN
        canvas.drawRect(rect, paint)

        // draw minimum thumb
        drawThumb(progressToPx(thumbMinValue), canvas)

        // draw maximum thumb
        drawThumb(progressToPx(thumbMaxValue), canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) return false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                activePointerId = event.getPointerId(event.pointerCount - 1)
                downMotionX = event.getX(event.findPointerIndex(activePointerId))

                pressedThumb = getPressedThumb(downMotionX)
                if (pressedThumb == null) return super.onTouchEvent(event)

                isPressed = true
                onStartTrackingTouch()
                trackTouchEvent(event)

                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                // Only handle thumb moving
                if (pressedThumb == null) return super.onTouchEvent(event)

                if (dragging) {
                    trackTouchEvent(event)
                }
            }
            MotionEvent.ACTION_UP -> {
                if (dragging) {
                    trackTouchEvent(event)
                    onStopTrackingTouch()
                    isPressed = false
                }

                pressedThumb = null
                performClick()
                invalidate()
            }
        }

        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    private fun onStartTrackingTouch() {
        dragging = true
    }

    private fun onStopTrackingTouch() {
        dragging = false
    }

    private fun trackTouchEvent(event: MotionEvent) {
        val pointerIndex = event.findPointerIndex(activePointerId)
        val x = event.getX(pointerIndex)
        val progress = pxToProgress(x)
        when (pressedThumb) {
            Thumb.MIN -> setThumbMinValue(progress)
            Thumb.MAX -> setThumbMaxValue(progress)
        }
    }

    private fun getPressedThumb(x: Float): Thumb? {
        var result: Thumb? = null
        val minThumbPressed = isInThumbRange(x, thumbMinValue)
        val maxThumbPressed = isInThumbRange(x, thumbMaxValue)
        if (minThumbPressed) {
            result = Thumb.MIN
        } else if (maxThumbPressed) {
            result = Thumb.MAX
        }
        return result
    }

    private fun isInThumbRange(x: Float, thumbValue: Int): Boolean {
        return abs(x - progressToPx(thumbValue)) <= thumbSize / 2
    }

    private fun setThumbMinValue(value: Int) {
        thumbMinValue = roundOffValueToStep(max(barMinValue, min(barMaxValue, min(value, thumbMaxValue))))
        invalidate()
    }

    private fun setThumbMaxValue(value: Int) {
        thumbMaxValue = roundOffValueToStep(max(barMinValue, min(barMaxValue, max(value, thumbMinValue))))
        invalidate()
    }

    private fun drawThumb(
        screenCoord: Float,
        canvas: Canvas
    ) {
        thumbDrawable.setBounds(
            screenCoord.toInt() - thumbSize / 2,
            0,
            screenCoord.toInt() + thumbSize / 2,
            thumbSize
        )
        thumbDrawable.draw(canvas)
    }

    private fun roundOffValueToStep(value: Int): Int {
        val d = value / barStepValue
        return max(barStepMinValue, min(barStepMaxValue, d)) * barStepValue
    }

    private fun progressToPx(value: Int): Float {
        val progress = value.toFloat() / barMaxValue
        return paddingLeft + progress * (width - (paddingStart + paddingEnd))
    }

    private fun pxToProgress(value: Float): Int {
        val result = (value - paddingStart) / (width - (paddingStart + paddingEnd))
        return (min(1f, max(0f, result)) * barMaxValue).roundToInt()
    }

    private fun Float.toPx() = this * resources.displayMetrics.density

    private enum class Thumb {
        MIN, MAX
    }
}