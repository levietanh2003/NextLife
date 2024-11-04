package com.fatherofapps.androidbase.data.apis

import com.fatherofapps.androidbase.data.models.ProductDetails
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path

interface PromotionalPostDetailAPI {
    @GET("post/post-by-id/{id}")
    suspend fun getPostPromotionalById(
        @Path("id") id: String
    ): Response<ProductDetails>
}