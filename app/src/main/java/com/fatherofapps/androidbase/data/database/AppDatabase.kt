package com.fatherofapps.androidbase.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fatherofapps.androidbase.data.database.daos.ProductDao
import com.fatherofapps.androidbase.data.database.entities.AdditionalFeeConverters
import com.fatherofapps.androidbase.data.database.entities.AdditionalFeeEntity
import com.fatherofapps.androidbase.data.database.entities.Converters
import com.fatherofapps.androidbase.data.database.entities.CustomerEntity
import com.fatherofapps.androidbase.data.database.entities.MapConverters
import com.fatherofapps.androidbase.data.database.entities.PostImageConverters
import com.fatherofapps.androidbase.data.database.entities.PostImageEntity
import com.fatherofapps.androidbase.data.database.entities.PricingDetailsEntity
import com.fatherofapps.androidbase.data.database.entities.ProductEntity
import com.fatherofapps.androidbase.data.database.entities.RoomInfoEntity

@Database(
    entities = [ProductEntity::class],
    version = 1
)
@TypeConverters(
    PostImageConverters::class,
    AdditionalFeeConverters::class,
    MapConverters::class
)
abstract class AppDatabase : RoomDatabase(){
//    abstract fun customerDao(): CustomerDao
    abstract fun productDao(): ProductDao
}