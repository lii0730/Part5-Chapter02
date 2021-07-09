package com.example.aop_part5_chapter02.presentation.Home

import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aop_part5_chapter02.data.entity.product.ProductEntity
import com.example.aop_part5_chapter02.databinding.FragmentHomeBinding
import com.example.aop_part5_chapter02.presentation.BaseFragment
import com.example.aop_part5_chapter02.presentation.Home.Detail.ProductDetailActivity
import com.example.aop_part5_chapter02.presentation.adapter.ProductListAdapter
import org.koin.android.viewmodel.ext.android.viewModel

internal class FragmentHome : BaseFragment<FragmentHomeViewModel, FragmentHomeBinding>() {

	override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

	private val adapter = ProductListAdapter(
		onProductItemClicked = {
			handleItemClick(it)
		}
	)

	override val viewModel by viewModel<FragmentHomeViewModel>()

	private fun initViews() = with(binding) {
		productRecyclerView.adapter = adapter
		productRecyclerView.layoutManager = GridLayoutManager(context, 2)
		//TODO: adapter 로 List 전달
		//	List 는 APi를 통해 얻어옴.

		swipeRefreshLayout.setOnRefreshListener {
			viewModel.fetchData()
		}
	}

	override fun observeData() {
		//TODO 데이터 처리?!
		viewModel.productListStateLiveData.observe(this) {
			when(it) {
				is ProductListState.Uninitialized -> {
					initViews()
				}
				is ProductListState.Loading -> {
					handleLoadingState()
				}
				is ProductListState.Success -> {
					handleSuccessState(it)
				}
				is ProductListState.Error -> {
					handleErrorState()
				}
			}
		}
	}

	private fun handleItemClick(productItem: ProductEntity) {
		//TODO: 아이템을 선택했을 떄 ProductDetailActivity 로 전달?
		val intent = Intent(context, ProductDetailActivity::class.java)
		intent.putExtra(ProductDetailActivity.PRODUCT_ID_KEY, productItem.id)
		startActivity(intent)
	}

	private fun handleErrorState() {
		Toast.makeText(context, "에러가 발생하였습니다.", Toast.LENGTH_SHORT).show()
	}

	private fun handleSuccessState(state : ProductListState.Success) = with(binding) {

		if(state.productList.isNotEmpty()) {
			adapter.apply {
				submitList(state.productList)
			}
		}
		swipeRefreshLayout.isRefreshing = false
	}

	private fun handleLoadingState() = with(binding) {
		swipeRefreshLayout.isRefreshing = true
	}

}