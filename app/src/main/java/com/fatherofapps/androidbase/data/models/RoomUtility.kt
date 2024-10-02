package com.fatherofapps.androidbase.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoomUtility(
    val furnitureAvailability: Map<String, Boolean>,
    val amenitiesAvailability: Map<String, Boolean>
)
