package com.example.aop_part5_chapter02.presentation.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aop_part5_chapter02.domain.todo.GetProductListUseCase
import com.example.aop_part5_chapter02.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class FragmentHomeViewModel(
	val getProductListUseCase: GetProductListUseCase
): BaseViewModel() {

	// LiveData Observe
	private var _productListStateLiveData = MutableLiveData<ProductListState>(ProductListState.Uninitialized)

	val productListStateLiveData : LiveData<ProductListState> = _productListStateLiveData

	override fun fetchData(): Job = viewModelScope.launch {
		_productListStateLiveData.postValue(ProductListState.Loading)
		_productListStateLiveData.postValue(ProductListState.Success(getProductListUseCase()))
	}

}