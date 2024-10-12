package com.fatherofapps.androidbase.data.models

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class ProductDetails(
    val responseCode: Int,
    val data: PromotionalPost,
    val message: String
)
