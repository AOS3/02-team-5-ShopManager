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
    fun insertProductData(productDAO: ProductDAO)

    // 상품 정보를 가져오는 메서드
    @Query("""
        select * from ProductTable 
        order by id desc """)
    fun selectProductDataAll(): List<ProductVO>

    // 상품 하나의 정보를 가져오는 메서드
    @Query("""
        select * from ProductTable
        where id = :id
    """)
    fun selectProductDataById(id:Int) : ProductVO


    // 상품 하나의 정보를 삭제하는 메서드
    @Delete
    fun deleteProductData(productVO: ProductVO)

    // 상품 하나의 정보를 수정하는 메서드
    @Update
    fun updateProductData(productVO: ProductVO)

    // 이름으로 상품을 검색하는 메서드
    // LIKE 연산자 특정 문자가 포함되어 있는 값을 조회
    @Query("""
        select * from ProductTable
        where name like :productName
        order by id desc""")
    fun searchProductByName(productName: String) : List<ProductVO>
}