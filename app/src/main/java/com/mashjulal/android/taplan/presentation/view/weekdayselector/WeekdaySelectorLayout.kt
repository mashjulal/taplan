package com.mashjulal.android.taplan.presentation.view.weekdayselector

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridLayout
import androidx.core.view.setMargins


class WeekdaySelectorLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr) {

    private val weekdaySelections: MutableList<Boolean> = mutableListOf()
    private val childClickListener: OnClickListener = OnClickListener {
        val weekdayView = it as WeekdayView
        val activeNew = !weekdayView.active
        weekdayView.active = activeNew
        weekdaySelections[indexOfChild(it)] = activeNew
    }

    init {
        columnCount = 7

    }

    override fun addView(child: View) {
        require(child is WeekdayView) { "Only WeekdayView must be inserted" }
        weekdaySelections.add(child.active)
        child.setOnClickListener(childClickListener)

        val layoutParams = LayoutParams(
            spec(UNDEFINED, 1f),
            spec(UNDEFINED, 1f)
        )
        layoutParams.width = 0
        layoutParams.setMargins(8)
        child.layoutParams = layoutParams
        super.addView(child)
    }


}