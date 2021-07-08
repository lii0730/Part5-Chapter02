package com.example.aop_part5_chapter02.data.repository

import com.example.aop_part5_chapter02.data.db.ProductDao
import com.example.aop_part5_chapter02.data.entity.product.ProductEntity
import com.example.aop_part5_chapter02.data.network.ProductApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultProductRepository(
    val productApi: ProductApiService,
    val productDao: ProductDao,
    val ioDispatcher: CoroutineDispatcher
): ProductRepository {
    //TODO: API or Local 로부터 Data를 긁어옴

    override suspend fun getProductList(): List<ProductEntity> = withContext(ioDispatcher) {
        val response = productApi.getProducts()
        return@withContext if(response.isSuccessful) {
            response.body()?.items?.map {
                it.toEntity()
            }?: listOf()
        } else {
            listOf()
        }
    }

    override suspend fun getLocalProductList(): List<ProductEntity> = withContext(ioDispatcher) {
        productDao.getAll()
    }

    override suspend fun getProductEntity(id: Long): ProductEntity? = withContext(ioDispatcher) {
        val response = productApi.getProduct(id)
        return@withContext if(response.isSuccessful) {
            response.body()?.toEntity()
        } else {
            null
        }
    }

    override suspend fun insertProduct(productEntity: ProductEntity): Long = withContext(ioDispatcher) {
        productDao.insert(productEntity)
    }

    override suspend fun insertProductList(productList: List<ProductEntity>) = withContext(ioDispatcher) {
        productDao.insert(productList)
    }

    override suspend fun deleteAll() = withContext(ioDispatcher){
        productDao.deleteAll()
    }

    override suspend fun deleteProduct(id: Long) = withContext(ioDispatcher){
        productDao.delete(id)
    }

    override suspend fun updateProduct(productEntity: ProductEntity) = withContext(ioDispatcher){
        productDao.update(productEntity)
    }
}