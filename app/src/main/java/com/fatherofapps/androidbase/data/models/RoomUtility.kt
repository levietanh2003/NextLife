package com.fatherofapps.androidbase.data.models

import com.fatherofapps.androidbase.data.database.entities.RoomUtilityEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoomUtility(
    val furnitureAvailability: Map<String, Boolean> = emptyMap(),
    val amenitiesAvailability: Map<String, Boolean> = emptyMap()
)
{
    fun toRoomUtilityEntity(): RoomUtilityEntity {
        return RoomUtilityEntity(
            furnitureAvailability = furnitureAvailability,
            amenitiesAvailability = amenitiesAvailability
        )
    }
}

