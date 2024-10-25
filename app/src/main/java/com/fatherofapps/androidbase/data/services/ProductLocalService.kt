package com.fatherofapps.androidbase.data.services

import com.fatherofapps.androidbase.data.database.daos.ProductDao
import com.fatherofapps.androidbase.data.database.entities.ProductEntity
import javax.inject.Inject

class ProductLocalService @Inject constructor(private val productDao: ProductDao) {


    // Lấy tất cả sản phẩm
//    suspend fun getAllProducts(): List<ProductEntity> {
//        return productDao.getAll()
//    }

    // Lấy sản phẩm theo ID
    suspend fun getProductById(productId: String): ProductEntity? {
        return productDao.getPostById(productId)
    }

    // Thêm sản phẩm
    suspend fun insertProduct(product: ProductEntity) {
        productDao.insertPost(product)
    }

    // Thêm danh sách sản phẩm
    suspend fun insertProducts(products: List<ProductEntity>) {
        productDao.insertProducts(products)
    }

    // Thêm bài viết khuyến mãi
    suspend fun insertPromotionalPosts(posts: List<ProductEntity>) {
        productDao.insertPromotionalPosts(posts)
    }

    // Thêm bài viết nổi bật
    suspend fun insertFeaturedPosts(posts: List<ProductEntity>) {
        productDao.insertFeaturedPosts(posts)
    }
}