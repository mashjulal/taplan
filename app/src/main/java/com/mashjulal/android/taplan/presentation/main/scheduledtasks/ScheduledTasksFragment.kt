package com.mashjulal.android.taplan.presentation.main.scheduledtasks

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.presentation.main.scheduledtasks.viewholder.SectionHeaderViewHolderDelegate
import com.mashjulal.android.taplan.presentation.main.scheduledtasks.viewholder.TaskViewHolderDelegate
import com.mashjulal.android.taplan.presentation.utils.activity.ToolbarCustomizable
import com.mashjulal.android.taplan.presentation.utils.recyclerview.CompositeViewHolderAdapter
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewModel
import kotlinx.android.synthetic.main.fragment_scheduled_tasks.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.ClassCastException


class ScheduledTasksFragment : Fragment() {

    private val viewModel by viewModel<ScheduledTasksViewModel>()
    private lateinit var adapter: CompositeViewHolderAdapter
    private var toolbarCustomizable: ToolbarCustomizable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scheduled_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initViewModel()
        toolbarCustomizable?.setTitles("Weekly tasks")
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

    private fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = CompositeViewHolderAdapter.Builder()
            .add(SectionHeaderViewHolderDelegate())
            .add(TaskViewHolderDelegate())
            .build()
        rv_items.adapter = adapter
    }

    private fun initViewModel() {
        viewModel.itemsLiveData.observe(this, Observer { updateList(it) })
    }

    private fun updateList(items: List<ItemViewModel>) {
        adapter.addItems(items)
    }

    companion object {

        const val TAG = "ScheduledTasksFragment"

        @JvmStatic
        fun newInstance() =
            ScheduledTasksFragment()
    }
}
