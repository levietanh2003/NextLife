package com.fatherofapps.androidbase.data.database.entities
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fatherofapps.androidbase.data.models.RoomInfo

@Entity(tableName = "room_info")
@TypeConverters(ListIntConverter::class, PostImageConverters::class)
data class RoomInfoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "address") val address: String? = null,
    @ColumnInfo(name = "type") val type: String? = null,
    @ColumnInfo(name = "style") val style: String? = null,
    @ColumnInfo(name = "floor") val floor: String? = null,
    @ColumnInfo(name = "width") val width: Double = 0.0,
    @ColumnInfo(name = "height") val height: Double = 0.0,
    @ColumnInfo(name = "total_area") val totalArea: Double = 0.0,
    @ColumnInfo(name = "capacity") val capacity: Int = 0,
    @ColumnInfo(name = "number_of_bedrooms") val numberOfBedrooms: Int = 0,
    @ColumnInfo(name = "number_of_bathrooms") val numberOfBathrooms: Int = 0,
    @ColumnInfo(name = "available_From_Date") val availableFromDate: List<Int>? = null,

    @TypeConverters(PostImageConverters::class)
    @ColumnInfo(name = "post_images") val postImages: List<PostImageEntity>? = null

){

    fun toRoomInfo(): RoomInfo {
        return RoomInfo(
            name = this.name ?: "",
            description = this.description ?: "",
            address = this.address ?: "",
            type = this.type ?: "",
            style = this.style ?: "",
            floor = this.floor ?: "",
            width = this.width,
            height = this.height,
            totalArea = this.totalArea,
            capacity = this.capacity,
            numberOfBedrooms = this.numberOfBedrooms,
            numberOfBathrooms = this.numberOfBathrooms,
            availableFromDate = this.availableFromDate ?: listOf(),
            postImages = this.postImages?.map { it.toPostImage() } ?: emptyList()
        )
    }
}
