package com.fatherofapps.androidbase.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "utility_table")
data class UtilityEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "utility_name") val name: String,
    @ColumnInfo(name = "is_available") val isAvailable: Boolean
)