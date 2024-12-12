package com.fatherofapps.androidbase.data.services

import com.fatherofapps.androidbase.base.network.BaseRemoteService
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.apis.NewsAPI
import com.fatherofapps.androidbase.data.apis.UserAPI
import com.fatherofapps.androidbase.data.models.user.LogOutResponses
import com.fatherofapps.androidbase.data.models.user.LoginRequest
import com.fatherofapps.androidbase.data.models.user.LoginResponse
import com.fatherofapps.androidbase.data.models.user.NewsResponse
import com.fatherofapps.androidbase.data.models.user.RegisterRequest
import com.fatherofapps.androidbase.data.models.user.RegisterResponse
import com.fatherofapps.androidbase.data.models.user.UserResponse
import javax.inject.Inject

class CustomerRemoteService @Inject constructor(
    private val userAPI: UserAPI,
    private val newsAPI: NewsAPI
    ) : BaseRemoteService() {

    // Các phương thức gọi API liên quan đến đăng ký và đăng nhập
    suspend fun registerUser(request: RegisterRequest): NetworkResult<RegisterResponse> {
        // Sử dụng callApi từ BaseRemoteService
        return callApi {
            userAPI.registerUser(request) // Gọi API đăng ký
        }
    }

    suspend fun loginUser(request: LoginRequest): NetworkResult<LoginResponse> {
        return callApi { userAPI.loginUser(request) }
    }

    // các phương thức logout
    suspend fun logOutUser(token: String): NetworkResult<LogOutResponses> {
        return callApi { userAPI.logoutUser(token) }
    }

    // get info user
    suspend fun getUserInfo(): NetworkResult<UserResponse> {
        // Gọi API và truyền vào token
        return callApi {
            userAPI.getUserInfo()
        }
    }

    // get all news
    suspend fun getAllNews(): NetworkResult<NewsResponse> {
        return callApi { newsAPI.getNews() }
    }
}
