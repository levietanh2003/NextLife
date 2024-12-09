package com.fatherofapps.androidbase.data.apis


import com.fatherofapps.androidbase.data.models.user.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    // get all news
    @GET("news/all")
    suspend fun getNews(): Response<NewsResponse>
}