package com.fatherofapps.androidbase.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

//@Entity(tableName = "pricing_details")
data class PricingDetailsEntity(
    val basePrice: Int,
    val electricityCost: Int,
    val waterCost: Int,
    @TypeConverters(AdditionalFeeConverters::class) val additionalFees: List<AdditionalFeeEntity>?
)

