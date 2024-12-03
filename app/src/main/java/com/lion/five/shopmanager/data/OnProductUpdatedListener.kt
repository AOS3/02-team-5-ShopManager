package com.lion.five.shopmanager.data

import com.lion.five.shopmanager.data.model.Product

interface OnProductUpdatedListener {
    fun onProductUpdated(updatedProduct: Product)
}