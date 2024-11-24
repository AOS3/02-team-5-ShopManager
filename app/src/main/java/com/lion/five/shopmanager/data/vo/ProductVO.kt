package com.lion.five.shopmanager.data.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProductTable")
data class ProductVO (
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var name:String = "",
    var price:Int = 0,
    var type:String = "",
    var description:String = "",
    var images:List<String> = listOf(),
    var stock:Int = 0,
    var reviewCount:Int = 0,
    // 기본값 false, 베스트 상품에만 객체 생성 시 true
    var isBest:Boolean = false,
    var createAt:Long = System.currentTimeMillis()
)