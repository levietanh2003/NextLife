package com.fatherofapps.androidbase.data.models.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaymentUserResponse(
    val responseCode: Int,
    val data: DataPayment?,
    val message: String

)
