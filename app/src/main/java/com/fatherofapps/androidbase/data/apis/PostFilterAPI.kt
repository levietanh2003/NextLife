package com.fatherofapps.androidbase.data.apis

import com.fatherofapps.androidbase.data.models.PromotionalPostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostFilterAPI {
    @GET("post/post-filter")
    suspend fun getPostPromotional(
        @Query("minPrice") minPrice: Double? = null,      // Optional field for minimum price
        @Query("maxPrice") maxPrice: Double? = null,      // Optional field for maximum price
        @Query("district") district: String? = null,   // Optional field for district
        @Query("type") type: String? = null,           // Optional field for post type
        @Query("hasPromotion") hasPromotion: Boolean? = null
    ): Response<PromotionalPostResponse>
}