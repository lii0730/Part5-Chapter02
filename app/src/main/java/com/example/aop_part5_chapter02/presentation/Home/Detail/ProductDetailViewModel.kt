package com.example.aop_part5_chapter02.presentation.Home.Detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aop_part5_chapter02.data.entity.product.ProductEntity
import com.example.aop_part5_chapter02.domain.todo.GetProductEntityUseCase
import com.example.aop_part5_chapter02.domain.todo.OrderProductEntityUseCase
import com.example.aop_part5_chapter02.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ProductDetailViewModel(
	private val productId: Long,
	private val getProductEntityUseCase : GetProductEntityUseCase,
	private val orderProductEntityUseCase: OrderProductEntityUseCase
): BaseViewModel() {

	private var _productDetailStateLiveData = MutableLiveData<ProductDetailState>(ProductDetailState.Uninitialized)
	val productDetailStateLiveData : LiveData<ProductDetailState> = _productDetailStateLiveData

	private lateinit var productEntity: ProductEntity

	override fun fetchData(): Job = viewModelScope.launch {
		setState(ProductDetailState.Loading)


		getProductEntityUseCase(productId)?.let { product->
			productEntity = product
			setState(
				ProductDetailState.Success(product)
			)
		} ?: kotlin.run {
			setState(ProductDetailState.Error)
		}
	}

	fun orderProduct() = viewModelScope.launch {
		val productId = orderProductEntityUseCase(productEntity)
		if(productId == productEntity.id) {
			setState(ProductDetailState.Order)
		}
	}

	private fun setState(state: ProductDetailState) {
		_productDetailStateLiveData.postValue(state)
	}
}