package com.fatherofapps.androidbase.data.models

data class FilterOptions(
    var minPrice: Double? = null,
    var maxPrice: Double? = null,
    var district: String? = null,
    var type: String? = null,
    var hasPromotion: Boolean? = null
)

