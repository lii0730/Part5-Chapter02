package com.example.aop_part5_chapter02.presentation.MyPage

import android.net.Uri
import com.example.aop_part5_chapter02.data.entity.product.ProductEntity

internal sealed class ProfileState {
	object UnInitialized: ProfileState()

	object Loading: ProfileState()

	data class Login(
		val idToken: String
	): ProfileState()

	sealed class Success: ProfileState() {
		data class Registered(
			val userName: String,
			val profileImgUrl: Uri?,
			val productList: List<ProductEntity> = listOf()
		): Success()

		object NotRegistered: Success()
	}

	object Error: ProfileState()
}
