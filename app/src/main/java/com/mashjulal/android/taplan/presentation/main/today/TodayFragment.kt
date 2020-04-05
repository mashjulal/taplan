package com.mashjulal.android.taplan.presentation.main.today

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.presentation.utils.activity.ToolbarCustomizable
import kotlinx.android.synthetic.main.fragment_today.*
import java.lang.ClassCastException
import java.text.SimpleDateFormat
import java.util.*


class TodayFragment : Fragment() {

    private var toolbarCustomizable: ToolbarCustomizable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_today, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindTaskCard()
        bindNextTaskList()
        initToolbar()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is ToolbarCustomizable) {
            throw ClassCastException()
        }
        toolbarCustomizable = context
    }

    override fun onDetach() {
        super.onDetach()
        toolbarCustomizable = null
    }

    private fun initToolbar() {
        toolbarCustomizable?.let {
            val calendar = Calendar.getInstance(Locale.getDefault())
            val timeFormatter = SimpleDateFormat.getDateInstance()
            it.setTitles(getString(R.string.today), timeFormatter.format(calendar.time))
        }
    }

    private fun bindNextTaskList() {
        tv_card_next_task_name.text = "Task"
        tv_card_next_task_time.text = "10:00"
        tv_card_next_remaining_time.text = "Will start in 30 minutes"
        tv_card_next_period.text = "10:00-10:30"
    }

    private fun bindTaskCard() {
        val adapter = TaskRecyclerViewAdapter(listOf(
            "Task 1" to (11 * 60 * 60 * 1000).toLong(),
            "Task 2" to (12 * 60 * 60 * 1000).toLong(),
            "Task 3" to (13 * 60 * 60 * 1000).toLong()
        ))
        rv_next_events.adapter = adapter
        rv_next_events.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TodayFragment()
    }
}
