package com.fatherofapps.androidbase.data.apis

import com.fatherofapps.androidbase.data.models.Carousel
import retrofit2.Response
import retrofit2.http.GET

interface CarouselAPI {
    @GET("carousel/all")
    suspend fun getCarouselImages(): Response<List<Carousel>>
}