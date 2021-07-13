package com.example.aop_part5_chapter02.domain.todo

import com.example.aop_part5_chapter02.data.entity.product.ProductEntity
import com.example.aop_part5_chapter02.data.repository.ProductRepository
import com.example.aop_part5_chapter02.domain.UseCase


// 주문버튼 클릭 -> 데이터베이스에 저장
class OrderProductEntityUseCase(
	private val productRepository: ProductRepository
): UseCase {
	suspend operator fun invoke(productEntity: ProductEntity): Long {
		return productRepository.insertProduct(productEntity)
	}
}