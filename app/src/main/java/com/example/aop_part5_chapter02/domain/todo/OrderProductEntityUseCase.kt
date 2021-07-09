package com.example.aop_part5_chapter02.domain.todo

import com.example.aop_part5_chapter02.data.entity.product.ProductEntity
import com.example.aop_part5_chapter02.data.repository.ProductRepository
import com.example.aop_part5_chapter02.domain.UseCase

class OrderProductEntityUseCase(
	private val productRepository: ProductRepository
): UseCase {
	suspend operator fun invoke(productEntity: ProductEntity): Long {
		return productRepository.insertProduct(productEntity)
	}
}