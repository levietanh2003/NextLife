package com.fatherofapps.androidbase.data.database.entities

import androidx.room.Entity
import com.fatherofapps.androidbase.data.models.PostImage

//@Entity(tableName = "product")
data class PostImageEntity(
    val name: String?,
    val type: String?,
    val urlImagePost: String?
){
    // Phương thức để chuyển đổi PostImageEntity thành PostImage
    fun toPostImage(): PostImage {
        return PostImage(
            name = this.name ?: "", // Gán giá trị mặc định nếu null
            type = this.type ?: "",
            urlImagePost = this.urlImagePost ?: ""
        )
    }
}

