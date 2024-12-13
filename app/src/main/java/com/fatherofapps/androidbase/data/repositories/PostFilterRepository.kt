package com.fatherofapps.androidbase.data.repositories

import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.models.PostData
import com.fatherofapps.androidbase.data.services.PostFilterRemoteService
import com.fatherofapps.androidbase.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostFilterRepository @Inject constructor(
    private val postFilterRemoteService: PostFilterRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    //
    suspend fun fetchPostFilter(minPrice: Double? = null,
                                maxPrice: Double? = null,
                                district: String? = null,
                                type: Int? = null,
                                hasPromotion: Boolean? = null): PostData = withContext(dispatcher) {
        when (val result = postFilterRemoteService.getPostFilter(minPrice, maxPrice, district, type, hasPromotion)) {
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