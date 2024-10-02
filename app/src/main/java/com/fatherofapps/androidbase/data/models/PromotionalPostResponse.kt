package com.fatherofapps.androidbase.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PromotionalPostResponse(
    val responseCode: Int,
    val message: String,
    val data: PostData
)
