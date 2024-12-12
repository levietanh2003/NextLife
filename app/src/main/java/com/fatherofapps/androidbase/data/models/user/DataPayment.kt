package com.fatherofapps.androidbase.data.models.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataPayment(
    val id : String,
    val data : UserData,
    val balance : Double?,
    val createdDate : Double?,
    val lastModifiedDate : Double?,
    val createBy : String?,
    val modifiedBy : String?
)
