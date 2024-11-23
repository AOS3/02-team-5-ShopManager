package com.lion.five.shopmanager.data.model


data class ProductModel (
    var id:Int,
    var name:String,
    var price:Int,
    var type:String,
    var description:String,
    var images:List<String>,
    var stock:Int,
    var reviewCount:Int,
    // 기본값 false, 베스트 상품에만 객체 생성 시 true
    var isBest:Boolean,
    var createAt:Long
)