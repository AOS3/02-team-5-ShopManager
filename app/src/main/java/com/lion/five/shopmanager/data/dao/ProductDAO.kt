package com.lion.five.shopmanager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.lion.five.shopmanager.data.vo.ProductVO

@Dao
interface ProductDAO {
    // 상품 정보 저장
    @Insert
    fun insertProductData(productVO: ProductVO)

    // 상품 정보를 가져오는 메서드
    @Query("SELECT * FROM ProductTable ORDER BY isBest DESC")
    fun selectProductDataAll(): List<ProductVO>

    // 상품 하나의 정보를 가져오는 메서드
    @Query("SELECT * FROM ProductTable WHERE id = :id")
    fun selectProductDataById(id: Int): ProductVO

    // 상품 하나의 정보를 삭제하는 메서드
    @Delete
    fun deleteProductData(productVO: ProductVO)

    // 상품 하나의 정보를 수정하는 메서드
    @Update
    fun updateProductData(productVO: ProductVO)

    // 이름으로 상품을 검색하는 메서드
    @Query("SELECT * FROM ProductTable WHERE name LIKE :productName ORDER BY id DESC")
    fun searchProductByName(productName: String): List<ProductVO>
}