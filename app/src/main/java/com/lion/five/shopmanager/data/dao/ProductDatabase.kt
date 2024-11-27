package com.lion.five.shopmanager.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lion.five.shopmanager.data.converter.Converters
import com.lion.five.shopmanager.data.vo.ProductVO

@Database(entities = [ProductVO::class], version = 1)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDAO(): ProductDAO

    companion object {
        private var instance: ProductDatabase? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    ProductDatabase::class.java,
                    "Product.db"
                ).build()
            }
    }
}
