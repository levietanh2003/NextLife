package com.fatherofapps.androidbase.data.database.entities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
class Converters {

    // Chuyển đổi List<PostImageEntity> thành String
    @TypeConverter
    fun fromPostImageEntityList(value: List<PostImageEntity>?): String? {
        val gson = Gson()
        return gson.toJson(value)
    }

    // Chuyển đổi String thành List<PostImageEntity>
    @TypeConverter
    fun toPostImageEntityList(value: String?): List<PostImageEntity>? {
        val gson = Gson()
        val listType = object : TypeToken<List<PostImageEntity>>() {}.type
        return gson.fromJson(value, listType)
    }

    // Chuyển đổi List<AdditionalFeeEntity> thành String
    @TypeConverter
    fun fromAdditionalFeeEntityList(value: List<AdditionalFeeEntity>?): String? {
        val gson = Gson()
        return gson.toJson(value)
    }

    // Chuyển đổi String thành List<AdditionalFeeEntity>
    @TypeConverter
    fun toAdditionalFeeEntityList(value: String?): List<AdditionalFeeEntity>? {
        val gson = Gson()
        val listType = object : TypeToken<List<AdditionalFeeEntity>>() {}.type
        return gson.fromJson(value, listType)
    }

    // Chuyển đổi Map<String, Boolean> thành String
    @TypeConverter
    fun fromMap(value: Map<String, Boolean>?): String? {
        val gson = Gson()
        return gson.toJson(value)
    }

    // Chuyển đổi String thành Map<String, Boolean>
    @TypeConverter
    fun toMap(value: String?): Map<String, Boolean>? {
        val gson = Gson()
        val mapType = object : TypeToken<Map<String, Boolean>>() {}.type
        return gson.fromJson(value, mapType)
    }
}