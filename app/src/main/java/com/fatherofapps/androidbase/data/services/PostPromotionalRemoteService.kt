package com.fatherofapps.androidbase.data.services

import com.fatherofapps.androidbase.base.network.BaseRemoteService
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.apis.PostPromotionalAPI
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.data.models.PromotionalPostResponse

import javax.inject.Inject

class PostPromotionalRemoteService @Inject constructor(private val postPromotionalAPI: PostPromotionalAPI) :
    BaseRemoteService() {

        suspend fun getAllPostPromotional(page: Int) : NetworkResult<PromotionalPostResponse>{
            return callApi { postPromotionalAPI.getPostPromotional(page,4) }
        }
}