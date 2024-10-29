package com.fatherofapps.androidbase.data.repositories

import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.data.services.PostSearchRemoteService
import com.fatherofapps.androidbase.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostSearchRepository @Inject constructor(
    private val postSearchRemoteService: PostSearchRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    // fun get list product form search return
    suspend fun fetchPostSearch(titleSearch: String? = null): List<PromotionalPost> = withContext(dispatcher) {
        when (val result = postSearchRemoteService.getPostSearch(titleSearch)) {
            is NetworkResult.Success -> {
                // Giả sử result.data là PromotionalPostResponse, trong đó chứa danh sách PromotionalPost
                val promotionalPostResponse = result.data.data.data
                // Tạo PostData bằng cách sử dụng dữ liệu từ phản hồi API
                promotionalPostResponse
            }
            is NetworkResult.Error -> {
                throw result.exception
            }
        }

    }
}