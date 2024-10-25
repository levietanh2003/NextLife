package com.fatherofapps.androidbase.data.database.entities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromMap(value: Map<String, Boolean>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toMap(value: String?): Map<String, Boolean>? {
        val mapType = object : TypeToken<Map<String, Boolean>>() {}.type
        return gson.fromJson(value, mapType)
    }
}