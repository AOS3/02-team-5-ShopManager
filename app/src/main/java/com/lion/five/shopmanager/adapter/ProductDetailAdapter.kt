package com.lion.five.shopmanager.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lion.five.shopmanager.databinding.ItemProductDetailImageBinding
import com.lion.five.shopmanager.utils.FileUtil

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
            itemView.post {
                Thread {
                    val imageFile = FileUtil.loadImageFile(itemView.context, fileName)
                    val options = BitmapFactory.Options().apply { inSampleSize = 4 }
                    val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath, options)

                    itemView.post {
                        binding.ivProductDetailImage.setImageBitmap(bitmap)
                    }
                }.start()
            }
        }
    }
}