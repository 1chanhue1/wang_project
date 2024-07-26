package com.chanhue.dps.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chanhue.dps.R
import com.chanhue.dps.databinding.ItemFilteredBinding
import com.chanhue.dps.databinding.ItemSearchBinding
import com.chanhue.dps.model.Contact

class FilteredListByChipAdapter(
    private val onClick: (String) -> Unit,
) : ListAdapter<String, FilteredListByChipAdapter.FilteredStringViewHolder>(FilteredItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilteredStringViewHolder {
        val binding = ItemFilteredBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilteredStringViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilteredStringViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FilteredStringViewHolder(private val binding: ItemFilteredBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            itemView.setOnClickListener {
                onClick(item)
            }
            with(binding) {
                tvFilteredItemTitle.text = item
            }
        }
    }
}

private class FilteredItemDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}