package com.lion.five.shopmanager.data.repository

import android.content.Context
import com.lion.five.shopmanager.data.dao.ProductDatabase
import com.lion.five.shopmanager.data.model.ProductModel
import com.lion.five.shopmanager.data.vo.ProductVO

class ProductRepository {
    companion object
    // 상품 정보를 저장하는 메서드
    fun insertProductInfo(context: Context, productModel: ProductModel){
        // 데이터 베이스 객체를 가져온다.
        val productDatabase = ProductDatabase.getInstance(context)

        // ViewModel 에 있는 데이터를 VO에 담아준다.
        TODO()

    }

    // 상품 정보 전체를 가져오는 메서드
    fun selectProductInfoAll(context: Context) : MutableList<ProductModel>{
        // 데이터 베이스 객체를 가져온다.
        val productDatabase = ProductDatabase.getInstance(context)
        // DB에서 상품 전체 데이터를 가져온다.
        val productVoList = productDatabase?.productDAO()?.selectProductDataAll()
        // 상품 데이터를 담을 리스트
        val productModelList = mutableListOf<ProductModel>()

        // 상품 데이터를 추출하고 객체에 담는다.
        // productModelList에 객체를 추가하여
        // productModelList를 반환한다.
        TODO()
    }

    // 선택한 상품 하나의 정보를 가져오는 메서드
    fun selectProductInfoById(context: Context, id:Int) : ProductModel{
        // 데이터 베이스 객체를 가져온다.
        val productDatabase = ProductDatabase.getInstance(context)
        // DB에서 상품 하나의 정보를 가져온다.
        val productVO = productDatabase?.productDAO()?.selectProductDataById(id)

        // 객체에 데이터를 담고 객체를 반환한다.
        TODO()
    }

    // 상품 하나의 정보 삭제
    fun deleteProductById(context: Context, id:Int){
        // 데이터 베이스 객체를 가져온다
        val productDatabase = ProductDatabase.getInstance(context)
        // 삭제할 상품 번호를 담고 있는 객체를 생성한다.
        val productVO = ProductVO(id = id)
        // 삭제한다.
        productDatabase?.productDAO()?.deleteProductData(productVO)
    }

    // 상품 정보 수정
    fun updateProductInfo(context: Context, productModel: ProductModel){
        // 데이터 베이스 객체를 가져온다
        val productDatabase = ProductDatabase.getInstance(context)

        // VO에 객체를 담아주고 수정한다.
        TODO()
    }
}