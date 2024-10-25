package com.fatherofapps.androidbase.data.database.entities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AdditionalFeeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromAdditionalFeeList(value: List<AdditionalFeeEntity>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toAdditionalFeeList(value: String?): List<AdditionalFeeEntity>? {
        val listType = object : TypeToken<List<AdditionalFeeEntity>>() {}.type
        return gson.fromJson(value, listType)
    }
}