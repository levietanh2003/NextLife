package com.fatherofapps.androidbase.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel
import com.fatherofapps.androidbase.data.modelJson.PostDataJson
import com.fatherofapps.androidbase.data.models.PostData
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.data.models.PromotionalPostResponse
import com.fatherofapps.androidbase.data.repositories.PostPromotionalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val postPromotionalRepository: PostPromotionalRepository) : BaseViewModel() {

//    private var _listPostPromotional = MutableLiveData<List<PostData>>() // Sử dụng List<PostData>
//
//    val listPostPromotional: LiveData<List<PostData>>
//        get() = _listPostPromotional

    private var _postPromotional = MutableLiveData<PostData>() // Sử dụng PostData

    val postPromotional: LiveData<PostData>
        get() = _postPromotional

    private var _promotionalPost = MutableLiveData<List<PromotionalPost>>() // Sử dụng PostData

    val promotionalPost: LiveData<List<PromotionalPost>>
        get() = _promotionalPost

    // fetchData
    override fun fetchData() {
        // hiện loading
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {

//            val postPromotionalResponse = postPromotionalRepository.fetchPromotionalPostsData()
//            // Gán danh sách PromotionalPost từ postPromotionalResponse.data vào LiveData
////            _listPostPromotional.postValue(listOf(postPromotionalResponse.data))
//            _postPromotional.postValue(postPromotionalResponse)
//
//            Log.d("Data", postPromotionalResponse.toString())
            val postPromotionalResponse = postPromotionalRepository.fetchPromotionalPostsData2()
            _promotionalPost.postValue(postPromotionalResponse.data)
            Log.d("Data PromotionalPost", postPromotionalResponse.data.toString())

        }
        registerJobFinish()
    }

//    fun fetchPromotionalPosts() {
//        showLoading(true)
//
//        viewModelScope.launch(handler) {
//            val postPromotionalResponse = postPromotionalRepository.fetchPromotionalPostsData2()
//            _promotionalPost.postValue(postPromotionalResponse.data)
//            Log.d("Data PromotionalPost", postPromotionalResponse.data.toString())
//        }
//    }
}
