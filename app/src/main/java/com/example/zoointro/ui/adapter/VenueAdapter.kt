package com.example.zoointro.ui.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.zoointro.widget.ItemClick
import com.example.zoointro.R
import com.example.zoointro.data.VenueEntity
import com.example.zoointro.widget.DataBindingAdapter

class VenueAdapter(itemClick: ItemClick<VenueEntity>?) :
    DataBindingAdapter<VenueEntity>(
        DiffCallback(), itemClick
    ) {
    class DiffCallback : DiffUtil.ItemCallback<VenueEntity>() {

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: VenueEntity,
            newItem: VenueEntity
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: VenueEntity,
            newItem: VenueEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun getItemViewType(position: Int) = R.layout.item_venue
}