package com.fatherofapps.androidbase.data.apis


import com.fatherofapps.androidbase.data.models.ResponseNewsDetail
import com.fatherofapps.androidbase.data.models.user.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
interface NewsAPI {
    // get all news
    @GET("news/all")
    suspend fun getNews(): Response<NewsResponse>

    // RealEstateExperience
    @GET("experience/all")
    suspend fun getRealEstateExperience(): Response<NewsResponse>

    @GET("experience/get/{id}")
    suspend fun getRealEstateExperienceById(
        @Path("id") id: String
    ): Response<ResponseNewsDetail>
}