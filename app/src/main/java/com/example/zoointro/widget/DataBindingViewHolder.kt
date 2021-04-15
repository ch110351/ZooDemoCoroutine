package com.example.zoointro.widget

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

class DataBindingViewHolder<T>(
    private val binding: ViewDataBinding,
    private val itemClick: ItemClick<T>? = null
) :
    RecyclerView.ViewHolder(binding.root) {
    constructor(binding: ViewDataBinding) : this(binding, null)

    fun bind(item: T) {
        binding.setVariable(BR.item, item)
        itemClick?.let {
            binding.setVariable(BR.itemclick, itemClick)
        }
        binding.executePendingBindings()
    }
}
