package com.fatherofapps.androidbase.data.models.user
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResponse(
    val responseCode: Int,
    val data: ResponseNews,
    val message: String
)
