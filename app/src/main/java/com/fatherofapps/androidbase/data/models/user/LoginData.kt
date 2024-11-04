package com.fatherofapps.androidbase.data.models.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginData(
    val token: String,
    val authenticated: Boolean
)
