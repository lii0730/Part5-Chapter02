package com.example.aop_part5_chapter02.presentation.MyPage

import android.net.Uri
import com.example.aop_part5_chapter02.data.entity.product.ProductEntity

internal sealed class FragmentMyPageState {
	object UnInitialized: FragmentMyPageState()

	object Loading: FragmentMyPageState()

	data class Login(
		val idToken: String
	): FragmentMyPageState()

	sealed class Success: FragmentMyPageState() {
		data class Registered(
			val userName: String,
			val profileImgUrl: Uri?,
			var productList: List<ProductEntity> = listOf()
		): Success()

		object NotRegistered: Success()
	}

	object Error: FragmentMyPageState()
}
