package com.lion.five.shopmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lion.five.shopmanager.databinding.ItemProductDetailImageBinding

class ProductDetailAdapter : RecyclerView.Adapter<ProductDetailAdapter.ViewHolder>() {
    private var imageList = listOf<Int>()

    inner class ViewHolder(private val binding: ItemProductDetailImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageRes: Int) {
            binding.ivProductDetailImage.setImageResource(imageRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductDetailImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount() = imageList.size

    fun submitList(list: List<Int>) {
        imageList = list
        notifyDataSetChanged()
    }
}