package com.fatherofapps.androidbase.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Transaction(
    val id: String,
    val transactionToken: String,
    val orderIdMomo: String?,
    val method: String,
    val amount: Double,
    val userId: Int,
    val status: String,
    val token: String?,
    val createDate: String?, // Adjust if needed
    val lastModifiedDate: Double?,
    val createdBy: String,
    val modifiedBy: String
)
