package com.mashjulal.android.taplan.presentation.calendar

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mashjulal.android.taplan.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView

class CalendarFragment : Fragment() {
    private var selectedDay = 0
    private var selectedMonth = 0
    private var selectedYear = 0

    private var onCalendarChangedListener: OnCalendarChangedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedDay = it.getInt(ARG_DAY)
            selectedMonth = it.getInt(ARG_MONTH)
            selectedYear = it.getInt(ARG_YEAR)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is OnCalendarChangedListener) {
            throw ClassCastException()
        }
        onCalendarChangedListener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(view.findViewById<MaterialCalendarView>(R.id.calendar)) {
            val day = CalendarDay.from(selectedYear, selectedMonth, selectedDay)
            currentDate = day
            selectedDate = day

            topbarVisible = false
            setOnMonthChangedListener { _, date ->
                onCalendarChangedListener?.onMonthChanged(date.month, date.year)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        onCalendarChangedListener = null
    }

    companion object {

        const val TAG = "CalendarFragment"

        private const val ARG_DAY = "ARG_DAY"
        private const val ARG_MONTH = "ARG_MONTH"
        private const val ARG_YEAR = "ARG_YEAR"

        @JvmStatic
        fun newInstance(selectedDay: Int, selectedMonth: Int, selectedYear: Int) =
            CalendarFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_DAY, selectedDay)
                    putInt(ARG_MONTH, selectedMonth)
                    putInt(ARG_YEAR, selectedYear)
                }
            }
    }

    interface OnCalendarChangedListener {
        fun onMonthChanged(month: Int, year: Int)
    }
}
