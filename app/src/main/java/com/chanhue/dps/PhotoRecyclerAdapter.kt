package com.chanhue.dps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chanhue.dps.databinding.ItemAddBtnBinding
import com.chanhue.dps.databinding.ItemPhotoBinding

class PhotoRecyclerAdapter(val items: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_ADD_BUTTON = 0
        private const val TYPE_IMAGE = 1
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
        fun onLongClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_ADD_BUTTON else TYPE_IMAGE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ADD_BUTTON) {
            val binding = ItemAddBtnBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AddButtonViewHolder(binding)
        } else {
            val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ImageViewHolder(binding, itemClick)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AddButtonViewHolder -> holder.bind()
            is ImageViewHolder -> holder.bind(items[position - 1])
        }

        // Setting click listener
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }

        // Setting long click listener
        holder.itemView.setOnLongClickListener {
            itemClick?.onLongClick(it, position)
            true // Return true to indicate that the long click event has been handled
        }
    }

    override fun getItemCount(): Int = items.size + 1

    fun addItem(imageUri: String) {
        items.add(imageUri)
        notifyItemInserted(items.size) // Notify item inserted
    }

    fun removeItem(position: Int) {
        if (position > 0 && position < items.size + 1) { // Adjust position for header
            items.removeAt(position - 1) // Adjust position due to header
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size) // Notify that items may have shifted
        }
    }

    inner class AddButtonViewHolder(private val binding: ItemAddBtnBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.itemAddAPhotoBtn.setOnClickListener {
                itemClick?.onClick(it, adapterPosition)
            }
        }
    }

    class ImageViewHolder(
        private val binding: ItemPhotoBinding,
        private val itemClick: ItemClick?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            Glide.with(binding.itemImg)
                .load(item)
                .into(binding.itemImg)

            // Click listener
            binding.itemImg.setOnClickListener {
                itemClick?.onClick(it, adapterPosition + 1) // +1 for header
            }
        }
    }
}
