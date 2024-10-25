package com.fatherofapps.androidbase.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fatherofapps.androidbase.data.models.PricingDetails

//@Entity(tableName = "pricing_details")
data class PricingDetailsEntity(
    val basePrice: Int,
    val electricityCost: Int,
    val waterCost: Int,
    @TypeConverters(AdditionalFeeConverters::class) val additionalFees: List<AdditionalFeeEntity>?
)
{
    fun toPricingDetails(): PricingDetails {
        return PricingDetails(
            basePrice = this.basePrice,
            electricityCost = this.electricityCost,
            waterCost = this.waterCost,
            additionalFees = this.additionalFees?.map { it.toAdditionalFee() } ?: emptyList() // Chuyển đổi danh sách
        )
    }
}

