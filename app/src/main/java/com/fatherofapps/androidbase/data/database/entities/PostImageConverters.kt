package com.fatherofapps.androidbase.data.database.entities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PostImageConverters {

    @TypeConverter
    fun fromPostImageList(postImages: List<PostImageEntity>?): String? {
        if (postImages == null) return null
        val type = object : TypeToken<List<PostImageEntity>>() {}.type
        return Gson().toJson(postImages, type)
    }

    @TypeConverter
    fun toPostImageList(postImagesString: String?): List<PostImageEntity>? {
        if (postImagesString == null) return null
        val type = object : TypeToken<List<PostImageEntity>>() {}.type
        return Gson().fromJson(postImagesString, type)
    }

}