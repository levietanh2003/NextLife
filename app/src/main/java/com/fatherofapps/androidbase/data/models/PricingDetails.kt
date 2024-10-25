package com.fatherofapps.androidbase.data.models

import com.fatherofapps.androidbase.data.database.entities.PricingDetailsEntity
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class PricingDetails(
    val basePrice: Int,
    val electricityCost: Int,
    val waterCost: Int,
    val additionalFees: List<AdditionalFee>
)
{
    fun toPricingDetailsEntity() : PricingDetailsEntity {
        return PricingDetailsEntity(
            basePrice = basePrice,
            electricityCost = electricityCost,
            waterCost = waterCost,
            additionalFees = additionalFees.map { it.toAdditionalFeeEntity() }
        )
    }
}
