package com.fatherofapps.androidbase.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.data.repositories.PostFeaturedRepository
import com.fatherofapps.androidbase.data.repositories.PostPromotionalRepository
import com.fatherofapps.androidbase.data.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * HomeViewModel is a ViewModel class responsible for managing the UI-related data for the home screen.
 * It interacts with repositories to fetch promotional and featured posts, and exposes them as LiveData to the UI.
 *
 * @param postPromotionalRepository: Repository responsible for fetching promotional posts data.
 * @param productRepository: Repository for handling product-related data (currently unused in this ViewModel).
 * @param postFeaturedRepository: Repository responsible for fetching featured posts data.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postPromotionalRepository: PostPromotionalRepository,
    private val productRepository: ProductRepository,
    private val postFeaturedRepository: PostFeaturedRepository
) : BaseViewModel() {

    // LiveData for holding a list of featured posts
    private var _postFeatured = MutableLiveData<List<PromotionalPost>>()
    val postFeatured: LiveData<List<PromotionalPost>>
        get() = _postFeatured

    // LiveData for holding a list of promotional posts
    private var _promotionalPost = MutableLiveData<List<PromotionalPost>>()
    val promotionalPost: LiveData<List<PromotionalPost>>
        get() = _promotionalPost

    private var _allPost = MutableLiveData<List<PromotionalPost>>()
    val allPost: LiveData<List<PromotionalPost>>
        get() = _allPost

    // Flag to track loading states for different post categories
    var isLoadingPromotionalPost = false
    var isLoadingFeaturedPost = false

    // Flags to track if "end of list" toast has been shown
    private var hasShownEndOfListToastPromotional = false
    private var hasShownEndOfListToastFeatured = false

    /**
     * Fetches promotional posts data and appends it to the existing list.
     * If no more posts are available, logs a message and prevents further loading.
     */
     fun fetchPromotionalPosts() {
        if (isLoadingPromotionalPost || hasShownEndOfListToastPromotional) return
        isLoadingPromotionalPost = true
        showLoading(true)

        parentJob = viewModelScope.launch {
            try {
                val response = postPromotionalRepository.fetchPromotionalPostsData2()
                if (response.isEmpty()) {
                    if (!hasShownEndOfListToastPromotional) {
                        hasShownEndOfListToastPromotional = true
                        Log.d("HomeViewModel", "No more promotional posts available.")
                    }
                } else {
                    val currentPosts = _promotionalPost.value.orEmpty()
                    _promotionalPost.postValue(currentPosts + response)
                    hasShownEndOfListToastPromotional = false
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching promotional posts: ${e.message}")
            } finally {
                showLoading(false)
                isLoadingPromotionalPost = false
            }
        }
    }

    /**
     * Fetches featured posts data and appends it to the existing list.
     * If no more posts are available, logs a message and prevents further loading.
     */
    fun fetchFeaturedPosts() {
        if (isLoadingFeaturedPost || hasShownEndOfListToastFeatured) return
        isLoadingFeaturedPost = true
        showLoading(true)

        parentJob = viewModelScope.launch {
            try {
                val response = postFeaturedRepository.fetchPostFeatured()
                if (response.isEmpty()) {
                    if (!hasShownEndOfListToastFeatured) {
                        hasShownEndOfListToastFeatured = true
                        Log.d("HomeViewModel", "No more featured posts available.")
                    }
                } else {
                    val currentFeaturedPosts = _postFeatured.value.orEmpty()
                    _postFeatured.postValue(currentFeaturedPosts + response)
                    hasShownEndOfListToastFeatured = false
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching featured posts: ${e.message}")
            } finally {
                showLoading(false)
                isLoadingFeaturedPost = false
            }
        }
    }

    /**
     * Fetches all posts data and appends it to the existing list.
     * Set up data in chart bar
     * If no more posts are available, logs a message and prevents further loading.
     */

    fun fetchAllProduct() {
        showLoading(true)
        isLoadingFeaturedPost = true// Cập nhật trạng thái isLoading
        parentJob = viewModelScope.launch {
            try {
                val response = postFeaturedRepository.fetchAllProduct()
                _allPost.postValue(response)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching products: ${e.message}")
            } finally {
                showLoading(false)
                isLoadingFeaturedPost = false// Ẩn trạng thái tải sau khi hoàn tất
            }
        }
        registerJobFinish()
    }


    /**
     * Resets the pagination of promotional posts and clears the existing data.
     * Call this method when refreshing or reloading the promotional posts.
     */
    fun resetPagination() {
        postPromotionalRepository.resetPagination() // Reset when needed
        _promotionalPost.value = emptyList() // Clear old data
    }
}

