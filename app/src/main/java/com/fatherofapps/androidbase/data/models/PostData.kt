package com.fatherofapps.androidbase.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostData(
    val currentPage: Int?,
    val totalPages: Int?,
    val pageSize: Int?,
    val totalElements: Int?,
    val data: List<PromotionalPost>
)

