package com.fatherofapps.androidbase.data.services

import com.fatherofapps.androidbase.base.network.BaseRemoteService
import com.fatherofapps.androidbase.data.apis.UserAPI
import javax.inject.Inject

class UserRemoteService @Inject constructor(private val userAPI: UserAPI) : BaseRemoteService()  {
    suspend fun getUsers() = callApi { userAPI.getUsers() }
}