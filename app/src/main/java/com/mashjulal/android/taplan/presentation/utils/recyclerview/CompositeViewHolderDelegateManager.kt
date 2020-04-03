package com.mashjulal.android.taplan.presentation.utils.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class CompositeViewHolderDelegateManager (
    private val delegates: List<ItemViewHolderDelegate<ItemViewModel>>
) {

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val delegate = delegates[viewType]
        val v = LayoutInflater.from(parent.context).inflate(delegate.layoutId, parent, false)
        return ItemViewHolder(v)
    }

    fun onBindViewHolder(items: List<ItemViewModel>, holder: ItemViewHolder, position: Int) {
        val item = items[position]
        val delegate = delegates.find { it.appliedTo(item) } ?: throw IllegalArgumentException()
        delegate.bind(holder, item, items, position)
    }

    fun getItemViewType(items: List<ItemViewModel>, position: Int): Int {
        val item = items[position]
        val viewType = delegates.indexOfFirst { it.appliedTo(item) }
        if (viewType == -1) {
            throw IllegalArgumentException()
        }
        return viewType
    }
}