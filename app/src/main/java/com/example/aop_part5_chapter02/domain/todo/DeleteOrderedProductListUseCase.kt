package com.example.aop_part5_chapter02.domain.todo

import com.example.aop_part5_chapter02.data.db.ProductDao
import com.example.aop_part5_chapter02.data.entity.product.ProductEntity
import com.example.aop_part5_chapter02.data.repository.ProductRepository
import com.example.aop_part5_chapter02.domain.UseCase

internal class DeleteOrderedProductListUseCase(
	private val productDao: ProductDao
): UseCase {
	suspend operator fun invoke()  {
		return productDao.deleteAll()
	}
}