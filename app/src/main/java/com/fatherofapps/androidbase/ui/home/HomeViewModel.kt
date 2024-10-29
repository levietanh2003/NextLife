package com.fatherofapps.androidbase.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel

import com.fatherofapps.androidbase.data.models.PromotionalPost

import com.fatherofapps.androidbase.data.repositories.PostFeaturedRepository

import com.fatherofapps.androidbase.data.repositories.PostPromotionalRepository
import com.fatherofapps.androidbase.data.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postPromotionalRepository: PostPromotionalRepository,
    private val productRepository: ProductRepository,
    private val postFeaturedRepository: PostFeaturedRepository,


    ) : BaseViewModel() {

    // product featured
    private var _postFeatured = MutableLiveData<List<PromotionalPost>>() // Sử dụng PostData
    val postFeatured: LiveData<List<PromotionalPost>>
        get() = _postFeatured

    private var _promotionalPost = MutableLiveData<List<PromotionalPost>>()
    // Sử dụng PostData

    val promotionalPost: LiveData<List<PromotionalPost>>
        get() = _promotionalPost



    // Để theo dõi trạng thái loading
    var isLoadingPage = false
    var isLoadingPromotionalPost = false
    var isLoadingFeaturedPost = false

    private var hasShownEndOfListToastPromotional = false
    private var hasShownEndOfListToastFeatured = false

    fun fetchPromotionalPosts() {
        if (isLoadingPromotionalPost || hasShownEndOfListToastPromotional) return
        isLoadingPromotionalPost = true
        showLoading(true)

        viewModelScope.launch {
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

    fun fetchFeaturedPosts() {
        if (isLoadingFeaturedPost || hasShownEndOfListToastFeatured) return
        isLoadingFeaturedPost = true
        showLoading(true)

        viewModelScope.launch {
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

    fun resetPagination() {
        postPromotionalRepository.resetPagination() // Reset khi cần
        _promotionalPost.value = emptyList() // Xóa dữ liệu cũ
    }
}
