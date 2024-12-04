package com.fatherofapps.androidbase.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel
import com.fatherofapps.androidbase.data.models.ProductDetails
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.data.repositories.PromotionalPostDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ProductDetailsViewModel is a ViewModel class responsible for managing the UI-related data for displaying
 * the details of a promotional post (product) based on the provided product ID. It fetches the product details
 * from the repository and exposes them as LiveData to the UI.
 *
 * @param promotionalPostDetailRepository: Repository responsible for fetching the details of a promotional post
 *                                         based on the product ID.
 */
@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val promotionalPostDetailRepository: PromotionalPostDetailRepository) : BaseViewModel(){
    private var _promotionalPost = MutableLiveData<PromotionalPost>()

    val promotionalPost: LiveData<PromotionalPost>
        get() = _promotionalPost

    /**
     * Fetches the details of a promotional post based on the provided product ID.
     * The method shows a loading indicator while the data is being fetched and updates the LiveData
     * with the fetched product details once the response is successful.
     *
     * @param idProduct: The unique identifier of the product whose details need to be fetched.
     */
    override fun fetchData(idProduct: String) {
        // Hiện loading
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val postPromotionalResponse = promotionalPostDetailRepository.fetchPromotionalPostsById(idProduct)
            Log.d("ProductDetailsViewModel", "API Response: $postPromotionalResponse")

            if (postPromotionalResponse != null) {
                // Update LiveData with the fetched product details
                _promotionalPost.postValue(postPromotionalResponse)
                Log.d("ProductDetailsViewModel", "Updated promotional post: $postPromotionalResponse")
            } else {
                Log.e("ProductDetailsViewModel", "Response data is null")
            }
        }
        registerJobFinish()
    }


}