package com.fatherofapps.androidbase.data.services

import com.fatherofapps.androidbase.base.network.BaseRemoteService
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.apis.RegisterAPI
import com.fatherofapps.androidbase.data.models.user.RegisterRequest
import com.fatherofapps.androidbase.data.models.user.RegisterResponse
import javax.inject.Inject

class CustomerRemoteService @Inject constructor(
    private val registerAPI: RegisterAPI
) : BaseRemoteService() {

    suspend fun registerUser(request: RegisterRequest): NetworkResult<RegisterResponse> {
        // Sử dụng callApi từ BaseRemoteService
        return callApi {
            registerAPI.registerUser(request) // Gọi API đăng ký
        }
    }
}
