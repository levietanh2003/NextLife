package com.fatherofapps.androidbase.data.apis

import com.fatherofapps.androidbase.data.models.PromotionalPostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostFilterAPI {
    @GET("post/post-filter")
    suspend fun filterPost(
        @Query("minPrice") minPrice: Double? = null,
        @Query("maxPrice") maxPrice: Double? = null,
        @Query("district") district: String? = null,
        @Query("type") type: Int? = null,
        @Query("hasPromotion") hasPromotion: Boolean? = null,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<PromotionalPostResponse>
}