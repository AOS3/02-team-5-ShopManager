package com.lion.five.shopmanager.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int = 0, // 기본값 0
    val name: String, // 상품명
    val price: Int, // 가격
    val type: String, // 상품 유형
    val description: String, // 상품 설명
    val images: List<Int>, // 이미지 리스트
    val stock: Int, // 재고 수량
    val reviewCount: Int, // 리뷰 수
    val isBest: Boolean = false, // 기본값 false, 베스트 상품은 true
    val createAt: Long = System.currentTimeMillis() // 생성 시간, 기본값 현재 시간
): Parcelable