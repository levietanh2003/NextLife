package com.fatherofapps.androidbase.data.models.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogOutResponses(
    val responseCode: Int,
    val data: String,
    val message: String
)
