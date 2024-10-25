package com.fatherofapps.androidbase.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey val id: String,
    val roomId: String?,
    val title: String?,
    val description: String?,
    @Embedded(prefix = "roomInfo_") val roomInfo: RoomInfoEntity?,
    @Embedded(prefix = "roomUtility_") val roomUtility: RoomUtilityEntity?,
    @Embedded(prefix = "pricingDetails_") val pricingDetails: PricingDetailsEntity?,
    val contactInfo: String?,
    val additionalDetails: String?,
    val status: String?,
    val createdDate: Double?,
    val lastModifiedDate: Double?,
    val createdBy: String?,
    val modifiedBy: String?,
    val fixPrice: Int?
)

