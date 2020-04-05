package com.mashjulal.android.taplan.presentation.main.scheduledtasks.viewholder

import com.mashjulal.android.taplan.R
import com.mashjulal.android.taplan.models.presentation.SectionHeaderViewModel
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewHolder
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewHolderDelegate
import com.mashjulal.android.taplan.presentation.utils.recyclerview.ItemViewModel
import kotlinx.android.synthetic.main.item_section_header.view.*

class SectionHeaderViewHolderDelegate: ItemViewHolderDelegate<SectionHeaderViewModel>() {

    override val layoutId: Int = R.layout.item_section_header

    override fun appliedTo(item: Any): Boolean = item is SectionHeaderViewModel

    override fun bind(
        holder: ItemViewHolder,
        item: SectionHeaderViewModel,
        items: List<ItemViewModel>,
        position: Int
    ) {
        with(holder.itemView) {
            tv_title.text = item.title
        }
    }
}