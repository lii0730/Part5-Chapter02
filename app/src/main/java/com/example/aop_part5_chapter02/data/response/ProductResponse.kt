package com.example.aop_part5_chapter02.data.response

import com.example.aop_part5_chapter02.data.entity.product.ProductEntity
import java.util.*

data class ProductResponse(
    val id: String,
    val createdAt: Long,
    val product_name : String,
    val productPrice: String,
    val productImage: String,
    val productType: String,
    val productIntroductionImage: String
) {
    fun toEntity() : ProductEntity =
        ProductEntity(
            id = id.toLong(),
            createdAt = createdAt,
            product_name = product_name,
            productPrice = productPrice.toDouble().toInt(),
            productImage = productImage,
            productType = productType,
            productIntroductionImage = productIntroductionImage
        )
}
