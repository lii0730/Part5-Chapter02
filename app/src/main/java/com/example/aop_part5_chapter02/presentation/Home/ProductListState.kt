package com.example.aop_part5_chapter02.presentation.Home

import com.example.aop_part5_chapter02.data.entity.product.ProductEntity

internal sealed class ProductListState {
	object Uninitialized : ProductListState()

	object Loading : ProductListState()

	data class Success(
		val productList: List<ProductEntity>
	) : ProductListState()

	object Error : ProductListState()
}