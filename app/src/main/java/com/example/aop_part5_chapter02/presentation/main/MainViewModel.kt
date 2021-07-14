package com.example.aop_part5_chapter02.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aop_part5_chapter02.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class MainViewModel: BaseViewModel() {

	private var _mainStateLiveData = MutableLiveData<MainState>()
	val mainStateLiveData = _mainStateLiveData

	override fun fetchData(): Job = Job()

	fun refreshOrderList() = viewModelScope.launch {
		_mainStateLiveData.postValue(MainState.RefreshOrderList)
	}
}