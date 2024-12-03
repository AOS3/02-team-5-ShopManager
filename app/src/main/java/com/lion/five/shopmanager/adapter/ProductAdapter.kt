package com.lion.five.shopmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lion.five.shopmanager.data.model.Product
import com.lion.five.shopmanager.databinding.ItemProductBinding
import com.lion.five.shopmanager.listener.OnItemClickListener
import com.lion.five.shopmanager.utils.FileUtil
import com.lion.five.shopmanager.utils.loadImage
import com.lion.five.shopmanager.utils.toDecimalFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductAdapter(
    private val listener: OnItemClickListener,
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) { position -> listener.onItemClick(getItem(position)) }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        onItemClick: (position: Int) -> Unit,
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        fun bind(product: Product) {
            with(binding) {
                tvProductTitle.text = product.name
                tvProductIsBest.isVisible = product.isBest
                tvProductPrice.text = "${product.price.toDecimalFormat()}원"
                tvProductReviewCount.text = if (product.reviewCount == 0) "리뷰 없음" else "${product.reviewCount}"
                tvProductType.text = product.type

                // 이미지 로딩 전에 태그 설정
                ivProductThumbnail.tag = product.images.first()
                CoroutineScope(Dispatchers.IO).launch {
                    val bitmap = FileUtil.loadImageFile(itemView.context, product.images.first())
                        .loadImage()

                    withContext(Dispatchers.Main) {
                        // 비동기 이미지 로딩 중 뷰가 재사용된 경우를 체크
                        if (ivProductThumbnail.tag == product.images.first()) {
                            ivProductThumbnail.setImageBitmap(bitmap)
                        }
                    }
                }
            }
        }
    }
}

class ProductDiffCallback: DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem

    }
}