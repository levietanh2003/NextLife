package com.fatherofapps.androidbase.data.database.entities

import androidx.room.TypeConverters
import com.fatherofapps.androidbase.data.models.RoomUtility

data class RoomUtilityEntity(
    @TypeConverters(MapConverters::class) val furnitureAvailability: Map<String, Boolean>? = emptyMap(),
    @TypeConverters(MapConverters::class) val amenitiesAvailability: Map<String, Boolean>? = emptyMap()
)
{
    fun toRoomUtility(): RoomUtility {
        return RoomUtility(
            furnitureAvailability = furnitureAvailability ?: emptyMap(),
            amenitiesAvailability = amenitiesAvailability ?: emptyMap()
        )
    }
}
