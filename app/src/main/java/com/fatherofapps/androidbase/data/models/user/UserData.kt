package com.fatherofapps.androidbase.data.models.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserData(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val dayOfBirth: List<Int>,
    val avatar: String?,
    val verificationToken: String,
    val enabled: Boolean,
    val roles: List<UserRole>,
//    val balance: Int = 0,
//    val createdDate: Double?,
//    val lastModifiedDate: Double?,
//    val createBy: String?,
//    val modifiedBy: String?,

){
    fun fullName(): String {
        return "$firstName $lastName"
    }
}