package com.fatherofapps.androidbase.data.models

import com.fatherofapps.androidbase.data.database.entities.ProductEntity
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
    val fixPrice: Int?,
//    val created: String?
)
{
    fun toProductEntity(): ProductEntity {
        return ProductEntity(
            id = id,
            title = title,
            description = description,
            roomId = roomId,
            contactInfo = contactInfo,
            additionalDetails = additionalDetails,
            status = status,
            createdDate = createdDate,
            lastModifiedDate = lastModifiedDate,
            createdBy = createdBy,
            modifiedBy = modifiedBy,
            fixPrice = fixPrice,
            roomInfo = roomInfo.toRoomInfoEntity(),
            roomUtility = roomUtility.toRoomUtilityEntity(),
            pricingDetails = pricingDetails.toPricingDetailsEntity()
        )
    }
}

