package com.lion.five.shopmanager.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lion.five.shopmanager.databinding.ItemProductImageBinding
import com.lion.five.shopmanager.listener.OnDeleteClickListener

class ProductImageAdapter(
    private val listener: OnDeleteClickListener,
) : RecyclerView.Adapter<ProductImageAdapter.ViewHolder>() {

    private val items = mutableListOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) { position -> listener.onDelete(position) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newList: List<Uri>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemProductImageBinding,
        onDelete: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnProductImageDelete.setOnClickListener {
                onDelete(adapterPosition)
            }
        }

        fun bind(uri: Uri) {
            binding.ivProductImage.setImageURI(uri)
        }
    }
}