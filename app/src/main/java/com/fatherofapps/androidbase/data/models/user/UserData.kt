package com.fatherofapps.androidbase.data.models.user

data class UserData(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val dayOfBirth: List<Int>,
    val verificationToken: String,
    val enabled: Boolean,
    val roles: List<UserRole>
){
    fun fullName(): String {
        return "$firstName $lastName"
    }
}