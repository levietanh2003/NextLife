package com.fatherofapps.androidbase.data.models.user

data class UserResponse(
    val responseCode: Int,
    val data: List<UserData>,
    val message: String
)