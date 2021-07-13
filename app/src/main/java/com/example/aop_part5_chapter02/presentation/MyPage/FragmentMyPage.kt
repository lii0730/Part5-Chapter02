package com.example.aop_part5_chapter02.presentation.MyPage

import com.example.aop_part5_chapter02.databinding.FragmentMyBinding
import com.example.aop_part5_chapter02.presentation.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

internal class FragmentMyPage: BaseFragment<FragmentMyPageViewModel, FragmentMyBinding>() {
	override val viewModel: FragmentMyPageViewModel by viewModel()

	override fun getViewBinding(): FragmentMyBinding = FragmentMyBinding.inflate(layoutInflater)

	override fun observeData() {

	}
}