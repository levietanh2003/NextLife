package com.fatherofapps.androidbase.data.models.user

data class UserData(
    val id: Int,
    val username: String,
    val firstName: String,
    val lastName: String,
    val dob: String,
    val roles: List<Role>
)