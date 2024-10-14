package com.fatherofapps.androidbase.data.models

data class Carousel(
    val `data`: List<PostImage>,
    val message: String,
    val responseCode: Int
)