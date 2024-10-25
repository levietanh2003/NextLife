package com.fatherofapps.androidbase.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fatherofapps.androidbase.data.database.entities.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAll(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(product: ProductEntity)

    @Query("SELECT * FROM product WHERE id = :producId")
    suspend fun getPostById(producId: String): ProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPromotionalPosts(posts: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeaturedPosts(posts: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    // Phương thức lấy sản phẩm có fixPrice = null
    @Query("SELECT * FROM product WHERE fixPrice IS NULL")
    suspend fun getProductsWithNullFixPrice(): List<ProductEntity>

    // Phương thức lấy sản phẩm có fixPrice != null
    @Query("SELECT * FROM product WHERE fixPrice IS NOT NULL")
    suspend fun getProductsWithNotNullFixPrice(): List<ProductEntity>
}