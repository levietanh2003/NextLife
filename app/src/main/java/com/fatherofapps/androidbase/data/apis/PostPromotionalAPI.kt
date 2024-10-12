package com.fatherofapps.androidbase.data.apis

import com.fatherofapps.androidbase.data.modelJson.PromotionalPostResponseJson
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.data.models.PromotionalPostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostPromotionalAPI {
    // get list post PromotionalAPI
    @GET("post/list-post-featured")
    suspend fun getPostPromotional(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<PromotionalPostResponse>
}



