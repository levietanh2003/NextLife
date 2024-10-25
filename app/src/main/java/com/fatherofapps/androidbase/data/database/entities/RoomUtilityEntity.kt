package com.fatherofapps.androidbase.data.database.entities

import androidx.room.TypeConverters

data class RoomUtilityEntity(
    @TypeConverters(MapConverters::class) val furnitureAvailability: Map<String, Boolean>?,
    @TypeConverters(MapConverters::class) val amenitiesAvailability: Map<String, Boolean>?
)
