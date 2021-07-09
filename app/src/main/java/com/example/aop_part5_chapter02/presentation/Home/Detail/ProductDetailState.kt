package com.example.aop_part5_chapter02.presentation.Home.Detail

import com.example.aop_part5_chapter02.data.entity.product.ProductEntity

internal sealed class ProductDetailState {

	object Uninitialized : ProductDetailState()

	object Loading : ProductDetailState()

	data class Success(
		val product: ProductEntity
	) : ProductDetailState()

	object Error : ProductDetailState()

	object Order : ProductDetailState()

}
