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
            listOf(
                R.drawable.ghibli_1,
                R.drawable.ghibli_2,
                R.drawable.ghibli_3,
                R.drawable.ghibli_4,
                R.drawable.ghibli_5,
                R.drawable.ghibli_6,
                R.drawable.ghibli_7,
                R.drawable.ghibli_8,
                R.drawable.ghibli_9,
                R.drawable.ghibli_10,
                R.drawable.ghibli_11,
                R.drawable.ghibli_12,
                R.drawable.marvel_1,
                R.drawable.marvel_2,
                R.drawable.marvel_3,
                R.drawable.marvel_4,
                R.drawable.marvel_5,
                R.drawable.marvel_6,
                R.drawable.marvel_7,
                R.drawable.marvel_8,
                R.drawable.marvel_9,
                R.drawable.marvel_10,
                R.drawable.marvel_11,
                R.drawable.pixar_1,
                R.drawable.pixar_2,
                R.drawable.pixar_3,
                R.drawable.pixar_4,
                R.drawable.pixar_5,
                R.drawable.pixar_6,
                R.drawable.pixar_7,
                R.drawable.pixar_8,
                R.drawable.pixar_9,
                R.drawable.disney_1,
                R.drawable.disney_2,
                R.drawable.disney_3,
                R.drawable.disney_4,
                R.drawable.disney_5,
                R.drawable.disney_6,
                R.drawable.disney_7,
                R.drawable.disney_8,
                R.drawable.disney_9,
                R.drawable.disney_10,
                R.drawable.harrypotter_1,
                R.drawable.harrypotter_2,
                R.drawable.harrypotter_3,
                R.drawable.harrypotter_4,
                R.drawable.harrypotter_5,
                R.drawable.harrypotter_6,
                R.drawable.harrypotter_7,
                R.drawable.harrypotter_8,
                R.drawable.harrypotter_9,
                R.drawable.harrypotter_10,
                R.drawable.teenieping_1,
                R.drawable.teenieping_2,
                R.drawable.teenieping_3,
                R.drawable.teenieping_4,
                R.drawable.teenieping_5
            ).forEach { drawableId ->
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
                        movieName = product.movieName
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