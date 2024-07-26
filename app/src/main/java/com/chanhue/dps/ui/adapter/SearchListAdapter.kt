package com.chanhue.dps.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chanhue.dps.R
import com.chanhue.dps.databinding.ItemSearchBinding
import com.chanhue.dps.model.Contact
import com.chanhue.dps.ui.SearchDiffCallback

class SearchListAdapter(
    private val onClick: (Contact) -> Unit,
) : ListAdapter<Contact, SearchListAdapter.SearchViewHolder>(SearchDiffCallback()), Filterable {

    private var originalList: List<Contact> = emptyList()

    override fun submitList(list: List<Contact>?) { // 검색어가 없을 때 전체 리스트를 originalList에 저장
        originalList = list ?: emptyList()
        super.submitList(list)
    }

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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val filteredList = if (charSequence.isNullOrBlank()) {
                    originalList
                } else {
                    filterContactList(originalList, charSequence.toString())
                }
                return FilterResults().apply {
                    values = filteredList
                }
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                val filteredContactList = filterResults.values as? List<Contact> ?: emptyList()
                submitFilteredList(filteredContactList)
            }
        }
    }

    private fun submitFilteredList(list: List<Contact>) { // 검색 결과를 리스트에 적용
        super.submitList(list) // ListAdapter의 submitList 함수를 사용하여 리스트를 갱신
    }

    // 검색어에 해당하는 연락처 리스트를 반환
    private fun filterContactList(list: List<Contact>, stringData: String): List<Contact> {
        val replacedStringData = stringData.replace("-", "") // 하이픈 제거
        return list.filter { contact ->
            val fullName = "${contact.petProfile.name} | ${contact.owner.name}"
            val replacedPhoneNumber = contact.owner.phoneNumber.replace("-", "")
            fullName.contains(stringData, ignoreCase = true) || replacedPhoneNumber.contains(replacedStringData)
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
