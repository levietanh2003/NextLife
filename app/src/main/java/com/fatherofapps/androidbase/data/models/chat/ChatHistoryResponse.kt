package com.fatherofapps.androidbase.data.models.chat

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatHistoryResponse(
    val responseCode: Int,
    val data: List<ChatRoom>,
    val message: String
)
