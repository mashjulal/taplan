package com.mashjulal.android.taplan.presentation.utils.recyclerview

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class ItemViewHolderDelegate<out T: ItemViewModel> {

    abstract val layoutId: Int

    abstract fun bind(holder: ItemViewHolder, item: @UnsafeVariance T, items: List<ItemViewModel>, position: Int)

    abstract fun appliedTo(item: Any): Boolean

    fun setRippleEffectOnClick(view: View) {
        val outValue = TypedValue()
        view.context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        view.setBackgroundResource(outValue.resourceId)
    }
}