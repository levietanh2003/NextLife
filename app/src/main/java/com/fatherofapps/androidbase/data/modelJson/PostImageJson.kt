package com.fatherofapps.androidbase.data.modelJson

import com.fatherofapps.androidbase.data.models.PostImage

class PostImageJson (
    val name: String,
    val type: String,
    val urlImagePost: String
){
    fun toPostImage() : PostImage {
        return PostImage(
            name,
            type,
            urlImagePost
        )
    }
}