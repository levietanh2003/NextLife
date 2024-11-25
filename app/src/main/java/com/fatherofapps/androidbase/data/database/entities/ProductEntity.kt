package com.fatherofapps.androidbase.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fatherofapps.androidbase.data.models.PricingDetails
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.data.models.RoomInfo
import com.fatherofapps.androidbase.data.models.RoomUtility


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
) {
    // Phương thức mở rộng để chuyển đổi ProductEntity thành PromotionalPost
    fun toPromotionalPost(): PromotionalPost {
        return PromotionalPost(
            id = this.id,
            title = this.title ?: "",  // Gán giá trị mặc định nếu null
            roomId = this.roomId ?: "",  // Gán giá trị mặc định nếu null
            description = this.description ?: "",
            roomInfo = this.roomInfo?.toRoomInfo() ?: RoomInfo(
                name = "",
                description = "",
                address = "",  // Cung cấp giá trị mặc định
                type = "",
                style = "",
                floor = "",
                width = 0.0,
                height = 0.0,
                totalArea = 0.0,
                capacity = 0,
                numberOfBedrooms = 0,
                numberOfBathrooms = 0,
                availableFromDate = 0.0,  // Cung cấp giá trị mặc định
                postImages = emptyList()
            ), // Cần xác định RoomUtilityEntity
            roomUtility = this.roomUtility?.toRoomUtility() ?: RoomUtility(
                furnitureAvailability = emptyMap(),
                amenitiesAvailability = emptyMap()
            ),
            pricingDetails = this.pricingDetails?.toPricingDetails() ?: PricingDetails(
                basePrice = 0,
                electricityCost = 0,
                waterCost = 0,
                additionalFees = emptyList()
            ),  // Cần xác định PricingDetailsEntity
            contactInfo = this.contactInfo ?: "",
            additionalDetails = this.additionalDetails ?: "",
            status = this.status ?: "",
            createdDate = this.createdDate ?: 0.0,  // Gán giá trị mặc định nếu null
            lastModifiedDate = this.lastModifiedDate ?: 0.0,  // Gán giá trị mặc định nếu null
            createdBy = this.createdBy ?: "",
            modifiedBy = this.modifiedBy ?: "",
            fixPrice = this.fixPrice
        )
    }

}

