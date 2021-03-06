package com.mashjulal.android.taplan.presentation.main.tasks

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.presentation.main.tasks.viewholder.TaskViewHolderDelegate
import com.mashjulal.android.taplan.presentation.utils.activity.ToolbarCustomizable
import com.mashjulal.android.taplan.presentation.utils.recyclerview.CompositeViewHolderAdapter
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewModel
import kotlinx.android.synthetic.main.fragment_scheduled_tasks.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.ClassCastException


class TasksFragment : Fragment() {

    private val viewModel by viewModel<TasksViewModel>()
    private lateinit var adapter: CompositeViewHolderAdapter
    private var toolbarCustomizable: ToolbarCustomizable? = null

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode) {
            viewModel.refreshTasksList()
        }
    }

    private fun initViews() {
        initRecyclerView()
        toolbarCustomizable?.setTitles("Tasks")
    }

    private fun initRecyclerView() {
        adapter = CompositeViewHolderAdapter.Builder()
            .add(TaskViewHolderDelegate())
            .build()
        rv_items.adapter = adapter
    }

    private fun initViewModel() {
        viewModel.itemsLiveData.observe(viewLifecycleOwner, Observer { updateList(it) })
    }

    private fun updateList(items: List<ItemViewModel>) {
        adapter.setItems(items)
    }

    companion object {

        const val TAG = "TasksFragment"

        @JvmStatic
        fun newInstance() =
            TasksFragment()
    }
}
