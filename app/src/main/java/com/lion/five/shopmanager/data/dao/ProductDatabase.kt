package com.lion.five.shopmanager.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lion.five.shopmanager.R
import com.lion.five.shopmanager.data.Storage
import com.lion.five.shopmanager.data.converter.Converters
import com.lion.five.shopmanager.data.vo.ProductVO
import com.lion.five.shopmanager.utils.FileUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Database(entities = [ProductVO::class], version = 1)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDAO(): ProductDAO

    companion object {
        private var instance: ProductDatabase? = null

        private fun initializeData(context: Context, database: ProductDatabase) {
            // 이미지 저장
            Storage.movieImages.forEach { drawableId ->
                FileUtil.saveImageFromDrawable(context, drawableId)
            }

            // 초기 데이터 삽입
            database.productDAO().let { dao ->
                Storage.products.forEach { product ->
                    val productVO = ProductVO(
                        name = product.name,
                        price = product.price,
                        type = product.type,
                        description = product.description,
                        images = product.images,
                        stock = product.stock,
                        reviewCount = product.reviewCount,
                        isBest = product.isBest,
                    )
                    dao.insertProductData(productVO)
                }
            }
        }

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "Product.db"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            instance?.let { database ->
                                initializeData(context.applicationContext, database)
                            }
                        }
                    }
                }).build().also { instance = it }
            }
    }
}