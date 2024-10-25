package com.fatherofapps.androidbase.data.models

import com.fatherofapps.androidbase.data.database.entities.RoomInfoEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoomInfo(
    val name: String,
    val description: String,
    val address: String,
    val type: String,
    val style: String,
    val floor: String,
    val postImages: List<PostImage>,
    val width: Double,
    val height: Double,
    val totalArea: Double,
    val capacity: Int,
    val numberOfBedrooms: Int,
    val numberOfBathrooms: Int,
    val availableFromDate: List<Int>
)
{
    fun toRoomInfoEntity() : RoomInfoEntity {
        return RoomInfoEntity(
            name = name,
            description = description,
            address = address,
            type = type,
            style = style,
            floor = floor,
            postImages = postImages.map { it.toPostImageEntity() },
            width = width,
            height = height,
            totalArea = totalArea,
            capacity = capacity,
            numberOfBedrooms = numberOfBedrooms,
            numberOfBathrooms = numberOfBathrooms,
            availableFromDate = availableFromDate
        )
    }
}
