package com.mashjulal.android.taplan.presentation.view.timelineschedule

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.mashjulal.android.taplan.R
import kotlin.math.max
import kotlin.math.roundToInt

class TimeLineLayoutGroup : ViewGroup {

    var hourHeight: Int = 32f.toPx()
    var childWidth = 150f.toPx()
    private var dividerStrokeWidth = 1f.toPx()
    private var maxChildrenEnd = 0
    private var numberOfRows = 24
    private var timeVerticalDividerOffset = resources.getDimension(R.dimen.margin_big)
    private var timeHorizontalDividerOffset = resources.getDimension(R.dimen.margin_small)
    private lateinit var dividerPaint: Paint
    private lateinit var textPaint: Paint
    private var dividerColor: Int = Color.WHITE
    private var dividerTextColor: Int = Color.WHITE
    private lateinit var dividerTitles: MutableList<String>

    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }


    @SuppressLint("CustomViewStyleable")
    private fun init(set: AttributeSet?) {

        setWillNotDraw(false)
        layoutTransition = LayoutTransition()
        //if user not set any divider array
        dividerTitles = mutableListOf()
        for (i in 0 until numberOfRows step 1) {
            dividerTitles.add("$i:00")
        }


        if (set == null) {
            dividerPaint = Paint().apply {
                strokeWidth = dividerStrokeWidth.toFloat()
                style = Paint.Style.FILL
                color = dividerColor
            }

            textPaint = Paint().apply {
                color = dividerTextColor
                textSize = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP, 16f,
                    resources.displayMetrics
                )
            }

            return
        }

        initAttrs(set)
    }

    private fun initAttrs(set: AttributeSet) {
        val ta = context.obtainStyledAttributes(set, R.styleable.TimeLineLayout)
        val dividerColorId = ta.getResourceId(R.styleable.TimeLineLayout_dividerColor, android.R.color.black)
        dividerColor = ContextCompat.getColor(context, dividerColorId)
        val dividerTextColorId = ta.getResourceId(R.styleable.TimeLineLayout_dividerTextColor, android.R.color.black)
        dividerTextColor = ContextCompat.getColor(context, dividerTextColorId)
        ta.recycle()

        dividerPaint = Paint().apply {
            strokeWidth = 1f.toPx().toFloat()
            style = Paint.Style.FILL
            color = dividerColor
        }

        textPaint = Paint().apply {
            color = dividerTextColor
            textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16f,
                resources.displayMetrics
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            max(resources.displayMetrics.widthPixels, maxChildrenEnd),
            hourHeight * numberOfRows
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawDividersAndTimeCaption(it)
        }
    }

    private fun drawDividersAndTimeCaption(canvas: Canvas) {
        for (i in 0 until numberOfRows step 1) {
            val y = i * hourHeight.toFloat()
            val rect = Rect()
            textPaint.getTextBounds(dividerTitles[i], 0, dividerTitles[i].length, rect)

            canvas.drawText(
                dividerTitles[i], timeVerticalDividerOffset - rect.width() - timeHorizontalDividerOffset / 2,
                y + hourHeight.toFloat() / 2 + rect.height() / 2, textPaint
            )

            canvas.drawLine(0f, y, measuredWidth.toFloat(), y, dividerPaint)

            if (i == numberOfRows - 1) {
                canvas.drawLine(0f, y + hourHeight.toFloat(),
                    measuredWidth.toFloat(), y + hourHeight.toFloat(),
                    dividerPaint)
            }

        }
    }


    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        maxChildrenEnd = 0
        for (i in 0 until childCount) {
            @Suppress("UNCHECKED_CAST")
            val child = getChildAt(i) as EventView<Event>
            child.top = convertMinuteToPx(child.getEventTime().first)
            child.left = calculateNewChildPlaceLeft(i, child)
            child.left = max(child.left, timeVerticalDividerOffset.roundToInt() + (timeHorizontalDividerOffset / 2).roundToInt())
            child.layout(
                child.left, child.top,
                child.left + child.measuredWidth,
                child.top + child.measuredHeight
            )

            maxChildrenEnd = max(maxChildrenEnd, child.right)
        }
        post {
            requestLayout()
        }
    }

    private fun calculateNewChildPlaceLeft(index: Int, child: EventView<Event>): Int {
        for (i in 0 until index) {
            @Suppress("UNCHECKED_CAST")
            val childToCheckForSpace = getChildAt(i) as EventView<Event>

            //if there is no height collision we can skip to another child to check
            if (!hasCommonTime(child.getEventTime(), childToCheckForSpace.getEventTime())
                || child.left + child.measuredWidth < childToCheckForSpace.left
            )
                continue

            //shift right when detect width collision and check again for new changes
            if (child.left < childToCheckForSpace.right) {
                child.left = childToCheckForSpace.right
                calculateNewChildPlaceLeft(index, child)
            }
        }
        return child.left
    }


    private fun hasCommonTime(firstRange: Pair<Float, Float>, secondRange: Pair<Float, Float>): Boolean =
        !(firstRange.first >= secondRange.second || firstRange.second <= secondRange.first)

    private fun convertMinuteToPx(startTime: Float) = (startTime * hourHeight).roundToInt()

    private fun Float.toPx(): Int {
        return (this * resources.displayMetrics.density).roundToInt()
    }


}