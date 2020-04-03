package com.mashjulal.android.taplan.presentation.scheduledtasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.android.ResourceExtractor
import com.mashjulal.android.taplan.presentation.scheduledtasks.viewholder.SectionHeaderViewHolderDelegate
import com.mashjulal.android.taplan.presentation.scheduledtasks.viewholder.TaskViewHolderDelegate
import com.mashjulal.android.taplan.presentation.utils.recyclerview.CompositeViewHolderAdapter
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewModel
import kotlinx.android.synthetic.main.activity_scheduled_tasks.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScheduledTasksActivity : AppCompatActivity() {

    private val viewModel by viewModel<ScheduledTasksViewModel>()
    private lateinit var adapter: CompositeViewHolderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scheduled_tasks)

        initViews()
        initViewModel()
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
}
