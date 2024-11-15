package com.fatherofapps.androidbase.data.services

import com.fatherofapps.androidbase.base.network.BaseRemoteService
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.apis.UserAPI
import com.fatherofapps.androidbase.data.models.user.UserResponse
import javax.inject.Inject

class UserRemoteService @Inject constructor(private val userAPI: UserAPI) : BaseRemoteService()  {
    suspend fun getUsers() = callApi { userAPI.getUsers() }


}