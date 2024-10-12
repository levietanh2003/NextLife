package com.fatherofapps.androidbase.data.services

import com.fatherofapps.androidbase.base.network.BaseRemoteService
import com.fatherofapps.androidbase.data.apis.CarouselAPI
import javax.inject.Inject

class CarouseRemoteService @Inject constructor(private val carouselAPI: CarouselAPI) : BaseRemoteService(){

    suspend fun getCarouselImages() = callApi { carouselAPI.getCarouselImages() }

}