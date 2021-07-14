package com.example.aop_part5_chapter02.presentation.Home

import com.example.aop_part5_chapter02.data.entity.product.ProductEntity

internal sealed class FragmentHomeState {
	object Uninitialized : FragmentHomeState()

	object Loading : FragmentHomeState()

	data class Success(
		val productList: List<ProductEntity>
	) : FragmentHomeState()

	object Error : FragmentHomeState()
}