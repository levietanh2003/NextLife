package com.fatherofapps.androidbase.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "additional_fee")
data class AdditionalFeeEntity(
    val type: String?,
    val amount: Int
)
