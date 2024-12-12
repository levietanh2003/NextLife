package com.fatherofapps.androidbase.data.models.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserRole(
    val name: String,
    val description: String,
    val permissions: List<Permission>
)
