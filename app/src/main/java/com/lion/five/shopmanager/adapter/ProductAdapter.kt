package com.lion.five.shopmanager.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.lion.five.shopmanager.data.model.Product
import com.lion.five.shopmanager.databinding.ItemProductBinding
import com.lion.five.shopmanager.listener.OnItemClickListener
import com.lion.five.shopmanager.utils.FileUtil
import com.lion.five.shopmanager.utils.toDecimalFormat

class ProductAdapter(
    private val listener: OnItemClickListener,
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val items = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) { position -> listener.onItemClick(items[position]) }
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
                itemView.post {
                    Thread {
                        val imageFile = FileUtil.loadImageFile(itemView.context, product.images.first())
                        val options = BitmapFactory.Options().apply { inSampleSize = 4 }
                        val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath, options)

                        itemView.post {
                            ivProductThumbnail.setImageBitmap(bitmap)
                        }
                    }.start()
                }

                tvProductIsBest.isVisible = product.isBest
                tvProductPrice.text = "${product.price.toDecimalFormat()}원"
                tvProductReviewCount.text = if (product.reviewCount == 0) "리뷰 없음" else "${product.reviewCount}"
                tvProductType.text = product.type
            }
        }
    }
}