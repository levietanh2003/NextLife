package com.fatherofapps.androidbase.data.repositories

import android.util.Log
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.services.UserRemoteService
import com.fatherofapps.androidbase.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteService: UserRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun fetchUsers() = withContext(dispatcher) {

        when (val result = userRemoteService.getUsers()) {
            is NetworkResult.Success -> result.data.data
            is NetworkResult.Error -> {
                Log.d("PromotionalRepository", "Error fetching promotional post by ID: ${result.exception.message}")
                throw result.exception
            }
        }
    }
}