package com.example.aop_part5_chapter02.presentation.MyPage

import android.app.Activity
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.aop_part5_chapter02.R
import com.example.aop_part5_chapter02.databinding.FragmentMyBinding
import com.example.aop_part5_chapter02.presentation.BaseFragment
import com.example.aop_part5_chapter02.presentation.Home.FragmentHome
import com.example.aop_part5_chapter02.presentation.adapter.ProductListAdapter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.android.viewmodel.ext.android.viewModel

internal class FragmentMyPage: BaseFragment<FragmentMyPageViewModel, FragmentMyBinding>() {

	private val adapter = ProductListAdapter(onProductItemClicked = {
		FragmentHome().handleItemClick(it)
	})

	private val firebaseAuth: FirebaseAuth by lazy {
		FirebaseAuth.getInstance()
	}

	private val gso: GoogleSignInOptions by lazy {
		GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestIdToken(getString(R.string.google_client_id))
			.requestEmail()
			.build()
	}

	private val gsc by lazy { GoogleSignIn.getClient(requireActivity(), gso) }

	private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
		if(it.resultCode == Activity.RESULT_OK) {
			val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
			try {
				task.getResult(ApiException::class.java)?.let { account ->
					viewModel.saveToken(account.idToken ?: throw Exception())
				}
			} catch (e: Exception) {
				handleErrorState()
			}
		}
	}

	companion object {
		const val TAG = "FragmentMyPage"
	}

	override val viewModel: FragmentMyPageViewModel by viewModel()

	override fun getViewBinding(): FragmentMyBinding = FragmentMyBinding.inflate(layoutInflater)

	override fun observeData() {
		viewModel.profileStateLiveData.observe(this) {
			when(it) {
				is FragmentMyPageState.UnInitialized -> {
					initViews()
				}
				is FragmentMyPageState.Loading -> {
					handleLoadingState()
				}
				is FragmentMyPageState.Login -> {
					handleLoginState(it)
				}
				is FragmentMyPageState.Success -> {
					handleSuccessState(it)
				}
				is FragmentMyPageState.Error -> {
					handleErrorState()
				}
			}
		}
	}

	private fun initViews() = with(binding) {
		loginButton.setOnClickListener {
			Toast.makeText(context, "구글 로그인을 시도합니다.", Toast.LENGTH_SHORT).show()
			loginGoogle()
		}
	}

	private fun loginGoogle() {
		val signInIntent = gsc.signInIntent
		launcher.launch(signInIntent)
	}

	private fun handleLoadingState() = with(binding) {
		progressBar.isVisible = true
		loginViewGroup.isGone = true
	}

	private fun handleLoginState(state: FragmentMyPageState.Login) {
		binding.progressBar.isVisible = true

		val credential = GoogleAuthProvider.getCredential(state.idToken, null)
		firebaseAuth.signInWithCredential(credential)
			.addOnCompleteListener(requireActivity()) {
				if(it.isSuccessful) {
					val user = firebaseAuth.currentUser
					viewModel.setUserInfo(user)
				} else {
					viewModel.setUserInfo(null)
				}
			}
	}

	private fun handleSuccessState(state: FragmentMyPageState.Success) {
		when(state) {
			is FragmentMyPageState.Success.Registered -> {
				handleRegisterState(state)
			}
			is FragmentMyPageState.Success.NotRegistered -> {
				adapter.submitList(null)
			}
		}
	}

	private fun handleRegisterState(state: FragmentMyPageState.Success.Registered) =with(binding) {
		profileGroup.isVisible = true
		loginViewGroup.isGone = true
		progressBar.isGone = true

		Glide.with(binding.root)
			.load(state.profileImgUrl)
			.circleCrop()
			.into(profileImageView)

		profileNameTextView.text = state.userName

		if(state.productList.isEmpty()) {

		} else {
			adapter.submitList(state.productList)
		}
	}

	private fun handleErrorState() {
		Toast.makeText(context, "에러가 발생하였습니다.", Toast.LENGTH_SHORT).show()
	}
}