package com.mashjulal.android.taplan.presentation.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class TaskListRecyclerViewAdapter(
    values: List<TaskListModel>
): RecyclerView.Adapter<TaskListRecyclerViewHolder>() {

    private val viewModels: List<TaskListViewModel> = values.map { model ->
        when (model) {
            is TaskModel -> TaskViewModel(model)
            is CalendarDayModel -> CalendarDayViewModel(model)
            is DividerModel -> DividerViewModel
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskListRecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TaskViewModel.ITEM_TYPE, CalendarDayViewModel.ITEM_TYPE -> {
                val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
                TaskListBindableRecyclerViewHolder(binding)
            }
            DividerViewModel.ITEM_TYPE -> {
                val v = inflater.inflate(DividerViewModel.ITEM_TYPE, parent, false)
                TaskListRecyclerViewHolder(v)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun getItemCount(): Int = viewModels.size

    override fun onBindViewHolder(
        holder: TaskListRecyclerViewHolder,
        position: Int
    ) {
        if (holder is TaskListBindableRecyclerViewHolder) {
            val value = viewModels[position]
            DataBindingUtil.getBinding<ViewDataBinding>(holder.itemView)
                ?.setVariable(value.bindingType, value)
                ?: throw IllegalArgumentException("Didn't find any binding for view")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewModels[position].type
    }
}

open class TaskListRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

class TaskListBindableRecyclerViewHolder(
    val viewDataBinding: ViewDataBinding
): TaskListRecyclerViewHolder(viewDataBinding.root)