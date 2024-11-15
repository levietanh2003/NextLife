package com.fatherofapps.androidbase.data.models.user

data class UserResponse(
    val responseCode: Int,
    val data: UserData,
    val message: String
)