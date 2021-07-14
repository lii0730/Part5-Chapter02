package com.example.aop_part5_chapter02.presentation.MyPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aop_part5_chapter02.data.preference.PreferenceManager
import com.example.aop_part5_chapter02.domain.todo.DeleteOrderedProductListUseCase
import com.example.aop_part5_chapter02.domain.todo.GetOrderedProductListUseCase
import com.example.aop_part5_chapter02.presentation.BaseViewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class FragmentMyPageViewModel(
	private val getOrderedProductListUseCase: GetOrderedProductListUseCase,
	private val deleteOrderedProductListUseCase: DeleteOrderedProductListUseCase,
	private val preferenceManager: PreferenceManager
): BaseViewModel() {

	private var _profileStateLiveData = MutableLiveData<FragmentMyPageState>(FragmentMyPageState.UnInitialized)
	val profileStateLiveData: LiveData<FragmentMyPageState> = _profileStateLiveData

	override fun fetchData(): Job = viewModelScope.launch {
		setState(FragmentMyPageState.Loading)

		//TODO: token을 제대로 못가져옴
		preferenceManager.getIdToken()?.let {
			setState(FragmentMyPageState.Login(it))
		} ?: kotlin.run {
			setState(FragmentMyPageState.Success.NotRegistered)
		}
	}

	fun setUserInfo(user: FirebaseUser?)= viewModelScope.launch {
		user?.let {
			setState(FragmentMyPageState.Success.Registered(
				it.displayName ?: "익명",
				it.photoUrl,
				getOrderedProductListUseCase()
			))
		} ?: kotlin.run {
			setState(FragmentMyPageState.Success.NotRegistered)
		}
	}

	fun saveToken(token: String) {
		preferenceManager.setIdToken(token)
		fetchData()
	}

	fun signOut() = viewModelScope.launch {
		preferenceManager.removeToken()
		deleteOrderedProductListUseCase()
		fetchData()
	}

	private fun setState(state: FragmentMyPageState) {
		_profileStateLiveData.postValue(state)
	}
}