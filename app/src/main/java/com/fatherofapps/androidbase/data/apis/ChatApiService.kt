package com.fatherofapps.androidbase.data.apis

import com.fatherofapps.androidbase.data.models.chat.ChatHistoryResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
interface ChatApiService {

    @GET("chat/history")
    suspend fun getChatHistory(
        @Query("userId") userId: Int,
    ): Response<ChatHistoryResponse>
}