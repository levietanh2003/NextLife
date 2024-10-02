package com.fatherofapps.androidbase.data.models

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class PricingDetails(
    val basePrice: Int,
    val electricityCost: Int,
    val waterCost: Int,
    val additionalFees: List<AdditionalFee>
)
