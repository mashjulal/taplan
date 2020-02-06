package com.mashjulal.android.taplan.presentation.view.timelineschedule

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.mashjulal.android.taplan.R
import kotlin.math.roundToInt

class EventView<T : Event>(
    context: Context,
    private val event: T,
    itemsMargin: Int = R.dimen.margin_small
) : FrameLayout(context) {

    private val marginBetweenItems: Int = resources.getDimension(itemsMargin).roundToInt()

    init {
        init()
    }


    private fun init() {
        setPadding(marginBetweenItems, 0, marginBetweenItems, 0)

        val view = View.inflate(context, R.layout.item_event, parent as? ViewGroup)
        view.findViewById<TextView>(R.id.tvTitle).text = event.title
        addView(view)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val oneHourHeight = (parent as TimeLineLayoutGroup).hourHeight
        val calculatedHeight: Float = (event.endTime - event.startTime) * oneHourHeight
        val childWidth = (parent as TimeLineLayoutGroup).childWidth
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(calculatedHeight.roundToInt(), MeasureSpec.EXACTLY)
        )
    }

    fun getEventTime() = event.startTime to event.endTime
}