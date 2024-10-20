package com.fatherofapps.androidbase.data.apis


import com.fatherofapps.androidbase.data.models.PromotionalPostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostPromotionalAPI {

    @GET("post/list-post-promotional")
    suspend fun getPostPromotional(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<PromotionalPostResponse>
}



