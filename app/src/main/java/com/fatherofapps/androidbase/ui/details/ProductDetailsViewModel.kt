package com.fatherofapps.androidbase.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel
import com.fatherofapps.androidbase.data.models.ProductDetails
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
}