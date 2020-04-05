package com.mashjulal.android.taplan.presentation.main.scheduledtasks.viewholder

import android.view.View
import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.models.presentation.SectionHeaderViewModel
import com.mashjulal.android.taplan.models.presentation.TaskViewModel
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewHolder
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewHolderDelegate
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewModel
import kotlinx.android.synthetic.main.item_task_2.view.*

class TaskViewHolderDelegate: ItemViewHolderDelegate<TaskViewModel>() {

    override val layoutId: Int = R.layout.item_task_2

    override fun appliedTo(item: Any): Boolean = item is TaskViewModel

    override fun bind(
        holder: ItemViewHolder,
        item: TaskViewModel,
        items: List<ItemViewModel>,
        position: Int
    ) {
        val lastInSection = (position == items.size - 1) || (items[position+1] is SectionHeaderViewModel)
        with(holder.itemView) {
            tv_title.text = item.title
            tv_period.text = item.period
            divider.visibility = if (lastInSection) View.GONE else View.VISIBLE
        }
    }
}