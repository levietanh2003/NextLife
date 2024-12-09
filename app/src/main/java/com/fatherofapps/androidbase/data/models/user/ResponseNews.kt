package com.fatherofapps.androidbase.data.models.user

import com.fatherofapps.androidbase.data.models.NewsData
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseNews(    val currentPage: Int,
                            val totalPages: Int,
                            val pageSize: Int,
                            val totalElements: Int,
                            val data: List<NewsData>)
