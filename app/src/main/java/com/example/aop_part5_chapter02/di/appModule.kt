package com.example.aop_part5_chapter02.di

import com.example.aop_part5_chapter02.data.db.provideDB
import com.example.aop_part5_chapter02.data.db.provideDao
import com.example.aop_part5_chapter02.data.network.provideGsonConverterFactory
import com.example.aop_part5_chapter02.data.network.provideOkHttpClient
import com.example.aop_part5_chapter02.data.network.provideProductApiService
import com.example.aop_part5_chapter02.data.network.provideProductRetrofit
import com.example.aop_part5_chapter02.data.preference.PreferenceManager
import com.example.aop_part5_chapter02.data.repository.DefaultProductRepository
import com.example.aop_part5_chapter02.data.repository.ProductRepository
import com.example.aop_part5_chapter02.domain.todo.*
import com.example.aop_part5_chapter02.domain.todo.DeleteOrderedProductListUseCase
import com.example.aop_part5_chapter02.domain.todo.GetOrderedProductListUseCase
import com.example.aop_part5_chapter02.domain.todo.GetProductEntityUseCase
import com.example.aop_part5_chapter02.domain.todo.GetProductListUseCase
import com.example.aop_part5_chapter02.presentation.Home.Detail.ProductDetailViewModel
import com.example.aop_part5_chapter02.presentation.Home.FragmentHomeViewModel
import com.example.aop_part5_chapter02.presentation.MyPage.FragmentMyPageViewModel
import com.example.aop_part5_chapter02.presentation.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

//TODO: 필요 객체 주입

val appModule = module {

	// viewModel
	viewModel {
		FragmentHomeViewModel(get())
	}

	viewModel {
		(productId: Long) -> ProductDetailViewModel(productId, get(), get())
	}

	viewModel {
		FragmentMyPageViewModel(get(), get(), get())
	}

	viewModel {
		MainViewModel()
	}

	// Database
	single { provideDB(androidApplication()) }
	single { provideDao(get()) }

	// Coroutine
	single { Dispatchers.IO }
	single { Dispatchers.Main }

	// NetWork
	single { provideGsonConverterFactory() }
	single { provideOkHttpClient() }
	single { provideProductRetrofit(get(), get()) }
	single { provideProductApiService(get()) }

	// Repository
	single<ProductRepository> { DefaultProductRepository(get(), get(), get()) }

	// UseCase
	single { GetProductListUseCase(get()) }
	single { GetOrderedProductListUseCase(get()) }
	single { GetProductEntityUseCase(get()) }
	single { OrderProductEntityUseCase(get()) }
	single { DeleteOrderedProductListUseCase(get()) }

	// Preference Manager
	single { PreferenceManager(androidContext()) }
}