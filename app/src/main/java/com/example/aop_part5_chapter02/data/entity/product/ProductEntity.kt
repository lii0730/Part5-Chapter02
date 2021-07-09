package com.example.aop_part5_chapter02.data.entity.product

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("createdAt") val createdAt: Long,
    val product_name : String,
    val productPrice: Int,
    val productImage: String,
    val productType: String,
    val productIntroductionImage: String
)
