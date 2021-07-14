package com.example.aop_part5_chapter02.presentation.MyPage

import android.app.Activity
import android.util.Log
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

	companion object {
		const val TAG = "FragmentMyPage"
	}

	private val adapter by lazy {
		ProductListAdapter(onProductItemClicked = {

		})
	}

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

	private val launcher =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
			if(it.resultCode == Activity.RESULT_OK) {
				val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
				try {
					task.getResult(ApiException::class.java)?.let { account ->
						Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
						viewModel.saveToken(account.idToken ?: throw Exception())
					}
				} catch (e: Exception) {
					handleErrorState()
					Log.e(TAG, e.toString())
				}
			}
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
		orderedProductRecyclerView.adapter = adapter
		loginButton.setOnClickListener {
			loginGoogle()
		}
		logoutButton.setOnClickListener {
			logoutGoogle()
		}
	}

	private fun loginGoogle() {
		val signInIntent = gsc.signInIntent
		launcher.launch(signInIntent)
	}

	private fun logoutGoogle() {
		firebaseAuth.signOut()
		viewModel.signOut()
		binding.profileGroup.isGone = true
		binding.loginViewGroup.isVisible = true
	}

	private fun handleLoadingState() = with(binding) {
		loginViewGroup.isGone = true
	}

	private fun handleLoginState(state: FragmentMyPageState.Login) {
		try {
			val credential = GoogleAuthProvider.getCredential(state.idToken, state.idToken)
			firebaseAuth.signInWithCredential(credential)
				.addOnCompleteListener(requireActivity()) {
					if(it.isSuccessful) {
						val user = firebaseAuth.currentUser
						viewModel.setUserInfo(user)
					} else {
						viewModel.setUserInfo(null)
					}
				}
		} catch (e: Exception) {
			Log.e(TAG, e.toString())
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
			Toast.makeText(context, "주문 리스트가 없습니다", Toast.LENGTH_SHORT).show()
		} else {
			adapter.submitList(state.productList)
		}
	}

	private fun handleErrorState() {
		Toast.makeText(context, "에러가 발생하였습니다.", Toast.LENGTH_SHORT).show()
	}
}