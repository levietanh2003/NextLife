package com.fatherofapps.androidbase.data.apis

import com.fatherofapps.androidbase.data.models.user.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserAPI {

    @GET("users")
    suspend fun getUsers(): Response<UserResponse>
}