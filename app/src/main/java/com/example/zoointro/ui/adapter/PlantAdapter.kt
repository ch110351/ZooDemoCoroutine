package com.example.zoointro.ui.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.zoointro.widget.DataBindingPagingAdapter
import com.example.zoointro.R
import com.example.zoointro.data.PlantEntity
import com.example.zoointro.widget.ItemClick

class PlantAdapter(itemClick: ItemClick<PlantEntity>?) :
    DataBindingPagingAdapter<PlantEntity>(
        DiffCallback(), itemClick
    ) {
    constructor() : this(null)

    class DiffCallback : DiffUtil.ItemCallback<PlantEntity>() {

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: PlantEntity,
            newItem: PlantEntity
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: PlantEntity,
            newItem: PlantEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun getItemViewType(position: Int) = R.layout.item_plant
}