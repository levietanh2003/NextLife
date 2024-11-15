package com.fatherofapps.androidbase.data.models.chat

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatRoom(
    val userId: Int,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String?,
    val lastMessage: String?,
    val lastMessageTime: String
)
