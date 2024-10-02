package com.fatherofapps.androidbase.data.modelJson

import com.fatherofapps.androidbase.data.models.AdditionalFee

class AdditionalFeeJson(
    val type: String,
    val amount: Int
)
{
    fun toAdditionalFee() : AdditionalFee{
        return AdditionalFee(
            type,
            amount
        )
    }
}