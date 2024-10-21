package com.fatherofapps.androidbase.data.services

import com.fatherofapps.androidbase.base.network.BaseRemoteService
import com.fatherofapps.androidbase.data.apis.PostSearchAPI
import javax.inject.Inject

class PostSearchRemoteService  @Inject constructor (private val postSearchAPI: PostSearchAPI) : BaseRemoteService() {

    suspend fun getPostSearch(titleSearch: String? = null) = callApi { postSearchAPI.getPostsSearch(titleSearch) }
}