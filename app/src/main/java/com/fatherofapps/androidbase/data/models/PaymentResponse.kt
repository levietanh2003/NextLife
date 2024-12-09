package com.fatherofapps.androidbase.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaymentResponse(
    val responseCode: Int,
    val data: TransactionData,
    val message: String
)
