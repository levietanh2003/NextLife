package com.fatherofapps.androidbase.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostImage(
    val name: String,
    val type: String,
    val urlImagePost: String
)
