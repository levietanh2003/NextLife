package com.fatherofapps.androidbase.data.modelJson

import com.fatherofapps.androidbase.data.models.PromotionalPost

class PromotionalPostJson(
    val id: String,
    val roomId: String,
    val title: String,
    val description: String,
    val roomInfo: RoomInfoJson,
    val roomUtility: RoomUtilityJson,
    val pricingDetails: PricingDetailsJson,
    val contactInfo: String,
    val additionalDetails: String,
    val status: String,
    val createdDate: Double,
    val lastModifiedDate: Double,
    val createdBy: String,
    val modifiedBy: String,
    val fixPrice: Int?
) {

    fun toPromotional(): PromotionalPost {
        return PromotionalPost(
            id,
            roomId,
            title,
            description,
            roomInfo.toRoomInfo(),
            roomUtility.toRoomUtility(),
            pricingDetails.toPricingDetails(),
            contactInfo,
            additionalDetails,
            status,
            createdDate,
            lastModifiedDate,
            createdBy,
            modifiedBy,
            fixPrice
        )
    }
}