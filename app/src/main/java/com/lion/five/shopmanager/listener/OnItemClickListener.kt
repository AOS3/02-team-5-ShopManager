package com.lion.five.shopmanager.listener

import com.lion.five.shopmanager.data.model.Product

interface OnItemClickListener {
    fun onItemClick(product: Product)
}