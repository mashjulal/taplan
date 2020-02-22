package com.mashjulal.android.taplan.presentation.main.today

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.mashjulal.android.taplan.BR
import com.mashjulal.android.taplan.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TaskRecyclerViewAdapter(
    items: List<Pair<String, Long>>
): RecyclerView.Adapter<TaskViewHolder>() {

    private val viewModels: List<UpcommingTaskViewModel> = items.map { UpcommingTaskViewModel(it) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater,
            R.layout.item_upcoming_event, parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int = viewModels.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val value = viewModels[position]
        DataBindingUtil.getBinding<ViewDataBinding>(holder.itemView)
            ?.setVariable(BR.nextTaskViewModel, value)
            ?: throw IllegalArgumentException("Didn't find any binding for view")
    }
}

class TaskViewHolder(
    viewDataBinding: ViewDataBinding
) : RecyclerView.ViewHolder(viewDataBinding.root)

class UpcommingTaskViewModel(task: Pair<String, Long>) {
    val title = task.first
    val startTime: String

    init {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = task.second
        startTime = SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.time)
    }
}