package com.fatherofapps.androidbase.data.repositories

import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.services.CarouseRemoteService
import com.fatherofapps.androidbase.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CarouseRepository @Inject constructor(
    private val carouseRemoteService: CarouseRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun getCarouse() = withContext(dispatcher) {
        when(val result = carouseRemoteService.getCarouselImages()){
            is NetworkResult.Success -> {
                result.data
            }
            is NetworkResult.Error -> {
                throw result.exception
        }
        }
    }
}