package com.fatherofapps.androidbase.data.modelJson

import com.fatherofapps.androidbase.data.models.RoomUtility


class RoomUtilityJson (
    val furnitureAvailability: Map<String, Boolean>,
    val amenitiesAvailability: Map<String, Boolean>
) {
    fun toRoomUtility() : RoomUtility {
        return RoomUtility(
            furnitureAvailability,
            amenitiesAvailability
        )
    }
}