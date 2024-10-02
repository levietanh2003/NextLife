package com.fatherofapps.androidbase.data.modelJson

import com.fatherofapps.androidbase.data.models.PricingDetails

class PricingDetailsJson(
    val basePrice: Int,
    val electricityCost: Int,
    val waterCost: Int,
    val additionalFees: List<AdditionalFeeJson>
)
{
    fun toPricingDetails() : PricingDetails {
        return  PricingDetails(
            basePrice,
            electricityCost,
            waterCost,
            additionalFees.map { it.toAdditionalFee() }
        )
    }
}