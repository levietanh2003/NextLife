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

    // fetchData
//    override fun fetchData() {
//        // hiện loading
//        showLoading(true)
//        parentJob = viewModelScope.launch(handler) {
//
//            val postPromotionalResponse = postPromotionalRepository.fetchPromotionalPostsData2()
//            val postFeaturedResponse = postFeaturedRepository.fetchPostFeatured()
//            Log.d("Data PostFeatured", postFeaturedResponse.toString())
//            Log.d("Data PromotionalPost", postPromotionalResponse.toString())
//            _postFeatured.postValue(postFeaturedResponse)
//            _promotionalPost.postValue(postPromotionalResponse)
//        }
//        registerJobFinish()
//    }

//    override fun fetchData() {
//        if (isLoadingPage) return // Avoid multiple calls while loading
//
//        showLoading(true)
////        isLoadingPage = true
//        parentJob = viewModelScope.launch(handler) {
//            try {
//                isLoadingPage = true
//                val postPromotionalResponse = postPromotionalRepository.fetchPromotionalPostsData2()
////                 Check if the response is empty
////                if (postPromotionalResponse.isEmpty()) {
////                    // If the response is empty, stop loading and reset the loading flag
////                    Log.d("HomeViewModel", "No promotional posts available. Stopping load.")
////                    isLoadingPage = false
////                    showLoading(false)
////                    return@launch // Exit the coroutine
////                }
////                // Append new data to existing data
////                _promotionalPost.postValue(_promotionalPost.value.orEmpty() + postPromotionalResponse)
//                // Check if the response is empty
//                if (postPromotionalResponse == null) {
//                    // Log the empty state but do not stop loading entirely
//                    isLoadingPage = false
//                    showLoading(false)
//                    Log.d("HomeViewModel", "No promotional posts available.")
//                    return@launch
//                    // You can also set a specific state in the UI if needed
//                }
//                    // Append new data to existing data
//                    _promotionalPost.postValue(_promotionalPost.value.orEmpty() + postPromotionalResponse)
//            } catch (e: Exception) {
//                Log.e("HomeViewModel", "Error fetching promotional posts: ${e.message}")
//            } finally {
//                showLoading(false)
//                isLoadingPage = false
//            }
//        }
//        registerJobFinish()
//    }
//    var hasShownEndOfListToast = false
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
//    override fun fetchData() {
//        if (isLoadingPage || hasShownEndOfListToast) return // Prevent multiple calls while loading
//        isLoadingPage = true
//        showLoading(true)
//
//        parentJob = viewModelScope.launch(handler) {
//            try {
//                val postPromotionalResponse = postPromotionalRepository.fetchPromotionalPostsData2()
//                val postFeaturedResponse = postFeaturedRepository.fetchPostFeatured()
//                // Check if the response is empty and handle accordingly
//                if (postPromotionalResponse.isEmpty()) {
//                    if (!hasShownEndOfListToast) { // Chỉ hiển thị Toast nếu chưa hiển thị
//                        hasShownEndOfListToast = true
//                        Log.d("HomeViewModel", "No promotional posts available. Stopping load.")
//                        // Hiển thị Toast ở đây nếu cần
//                        // Toast.makeText(context, "Đã hết sản phẩm", Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                    // Append new data to existing data
//                    val currentPosts = _promotionalPost.value.orEmpty()
//                    _promotionalPost.postValue(currentPosts + postPromotionalResponse)
//                    // Append new data to existing data
//                    val currentFeaturedPosts = _postFeatured.value.orEmpty()
//                    _postFeatured.postValue(currentFeaturedPosts + postFeaturedResponse)
//                    hasShownEndOfListToast = false
//                }
//            } catch (e: Exception) {
//                Log.e("HomeViewModel", "Error fetching promotional posts: ${e.message}")
//            } finally {
//                showLoading(false)
//                isLoadingPage = false
//            }
//        }
//        registerJobFinish()
//    }
    fun resetPagination() {
        postPromotionalRepository.resetPagination() // Reset khi cần
        _promotionalPost.value = emptyList() // Xóa dữ liệu cũ
    }
}
