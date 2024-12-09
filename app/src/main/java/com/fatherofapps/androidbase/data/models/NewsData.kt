package com.fatherofapps.androidbase.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsData(
    val id: String,
    val title: String,
    val description: String,
    val postImages: List<PostImage>
)
