package com.fatherofapps.androidbase.data.repositories

import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.models.NewsData
import com.fatherofapps.androidbase.data.models.user.LogOutResponses
import com.fatherofapps.androidbase.data.models.user.LoginRequest
import com.fatherofapps.androidbase.data.models.user.LoginResponse
import com.fatherofapps.androidbase.data.models.user.NewsResponse
import com.fatherofapps.androidbase.data.models.user.RegisterRequest
import com.fatherofapps.androidbase.data.models.user.RegisterResponse
import com.fatherofapps.androidbase.data.models.user.UserData
import com.fatherofapps.androidbase.data.services.CustomerRemoteService
import com.fatherofapps.androidbase.di.AppSharePreference
import com.fatherofapps.androidbase.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CustomerRepository @Inject constructor(
    private val customerRemoteService: CustomerRemoteService,
//    private val customerLocalService: CustomerLocalService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,


    ) {

    suspend fun postRegister(request: RegisterRequest): NetworkResult<RegisterResponse> = withContext(dispatcher) {
        customerRemoteService.registerUser(request)
    }

    suspend fun postLogin(request: LoginRequest): NetworkResult<LoginResponse> = withContext(dispatcher) {
        customerRemoteService.loginUser(request)
    }

    suspend fun logOut(token: String) : NetworkResult<LogOutResponses> = withContext(dispatcher) {
        customerRemoteService.logOutUser(token)
    }

    suspend fun getInfoUser(): NetworkResult<UserData> = withContext(dispatcher) {
        val result = customerRemoteService.getUserInfo()

        when (result) {
            is NetworkResult.Success -> {
                if (result.data.responseCode == 200) {
                    NetworkResult.Success(result.data.data)

                } else {
                    NetworkResult.Error(Exception(result.data.message))  // Return string message directly
                }
            }
            is NetworkResult.Error -> {
                NetworkResult.Error(result.exception)
            }
        }
    }

    // get all news
//    suspend fun getAllNews(): NetworkResult<List<NewsData>> = withContext(dispatcher) {
//        val result = customerRemoteService.getAllNews()
//
//        when(result){
//            is NetworkResult.Success ->{
//                if (result.data.responseCode == 200){
//                    NetworkResult.Success(result.data.data.data)
//                } else {
//                    NetworkResult.Error(Exception(result.data.message))
//                }
//            }
//            is NetworkResult.Error -> {
//                NetworkResult.Error(result.exception)
//            }
//        }
//    }
    suspend fun getAllNews(): NetworkResult<List<NewsData>> = withContext(dispatcher) {
        val result = customerRemoteService.getAllNews()

        when (result) {
            is NetworkResult.Success -> {
                NetworkResult.Success(result.data.data.data)
            }
            is NetworkResult.Error -> {
                // Trả về lỗi nếu có ngoại lệ
                NetworkResult.Error(result.exception)
            }
        }
    }
}