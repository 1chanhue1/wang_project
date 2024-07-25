package com.chanhue.dps

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chanhue.dps.databinding.ItemPhotoBinding

class PhotoRecyclerAdapter(val items: MutableList<String>) : RecyclerView.Adapter<PhotoRecyclerAdapter.Holder>()  {
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }

        holder.productImg.setImageURI(Uri.parse(items[position]))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Holder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        val productImg = binding.itemImg
    }
}