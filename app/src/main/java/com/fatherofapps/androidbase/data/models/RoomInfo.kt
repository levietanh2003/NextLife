package com.fatherofapps.androidbase.data.models

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
