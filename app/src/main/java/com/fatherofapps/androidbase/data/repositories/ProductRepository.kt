package com.fatherofapps.androidbase.data.repositories

import com.fatherofapps.androidbase.data.database.entities.ProductEntity
import com.fatherofapps.androidbase.data.services.ProductLocalService
import com.fatherofapps.androidbase.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productLocalService: ProductLocalService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher


) {
    // Hàm để lưu sản phẩm vào database
    suspend fun insertProducts(products: List<ProductEntity>)  = withContext(dispatcher) {
        productLocalService.insertProducts(products) // Gọi DAO để lưu danh sách sản phẩm
    }
    // Thêm sản phẩm
    suspend fun insertProduct(product: ProductEntity)  = withContext(dispatcher) {
        productLocalService.insertProduct(product)
    }

    // Thêm sản phẩm khuyến mãi
    suspend fun insertPromotionalPosts(posts: List<ProductEntity>) = withContext(dispatcher)  {
        productLocalService.insertPromotionalPosts(posts)
    }

    // Thêm sản phẩm nổi bật
    suspend fun insertFeaturedPosts(posts: List<ProductEntity>)  = withContext(dispatcher) {
        productLocalService.insertFeaturedPosts(posts)
    }
}