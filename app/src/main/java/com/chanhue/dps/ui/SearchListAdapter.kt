package com.chanhue.dps.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chanhue.dps.R
import com.chanhue.dps.databinding.ItemSearchBinding
import com.chanhue.dps.model.Contact

class SearchListAdapter(
    private val onClick: (Contact) -> Unit,
) : ListAdapter<Contact, SearchListAdapter.SearchViewHolder>(SearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SearchViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            itemView.setOnClickListener {
                onClick(contact)
            }
            with(binding) {
                Glide.with(ivSearchPetProfileImage)
                    .load(contact.petProfile.thumbnailImage)
                    .into(ivSearchPetProfileImage)
                tvSearchPetName.text = itemView.context.getString(
                    R.string.format_item_name,
                    contact.petProfile.name,
                    contact.owner.name
                )
                tvSearchPhoneNumber.text = contact.owner.phoneNumber
            }
        }
    }
}

private class SearchDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}