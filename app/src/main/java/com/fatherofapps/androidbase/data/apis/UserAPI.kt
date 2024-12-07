package com.fatherofapps.androidbase.data.apis

import com.fatherofapps.androidbase.data.models.user.LogOutResponses
import com.fatherofapps.androidbase.data.models.user.LoginRequest
import com.fatherofapps.androidbase.data.models.user.LoginResponse
import com.fatherofapps.androidbase.data.models.user.RegisterRequest
import com.fatherofapps.androidbase.data.models.user.RegisterResponse
import com.fatherofapps.androidbase.data.models.user.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserAPI {

    @GET("users")
    suspend fun getUsers(): Response<UserResponse>
    // login API
    @POST("auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    // register API
    @POST("users/create")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    // logout API
    @POST("auth/logout")
    suspend fun logoutUser(token: String): Response<LogOutResponses>

    // get info user
    @GET("users/my-info")
    suspend fun getUserInfo(): Response<UserResponse>

    // edit profile

}