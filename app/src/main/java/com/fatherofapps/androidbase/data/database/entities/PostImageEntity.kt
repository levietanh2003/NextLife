package com.fatherofapps.androidbase.data.database.entities

import androidx.room.Entity

//@Entity(tableName = "product")
data class PostImageEntity(
    val name: String?,
    val type: String?,
    val urlImagePost: String?
)

