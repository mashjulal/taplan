package com.mashjulal.android.taplan.presentation.view.timelineschedule

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ScrollView

class TimeLineLayout : ScrollView {
    private lateinit var horizontalScrollView: HorizontalScrollView

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    {
        horizontalScrollView = HorizontalScrollView(context)
        horizontalScrollView.addView(TimeLineLayoutGroup(context,attrs))
        addView(horizontalScrollView)
        post {
            horizontalScrollView.requestLayout()
        }
    }

    fun <T : Event>addEvent(child: EventView<T>?) {
        (horizontalScrollView.getChildAt(0) as ViewGroup).addView(child)
    }
}