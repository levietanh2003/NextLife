package com.fatherofapps.androidbase.data.services

import com.fatherofapps.androidbase.base.network.BaseRemoteService
import com.fatherofapps.androidbase.data.apis.PromotionalPostDetailAPI
import javax.inject.Inject

class PromotionalPostDetailRemoteService @Inject constructor(private val promotionalPostDetailAPI: PromotionalPostDetailAPI) : BaseRemoteService(){

    // get by id
    suspend fun getPostPromotionalById(id: String) = callApi { promotionalPostDetailAPI.getPostPromotionalById(id) }

}