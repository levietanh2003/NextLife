package com.fatherofapps.androidbase.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fatherofapps.androidbase.data.models.AdditionalFee

//@Entity(tableName = "additional_fee")
data class AdditionalFeeEntity(
    val type: String?,
    val amount: Int
)
{
    fun toAdditionalFee(): AdditionalFee {
        return AdditionalFee(
            type = this.type ?: "", // Gán giá trị mặc định nếu null
            amount = this.amount
        )
    }
}
