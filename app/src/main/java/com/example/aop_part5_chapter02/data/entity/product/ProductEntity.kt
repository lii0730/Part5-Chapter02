package com.example.aop_part5_chapter02.data.entity.product

import java.util.*

data class ProductEntity(
    val id: Long,
    val createdAt: Date,
    val product_name : String,
    val productPrice: Int,
    val productImage: String,
    val productType: String,
    val productIntroductionImage: String
)
