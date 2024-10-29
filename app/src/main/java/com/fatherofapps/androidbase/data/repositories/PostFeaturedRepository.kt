package com.fatherofapps.androidbase.data.repositories

import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.data.services.PostFeatureRemoteService
import com.fatherofapps.androidbase.data.services.ProductLocalService
import com.fatherofapps.androidbase.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostFeaturedRepository @Inject constructor(
    private val postFeaturedRemoteService: PostFeatureRemoteService,
    private val productLocalService: ProductLocalService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    private var currentPage = 1
    private var isLastPage = false

    suspend fun fetchPostFeatured(): List<PromotionalPost>  = withContext(dispatcher) {
        if (isLastPage) return@withContext emptyList()
        when (val result = postFeaturedRemoteService.getAllPostFeatured(currentPage)) {
            is NetworkResult.Success -> {
                // Giả sử result.data là PromotionalPostResponse, trong đó chứa danh sách PromotionalPost
                val featuredPostResponse = result.data.data.data
                val check = result.data.data
                if(check.totalElements == 0){
                    isLastPage = true
                    return@withContext emptyList()
                }
                currentPage++
//                val productEntities = featuredPostResponse.map { product -> product.toProductEntity() }
//                productLocalService.insertProducts(productEntities)
                // lay du lieu tu database SQLite
//                val fetchProductDatabase = productLocalService.getProductsWithNotNullFixPrice().map { to -> to.toPromotionalPost() }
//                Log.d("PostFeatureRepo", "Converted ProductEntities: $fetchProductDatabase")
//                fetchProductDatabase
                featuredPostResponse
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