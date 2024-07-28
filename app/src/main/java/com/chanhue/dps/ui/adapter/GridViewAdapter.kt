package com.chanhue.dps.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chanhue.dps.databinding.GridListitemBinding
import com.chanhue.dps.model.Contact

class GridViewAdapter(
    var items: MutableList<Contact>,
    var onItemClick: (Contact) -> Unit,
    var onItemLongClick: (Contact) -> Unit
) : RecyclerView.Adapter<GridViewAdapter.Holder>()  {
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = GridListitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClick(items[position])
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClick(items[position])
            true
        }

        Glide.with(holder.productImg)
            .load(items[position].petProfile.thumbnailImage)
            .into(holder.productImg)

        holder.productPname.text = items[position].petProfile.name
        holder.productOname.text = items[position].owner.name

        Log.d("grid",items[position].toString())
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Holder(val binding: GridListitemBinding) : RecyclerView.ViewHolder(binding.root) {
        val productImg = binding.gridIvItemIn
        val productPname = binding.gridTvPName
        val productOname = binding.gridTvOName
    }

//    fun updateContacts(newContacts: List<Contact>) {
//        items = newContacts.toMutableList()
//        notifyDataSetChanged()
//    }

    fun updateContacts(newContacts: List<Contact>) {
        val diffCallback = FavoriteDiffCallback(items, newContacts)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items = newContacts.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
}

class FavoriteDiffCallback(
    private val oldList: List<Contact>,
    private val newList: List<Contact>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}