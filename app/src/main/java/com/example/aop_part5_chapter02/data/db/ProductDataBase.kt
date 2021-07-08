package com.example.aop_part5_chapter02.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.aop_part5_chapter02.data.entity.product.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductDataBase : RoomDatabase(){
	abstract fun productDao() : ProductDao

	companion object {
		const val DB_NAME = "ProductDataBase.db"
	}
}