package com.fatherofapps.androidbase.data.repositories

import android.util.Log
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.services.PromotionalPostDetailRemoteService
import com.fatherofapps.androidbase.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PromotionalPostDetailRepository @Inject constructor(
    private val promotionalPostDetailRemoteService: PromotionalPostDetailRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher

) {

    suspend fun fetchPromotionalPostsById(id: String) = withContext(dispatcher) {
        if (id.isEmpty()) {
            throw IllegalArgumentException("ID cannot be empty")
        }

        when (val result = promotionalPostDetailRemoteService.getPostPromotionalById(id)) {
            is NetworkResult.Success -> result.data.data
            is NetworkResult.Error -> {
                Log.d("PromotionalRepository", "Error fetching promotional post by ID: ${result.exception.message}")
                throw result.exception
            }
        }
    }
}