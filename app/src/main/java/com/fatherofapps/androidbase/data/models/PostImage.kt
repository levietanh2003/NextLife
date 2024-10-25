package com.fatherofapps.androidbase.data.models

import com.fatherofapps.androidbase.data.database.entities.PostImageEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostImage(
    val name: String,
    val type: String,
    val urlImagePost: String
)
{
    fun toPostImageEntity() : PostImageEntity {
        return PostImageEntity(
            name = name,
            type = type,
            urlImagePost = urlImagePost
        )
    }
}
