package com.lion.five.shopmanager.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lion.five.shopmanager.databinding.ItemProductDetailImageBinding
import com.lion.five.shopmanager.utils.FileUtil
import com.lion.five.shopmanager.utils.loadImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailAdapter : RecyclerView.Adapter<ProductDetailAdapter.ViewHolder>() {
    private var imageList = listOf<String>()

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

    fun submitList(list: List<String>) {
        imageList = list
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemProductDetailImageBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(fileName: String) {
            with(binding) {
                ivProductDetailImage.tag = fileName
                CoroutineScope(Dispatchers.IO).launch {
                    val bitmap = FileUtil.loadImageFile(itemView.context, fileName)
                        .loadImage()

                    withContext(Dispatchers.Main) {
                        // 비동기 이미지 로딩 중 뷰가 재사용된 경우를 체크
                        if (ivProductDetailImage.tag == fileName) {
                            ivProductDetailImage.setImageBitmap(bitmap)
                        }
                    }
                }
            }
        }
    }
}