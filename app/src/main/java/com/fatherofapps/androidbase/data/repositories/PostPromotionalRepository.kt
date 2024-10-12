package com.fatherofapps.androidbase.data.repositories

import android.util.Log
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.models.PostData
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.data.services.PostPromotionalRemoteService
import com.fatherofapps.androidbase.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostPromotionalRepository @Inject constructor(
    private val postPromotionalRemoteService: PostPromotionalRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun fetchPromotionalPosts() = withContext(dispatcher) {
        // Gọi phương thức từ remote service để lấy dữ liệu
        when(val result = postPromotionalRemoteService.getAllPostPromotional()){
            is NetworkResult.Success -> {
                // chuyen từ repository sang viewModel là một moduleObject
                result.data
            }
            is NetworkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun fetchPromotionalPostsData() = withContext(dispatcher) {
        when (val result = postPromotionalRemoteService.getAllPostPromotional()) {
            is NetworkResult.Success -> {
                // Giả sử result.data là PromotionalPostResponse
                 result.data.data.data
                // Trả về PostData từ response
            }
            is NetworkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun fetchPromotionalPostsData2(): PostData = withContext(dispatcher) {
        when (val result = postPromotionalRemoteService.getAllPostPromotional()) {
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

    // get theo id
//    suspend fun fetchPromotionalPostsById(id: String): PromotionalPost = withContext(dispatcher) {
//        when (val result = postPromotionalRemoteService.getPostPromotionalById(id)) {
//            is NetworkResult.Success -> {
//
//                result.data
//                // Giả sử result.data là PromotionalPostResponse, trong đó chứa danh sách PromotionalPost
//                // Tạo PostData bằng cách sử dụng dữ liệu từ phản hồi API
//            }
//            is NetworkResult.Error -> {
//                Log.d("PromotionalRepository", "Error fetching promotional post by ID: ${result.exception.message}")
//                throw result.exception
//            }
//        }
//    }
//    suspend fun fetchPromotionalPostsById(id: String): PromotionalPost = withContext(dispatcher) {
//        if (id.isEmpty()) {
//            throw IllegalArgumentException("ID cannot be empty")
//        }
//
//        when (val result = postPromotionalRemoteService.getPostPromotionalById(id)) {
//            is NetworkResult.Success -> result.data
//            is NetworkResult.Error -> {
//                Log.d("PromotionalRepository", "Error fetching promotional post by ID: ${result.exception.message}")
//                throw result.exception
//            }
//        }
//    }
}
