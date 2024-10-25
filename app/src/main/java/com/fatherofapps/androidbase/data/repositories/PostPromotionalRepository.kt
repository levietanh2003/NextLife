package com.fatherofapps.androidbase.data.repositories

import android.util.Log
import com.fatherofapps.androidbase.base.network.NetworkResult
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

    suspend fun fetchPromotionalPostsData2()  = withContext(dispatcher) {
        when (val result = postPromotionalRemoteService.getAllPostPromotional()) {
            is NetworkResult.Success -> {
                val promotionalPosts = result.data.data.data
                // Tạo ProductEntity từ dữ liệu API và lưu vào database
                val productEntities = promotionalPosts.map { product -> product.toProductEntity() }
                Log.d("PostPromotionalRepo", "Converted ProductEntities: $productEntities")

                productLocalService.insertProducts(productEntities)
                promotionalPosts
            }
            is NetworkResult.Error -> {
                throw result.exception
            }
        }
    }

//    suspend fun fetchPromotionalPostsData(): List<ProductEntity> = withContext(dispatcher) {
//        when (val result = postPromotionalRemoteService.getAllPostPromotional()) {
//            is NetworkResult.Success -> {
//                val promotionalPosts = result.data.data.data
//                val productEntities = promotionalPosts.map { post -> post.toProductEntity() }
//                productLocalService.saveProduct(productEntities)
//                productEntities
//            }
//            is NetworkResult.Error -> throw result.exception
//        }
//    }
}
