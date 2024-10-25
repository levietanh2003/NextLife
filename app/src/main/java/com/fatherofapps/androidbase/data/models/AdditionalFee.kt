package com.fatherofapps.androidbase.data.models

import com.fatherofapps.androidbase.data.database.entities.AdditionalFeeEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AdditionalFee(
    val type: String,
    val amount: Int
)
{
    fun toAdditionalFeeEntity() : AdditionalFeeEntity {
        return AdditionalFeeEntity(
            type = type,
            amount = amount
        )
    }
}
