package com.example.aop_part5_chapter02.domain.todo

import android.util.Log
import com.example.aop_part5_chapter02.data.db.ProductDao
import com.example.aop_part5_chapter02.data.entity.product.ProductEntity
import com.example.aop_part5_chapter02.data.repository.ProductRepository
import com.example.aop_part5_chapter02.domain.UseCase

internal class GetOrderedProductListUseCase(
	private val productDao: ProductDao
): UseCase {

	suspend operator fun invoke() : List<ProductEntity> {
		Log.d("product GetALL" ,productDao.getAll().toString())
		return productDao.getAll()
	}
}