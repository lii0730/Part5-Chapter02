package com.example.aop_part5_chapter02.presentation.MyPage

import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aop_part5_chapter02.domain.todo.GetOrderedProductListUseCase
import com.example.aop_part5_chapter02.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class FragmentMyPageViewModel(
	private val getOrderedProductListUseCase: GetOrderedProductListUseCase,
	private val preferenceManager: PreferenceManager
): BaseViewModel() {

	private var _profileStateLiveData = MutableLiveData<ProfileState>(ProfileState.UnInitialized)
	val profileStateLiveData: LiveData<ProfileState> = _profileStateLiveData

	override fun fetchData(): Job = viewModelScope.launch {
		setState(ProfileState.Loading)

	}

	private fun setState(state: ProfileState) {
		_profileStateLiveData.postValue(state)
	}
}