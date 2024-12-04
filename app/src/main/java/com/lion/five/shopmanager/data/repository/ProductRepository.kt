package com.lion.five.shopmanager.data.repository

import android.content.Context
import com.lion.five.shopmanager.data.MovieName
import com.lion.five.shopmanager.data.dao.ProductDatabase
import com.lion.five.shopmanager.data.model.Product
import com.lion.five.shopmanager.data.vo.ProductVO

object ProductRepository {
    // 상품 정보를 저장하는 메서드
    fun insertProductInfo(context: Context, productModel: Product) {
        val productDatabase = ProductDatabase.getInstance(context)

        val productVO = ProductVO(
            name = productModel.name,
            price = productModel.price,
            type = productModel.type,
            description = productModel.description,
            images = productModel.images,
            stock = productModel.stock,
            reviewCount = productModel.reviewCount,
            isBest = productModel.isBest,
            movieName = productModel.movieName
        )

        productDatabase.productDAO().insertProductData(productVO)
    }

    // 상품 정보 전체를 가져오는 메서드
    fun selectProductInfoAll(context: Context): List<Product> {
        val productDatabase = ProductDatabase.getInstance(context)
        val voList: List<ProductVO> = productDatabase.productDAO().selectProductDataAll()

        return voList.map { vo ->
            Product(
                id = vo.id,
                name = vo.name,
                price = vo.price,
                type = vo.type,
                description = vo.description,
                images = vo.images,
                stock = vo.stock,
                reviewCount = vo.reviewCount,
                isBest = vo.isBest,
                createAt = vo.createAt,
                movieName = vo.movieName
            )
        }
    }

    // 아이디로 상품을 검색하는 메서드
    // 선택한 상품 하나의 정보를 가져오는 메서드
    fun searchProductById(context: Context, id: Int): Product {
        // 데이터베이스 객체를 가져온다.
        val productDatabase = ProductDatabase.getInstance(context)

        // productDAO 객체를 가져온다.
        val dao = productDatabase.productDAO()

        // DB에서 상품 하나의 정보를 가져온다.
        val productVO = dao.selectProductDataById(id)

        // 상품 정보가 없으면 null을 반환
        return productVO.let {
            Product(
                id = it.id,
                name = it.name,
                description = it.description,
                price = it.price,
                type = it.type,
                images = it.images,
                stock = it.stock,
                reviewCount = it.reviewCount,
                isBest = it.isBest,
                createAt = it.createAt,
                movieName = it.movieName
            )
        }
    }

    // 상품 하나의 정보 삭제
    fun deleteProductById(context: Context, id:Int){
        // 데이터 베이스 객체를 가져온다
        val productDatabase = ProductDatabase.getInstance(context)
        // 삭제할 상품 번호를 담고 있는 객체를 생성한다.
        val productVO = ProductVO(id = id)
        // 상품 하나를 삭제한다.
        productDatabase.productDAO().deleteProductData(productVO)
    }

    // 상품 정보 수정
    fun updateProductInfo(context: Context, productModel: Product) {
        val productDatabase = ProductDatabase.getInstance(context)

        val productVO = ProductVO(
            id = productModel.id,
            name = productModel.name,
            price = productModel.price,
            type = productModel.type,
            description = productModel.description,
            images = productModel.images,
            stock = productModel.stock,
            reviewCount = productModel.reviewCount,
            isBest = productModel.isBest,
            movieName = productModel.movieName
        )

        productDatabase.productDAO().updateProductData(productVO)
    }

    // 상품 정보 검색
    fun searchProductByName(context: Context, productName: String): MutableList<Product> {
        // 데이터베이스 객체를 가져온다.
        val productDatabase = ProductDatabase.getInstance(context)
        // 공백으로 단어 분리
        val keywords = productName.trim().split("\\s+".toRegex())

        // 검색 쿼리로 첫 번째 키워드만 검색
        val productVoList = productDatabase?.productDAO()?.searchProductByName("%${keywords.first()}%")
        val productModelList = mutableListOf<Product>()

        productVoList?.forEach { vo ->
            // 모든 키워드가 포함되어 있는 상품만 필터링
            if (keywords.all { keyword -> vo.name.contains(keyword, ignoreCase = true) }) {
                val productModel = Product(
                    id = vo.id,
                    name = vo.name,
                    price = vo.price,
                    type = vo.type,
                    description = vo.description,
                    images = vo.images,
                    stock = vo.stock,
                    reviewCount = vo.reviewCount,
                    isBest = vo.isBest,
                    createAt = vo.createAt,
                    movieName = vo.movieName
                )
                productModelList.add(productModel)
            }
        }

        return productModelList
    }

    // 인기상품을 가져오는 메서드
    fun getBestProducts(context: Context, limit: Int): List<Product> {
        // Room DB에서 isBest가 true인 상품을 가져옵니다.
        val productDao = ProductDatabase.getInstance(context).productDAO()
        val voList = productDao.selectBestProducts(limit)
        return voList.map { vo ->
            Product(
                id = vo.id,
                name = vo.name,
                price = vo.price,
                type = vo.type,
                description = vo.description,
                images = vo.images,
                stock = vo.stock,
                reviewCount = vo.reviewCount,
                isBest = vo.isBest,
                createAt = vo.createAt,
                movieName = vo.movieName
            )
        }
    }
}