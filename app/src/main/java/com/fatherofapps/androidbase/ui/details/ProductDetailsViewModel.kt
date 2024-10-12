package com.fatherofapps.androidbase.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel
import com.fatherofapps.androidbase.data.apis.PromotionalPostDetailAPI
import com.fatherofapps.androidbase.data.models.ProductDetails
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.data.repositories.PromotionalPostDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val promotionalPostDetailRepository: PromotionalPostDetailRepository) : BaseViewModel(){
    private var _promotionalPost = MutableLiveData<ProductDetails>()

    val promotionalPost: LiveData<ProductDetails>
        get() = _promotionalPost

//    override fun fetchData(idProduct : String) {
//        // hiện loading
//        showLoading(true)
//        parentJob = viewModelScope.launch(handler) {
//
//            val postPromotionalResponse = postPromotionalRepository.fetchPromotionalPostsById(idProduct)
//            Log.d("ProductDetailsViewModel", "API Response: $postPromotionalResponse")
//
//
//            // Gán danh sách PromotionalPost từ postPromotionalResponse.data vào LiveData
////            _listPostPromotional.postValue(listOf(postPromotionalResponse.data))
//            _promotionalPost.postValue(listOf(postPromotionalResponse))
//
//            Log.d("Data when GET API by ID", postPromotionalResponse.toString())
//
//        }
//        registerJobFinish()
//    }

    override fun fetchData(idProduct: String) {
        // Hiện loading
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val postPromotionalResponse = promotionalPostDetailRepository.fetchPromotionalPostsById(idProduct)
            Log.d("ProductDetailsViewModel", "API Response: $postPromotionalResponse")

            if (postPromotionalResponse != null) {
                // Cập nhật giá trị cho LiveData
                _promotionalPost.postValue(postPromotionalResponse)
                Log.d("ProductDetailsViewModel", "Updated promotional post: $postPromotionalResponse")
            } else {
                Log.e("ProductDetailsViewModel", "Response data is null")
            }
        }
        registerJobFinish()
    }


//    override fun fetchData(idProduct : String) {
//        // hiện loading
//        showLoading(true)
//        parentJob = viewModelScope.launch(handler) {
//
//            val postPromotionalResponse = postPromotionalRepository.fetchPromotionalPostsById(idProduct)
//            Log.d("ProductDetailsViewModel", "API Response: $postPromotionalResponse")
//
//
//            if (postPromotionalResponse != null) {
//
//                // Cập nhật giá trị cho LiveData
//                _promotionalPost.postValue(postPromotionalResponse)
//                Log.d("Filtered Posts", "Filtered Posts: $postPromotionalResponse")
//            } else {
//                Log.e("ProductDetailsViewModel", "Response data is null")
//            }
//            // Gán danh sách PromotionalPost từ postPromotionalResponse.data vào LiveData
////            _listPostPromotional.postValue(listOf(postPromotionalResponse.data))
////            _promotionalPost.postValue(postPromotionalResponse)
////
////            Log.d("Data when GET API by ID", postPromotionalResponse.toString())
//
//        }
//        registerJobFinish()
//    }

}