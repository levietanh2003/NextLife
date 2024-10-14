package com.fatherofapps.androidbase.data.repositories

import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.models.PostData
import com.fatherofapps.androidbase.data.services.PostFeatureRemoteService
import com.fatherofapps.androidbase.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostFeaturedRepository @Inject constructor(
    private val postFeaturedRemoteService: PostFeatureRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun fetchPostFeatured(): PostData = withContext(dispatcher) {
        when (val result = postFeaturedRemoteService.getAllPostFeatured()) {
            is NetworkResult.Success -> {
                // Giả sử result.data là PromotionalPostResponse, trong đó chứa danh sách PromotionalPost
                val promotionalPostResponse = result.data.data

                // Tạo PostData bằng cách sử dụng dữ liệu từ phản hồi API
                PostData(
                    currentPage = promotionalPostResponse.currentPage,
                    totalPages = promotionalPostResponse.totalPages,
                    pageSize = promotionalPostResponse.pageSize,
                    totalElements = promotionalPostResponse.totalElements,
                    data = promotionalPostResponse.data // Đây là List<PromotionalPost>
                )
            }
            is NetworkResult.Error -> {
                throw result.exception
            }
        }
    }
}