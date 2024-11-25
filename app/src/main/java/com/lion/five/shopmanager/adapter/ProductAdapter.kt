package com.lion.five.shopmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.lion.five.shopmanager.data.model.Product
import com.lion.five.shopmanager.databinding.ItemProductBinding
import com.lion.five.shopmanager.utils.toDecimalFormat

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val items = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(products: List<Product>) {
        items.clear()
        items.addAll(products)
        notifyDataSetChanged()
    }

    class ProductViewHolder(
        private val binding: ItemProductBinding,
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                tvProductTitle.text = product.name
                ivProductThumbnail.setImageResource(product.images.first())
                tvProductIsBest.isVisible = product.isBest
                tvProductPrice.text = "${product.price.toDecimalFormat()}원"
                tvProductReviewCount.text = if (product.reviewCount == 0) "리뷰 없음" else "${product.reviewCount}"
                tvProductType.text = product.type
            }
        }

    }

}