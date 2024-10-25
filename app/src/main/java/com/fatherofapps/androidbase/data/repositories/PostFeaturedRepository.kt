package com.fatherofapps.androidbase.data.repositories

import android.util.Log
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.models.PostData
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

    suspend fun fetchPostFeatured() = withContext(dispatcher) {
        when (val result = postFeaturedRemoteService.getAllPostFeatured()) {
            is NetworkResult.Success -> {
                // Giả sử result.data là PromotionalPostResponse, trong đó chứa danh sách PromotionalPost
//                val featuredPostResponse = result.data.data.data
//
//                val productEntities = featuredPostResponse.map { product -> product.toProductEntity() }
//                productLocalService.insertProducts(productEntities)
                // lay du lieu tu database SQLite
                val fetchProductDatabase = productLocalService.getProductsWithNotNullFixPrice().map { to -> to.toPromotionalPost() }
                Log.d("PostFeatureRepo", "Converted ProductEntities: $fetchProductDatabase")
                fetchProductDatabase
            }
            is NetworkResult.Error -> {
                throw result.exception
            }
        }
    }
}