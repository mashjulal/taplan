package com.mashjulal.android.taplan.presentation.main.tasks.viewholder

import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.models.presentation.ScheduledTaskViewModel
import com.mashjulal.android.taplan.presentation.task.AboutTaskActivity
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewHolder
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewHolderDelegate
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewModel
import kotlinx.android.synthetic.main.item_task_2.view.*


class TaskViewHolderDelegate: ItemViewHolderDelegate<ScheduledTaskViewModel>() {

    override val layoutId: Int = R.layout.item_task_2

    override fun appliedTo(item: Any): Boolean = item is ScheduledTaskViewModel

    override fun bind(
        holder: ItemViewHolder,
        item: ScheduledTaskViewModel,
        items: List<ItemViewModel>,
        position: Int
    ) {
        val lastInSection = (position == items.size - 1)
        with(holder.itemView) {
            tv_title.text = item.title
            tv_period.visibility = View.GONE
            divider.visibility = if (lastInSection) View.GONE else View.VISIBLE
            setRippleEffectOnClick(this)
            setOnClickListener {
                val activity = it.context as AppCompatActivity
                activity.startActivityForResult(AboutTaskActivity.newIntent(activity, item.model), AboutTaskActivity.REQUEST_CODE_SHOW)
            }
        }
    }
}