package com.lion.five.shopmanager.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lion.five.shopmanager.data.vo.ProductVO

@Database(entities = [ProductVO::class], version = 1, exportSchema = true)
abstract class ProductDatabase : RoomDatabase(){
    abstract fun productDAO() : ProductDAO

    companion object{
        var productDatabase:ProductDatabase? = null
        @Synchronized
        fun getInstance(context: Context) : ProductDatabase?{
            synchronized(ProductDatabase::class){
                productDatabase = Room.databaseBuilder(
                    context.applicationContext, ProductDatabase::class.java,
                    "Product.db"
                ).build()
            }
            return productDatabase
        }
        fun destroyInstance(){
            productDatabase = null
        }
    }
}