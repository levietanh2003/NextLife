package com.fatherofapps.androidbase.data.services

import com.fatherofapps.androidbase.base.network.BaseRemoteService
import com.fatherofapps.androidbase.data.apis.PostFeaturedAPI
import javax.inject.Inject

class PostFeatureRemoteService @Inject constructor(
    private val postFeatureAPI: PostFeaturedAPI,
) : BaseRemoteService(){

    suspend fun getAllPostFeatured(page: Int) = callApi { postFeatureAPI.getPostFeatured(page,4) }
}