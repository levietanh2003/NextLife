package com.fatherofapps.androidbase.data.modelJson

import com.fatherofapps.androidbase.data.models.RoomInfo

class RoomInfoJson (
    val name: String,
    val description: String,
    val address: String,
    val type: String,
    val style: String,
    val floor: String,
    val postImages: List<PostImageJson>,
    val width: Double,
    val height: Double,
    val totalArea: Double,
    val capacity: Int,
    val numberOfBedrooms: Int,
    val numberOfBathrooms: Int,
    val availableFromDate: List<Int>
)
{
     fun toRoomInfo(): RoomInfo {
        return RoomInfo(
            name,
            description,
            address,
            type,
            style,
            floor,
            postImages.map { it.toPostImage() },
            width,
            height,
            totalArea,
            capacity,
            numberOfBedrooms,
            numberOfBathrooms,
            availableFromDate,
        )
    }
}