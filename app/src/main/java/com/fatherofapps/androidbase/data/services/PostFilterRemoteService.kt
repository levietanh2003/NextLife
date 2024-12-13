package com.fatherofapps.androidbase.data.services

import com.fatherofapps.androidbase.base.network.BaseRemoteService
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.apis.PostFilterAPI
import com.fatherofapps.androidbase.data.models.PromotionalPostResponse
import javax.inject.Inject

class PostFilterRemoteService @Inject constructor( private val postFilterAPI: PostFilterAPI)  : BaseRemoteService() {
    suspend fun getPostFilter(       minPrice: Double? = null,
                                     maxPrice: Double? = null,
                                     district: String? = null,
                                     type: Int? = null,
                                     hasPromotion: Boolean? = null) : NetworkResult<PromotionalPostResponse> {
        return callApi { postFilterAPI.filterPost(minPrice, maxPrice, district, type, hasPromotion,1,20) }
    }
}