package com.fatherofapps.androidbase.data.repositories

import android.util.Log
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.data.services.PostPromotionalRemoteService
import com.fatherofapps.androidbase.data.services.ProductLocalService
import com.fatherofapps.androidbase.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostPromotionalRepository @Inject constructor(
    private val postPromotionalRemoteService: PostPromotionalRemoteService,
    private val productLocalService: ProductLocalService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    private var currentPage = 1
    private var isLastPage = false

    suspend fun fetchPromotionalPostsData2(): List<PromotionalPost> = withContext(dispatcher) {
        if (isLastPage) return@withContext emptyList() // If last page, return empty list

        return@withContext when (val result = postPromotionalRemoteService.getAllPostPromotional(currentPage)) {
            is NetworkResult.Success -> {
                val promotionalPosts = result.data.data.data
                val check = result.data.data

                // Check if there are no products available
                if (check.totalElements == 0) {
                    isLastPage = true // Set last page flag if no products
                    return@withContext emptyList() // Return empty list
                }

                currentPage++ // Increment page after a successful fetch
                promotionalPosts
            }
            is NetworkResult.Error -> {
                throw result.exception
            }
        }
    }

    fun resetPagination() {
        currentPage = 1 // Reset page khi cần
    }
}
