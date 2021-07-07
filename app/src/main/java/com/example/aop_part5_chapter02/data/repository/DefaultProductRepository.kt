package com.example.aop_part5_chapter02.data.repository

import com.example.aop_part5_chapter02.data.network.ProductApiService

class DefaultProductRepository(
    val productApi: ProductApiService,

): ProductRepository {

}