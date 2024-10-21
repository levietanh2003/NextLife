package com.fatherofapps.androidbase.data.apis

import com.fatherofapps.androidbase.data.models.PromotionalPostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostSearchAPI {
    @GET("post/post-filter")
    suspend fun getPostsSearch(
        @Query("tile") type: String? = null,
    ): Response<PromotionalPostResponse>
}