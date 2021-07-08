package com.example.aop_part5_chapter02.data.db

import android.content.Context
import androidx.room.Room
import com.example.aop_part5_chapter02.data.db.ProductDataBase.Companion.DB_NAME

internal fun provideDB(context: Context): ProductDataBase =
	Room.databaseBuilder(
		context,
		ProductDataBase::class.java,
		DB_NAME
	).build()

internal fun provideDao(dataBase: ProductDataBase) = dataBase.productDao()