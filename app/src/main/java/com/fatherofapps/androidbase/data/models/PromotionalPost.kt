package com.fatherofapps.androidbase.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PromotionalPost(
    val id: String,
    val roomId: String,
    val title: String,
    val description: String,
    val roomInfo: RoomInfo,
    val roomUtility: RoomUtility,
    val pricingDetails: PricingDetails,
    val contactInfo: String,
    val additionalDetails: String,
    val status: String,
    val createdDate: Double,
    val lastModifiedDate: Double,
    val createdBy: String,
    val modifiedBy: String,
    val fixPrice: Int?
)

