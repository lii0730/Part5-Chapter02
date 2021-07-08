package com.example.aop_part5_chapter02.data.repository

import com.example.aop_part5_chapter02.data.entity.product.ProductEntity

interface ProductRepository {

	suspend fun getProductList() : List<ProductEntity>

	suspend fun getLocalProductList() : List<ProductEntity>

	suspend fun getProductEntity(id: Long) : ProductEntity?

	suspend fun insertProduct(productEntity: ProductEntity) : Long

	suspend fun insertProductList(productList: List<ProductEntity>)

	suspend fun deleteAll()

	suspend fun deleteProduct(id: Long)

	suspend fun updateProduct(productEntity: ProductEntity)
}