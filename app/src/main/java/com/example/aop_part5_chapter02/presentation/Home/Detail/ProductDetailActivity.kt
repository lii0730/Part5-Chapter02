package com.example.aop_part5_chapter02.presentation.Home.Detail

import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.aop_part5_chapter02.databinding.ActivityProductDetailBinding
import com.example.aop_part5_chapter02.presentation.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class ProductDetailActivity : BaseActivity<ProductDetailViewModel, ActivityProductDetailBinding>() {

	companion object {
		const val PRODUCT_ID_KEY = "PRODUCT_ID_KEY"
	}

	override val viewModel: ProductDetailViewModel by viewModel {
		parametersOf(
			intent.getLongExtra(PRODUCT_ID_KEY, -1)
		)
	}

	override fun getViewBinding(): ActivityProductDetailBinding = ActivityProductDetailBinding.inflate(layoutInflater)

	override fun observeData() {
		viewModel.productDetailStateLiveData.observe(this) {
			when(it) {
				is ProductDetailState.Uninitialized -> initViews()
				is ProductDetailState.Loading -> handleLoadingState()
				is ProductDetailState.Success -> handleSuccessState(it)
				is ProductDetailState.Order -> handleOrderState()
				is ProductDetailState.Error -> handleErrorState()
			}
		}
	}
	
	private fun initViews() = with(binding) {
		setSupportActionBar(toolBar)
		actionBar?.setDisplayHomeAsUpEnabled(true)
		actionBar?.setDisplayShowHomeEnabled(true)
		title = ""
		toolBar.setNavigationOnClickListener {
			finish()
		}
		
		orderButton.setOnClickListener { 
			viewModel.orderProduct()
			finish()
		}
	}
	
	private fun handleLoadingState() {
		
	}
	
	private fun handleSuccessState(state: ProductDetailState.Success) = with(binding) {
		state.product.let {
			title = it.product_name
			productCategoryTextView.text = it.productType
			productPriceTextView.text = "${it.productPrice}원"
			Glide.with(binding.root)
				.load(it.productImage)
				.into(productImageView)

			Glide.with(binding.root)
				.load(it.productIntroductionImage)
				.into(productIntroductionImageView)
		}
	}
	
	private fun handleOrderState() {
		Toast.makeText(this, "주문이 완료되었습니다!", Toast.LENGTH_SHORT).show()
	}
	
	private fun handleErrorState() {
		Toast.makeText(this, "에러가 발생하였습니다!", Toast.LENGTH_SHORT).show()
	}
}