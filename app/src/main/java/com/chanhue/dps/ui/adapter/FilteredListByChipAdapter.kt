package com.chanhue.dps.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chanhue.dps.databinding.ItemFilteredBinding

class FilteredListByChipAdapter(
    private val onClick: (String) -> Unit,
) : ListAdapter<String, FilteredListByChipAdapter.FilteredStringViewHolder>(FilteredItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilteredStringViewHolder {
        val binding =
            ItemFilteredBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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