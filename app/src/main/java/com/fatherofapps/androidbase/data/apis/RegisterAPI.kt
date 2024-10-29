package com.fatherofapps.androidbase.data.apis

import com.fatherofapps.androidbase.data.models.user.RegisterRequest
import com.fatherofapps.androidbase.data.models.user.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterAPI {
    @POST("users/create")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>
}