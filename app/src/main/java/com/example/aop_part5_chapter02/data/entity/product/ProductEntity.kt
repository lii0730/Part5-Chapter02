package com.example.aop_part5_chapter02.data.entity.product

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("createdAt") val createdAt: Long,
    @SerializedName("product_name") val product_name : String,
    @SerializedName("product_price") val productPrice: Int,
    @SerializedName("product_image") val productImage: String,
    @SerializedName("product_type") val productType: String,
    @SerializedName("product_introduction_image") val productIntroductionImage: String
)
