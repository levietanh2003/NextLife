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

    private var _promotionalPost = MutableLiveData<List<PromotionalPost>>() // Sử dụng PostData

    val promotionalPost: LiveData<List<PromotionalPost>>
        get() = _promotionalPost

    // fetchData
    override fun fetchData() {
        // hiện loading
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {

            val postPromotionalResponse = postPromotionalRepository.fetchPromotionalPostsData2()
            val postFeaturedResponse = postFeaturedRepository.fetchPostFeatured()
            Log.d("Data PostFeatured", postFeaturedResponse.toString())
            Log.d("Data PromotionalPost", postPromotionalResponse.toString())
            _postFeatured.postValue(postFeaturedResponse.data)
            _promotionalPost.postValue(postPromotionalResponse)
        }
        registerJobFinish()
    }
}
