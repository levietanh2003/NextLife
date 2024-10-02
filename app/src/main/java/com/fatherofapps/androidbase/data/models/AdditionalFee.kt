package com.fatherofapps.androidbase.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AdditionalFee(
    val type: String,
    val amount: Int
)
