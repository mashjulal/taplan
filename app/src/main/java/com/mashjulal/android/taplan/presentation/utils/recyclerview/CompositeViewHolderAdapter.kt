package com.mashjulal.android.taplan.presentation.utils.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class CompositeViewHolderAdapter private constructor(
    delegates: List<ItemViewHolderDelegate<ItemViewModel>>
): RecyclerView.Adapter<ItemViewHolder>() {

    private val delegatesManager = CompositeViewHolderDelegateManager(delegates)
    private val data: MutableList<ItemViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        delegatesManager.onCreateViewHolder(parent, viewType)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(data, holder, position)
    }

    override fun getItemViewType(position: Int): Int =
        delegatesManager.getItemViewType(data, position)

    fun addItems(items: List<ItemViewModel>) {
        data.addAll(items)
        notifyItemRangeChanged(itemCount-items.size-1, items.size)
    }

    fun setItems(items: List<ItemViewModel>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    class Builder {

        private val delegates: MutableList<ItemViewHolderDelegate<ItemViewModel>> = mutableListOf()

        fun add(delegate: ItemViewHolderDelegate<ItemViewModel>): Builder {
            delegates.add(delegate)
            return this
        }

        fun build() = CompositeViewHolderAdapter(delegates)

    }
}