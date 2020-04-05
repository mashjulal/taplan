package com.mashjulal.android.taplan.presentation.main.scheduledtasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.presentation.main.tasks.viewholder.TaskViewHolderDelegate
import com.mashjulal.android.taplan.presentation.utils.recyclerview.CompositeViewHolderAdapter
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewModel
import kotlinx.android.synthetic.main.fragment_scheduled_tasks.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class TasksFragment : Fragment() {

    private val viewModel by viewModel<TasksViewModel>()
    private lateinit var adapter: CompositeViewHolderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initViewModel()
    }

    private fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = CompositeViewHolderAdapter.Builder()
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

        @JvmStatic
        fun newInstance() =
            TasksFragment()
    }
}
