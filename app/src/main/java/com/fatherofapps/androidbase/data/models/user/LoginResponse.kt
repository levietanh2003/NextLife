package com.fatherofapps.androidbase.data.models.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(    val responseCode: Int,
                             val data: LoginData,
                             val message: String)
