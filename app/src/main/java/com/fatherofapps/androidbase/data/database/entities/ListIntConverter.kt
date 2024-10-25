package com.fatherofapps.androidbase.data.database.entities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListIntConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromList(value: List<Int>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toList(value: String?): List<Int>? {
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, listType)
    }
}