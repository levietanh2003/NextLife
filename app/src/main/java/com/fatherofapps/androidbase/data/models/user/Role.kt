package com.fatherofapps.androidbase.data.models.user

data class Role(
    val name: String,
    val description: String,
    val permissions: List<Permission>
)
