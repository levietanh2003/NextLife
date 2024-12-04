package com.fatherofapps.androidbase.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.data.repositories.PostFilterRepository
import com.fatherofapps.androidbase.data.repositories.PostSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * SearchViewModel is a ViewModel class responsible for managing the UI-related data for the search functionality.
 * It interacts with repositories to fetch filtered posts based on given parameters or search posts by title,
 * and exposes them as LiveData to the UI.
 *
 * @param postFilterRepository: Repository responsible for fetching posts based on filtering criteria such as price, district, and promotion.
 * @param postSearchRepository: Repository responsible for searching posts by title.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val postFilterRepository: PostFilterRepository,
    private val postSearchRepository: PostSearchRepository

) : BaseViewModel() {

    // Flag to track if "end of list" toast has been shown
    private var hasShownEndOfListToast= false

    // LiveData for holding the list of posts after applying filters
    private var _listPost = MutableLiveData<List<PromotionalPost>>()
    val getPost: LiveData<List<PromotionalPost>>
        get() = _listPost


    /**
     * Fetches posts based on filter criteria such as price, district, type, and promotion status.
     * Updates the list of posts with the filtered results.
     *
     * @param minPrice: The minimum price for filtering posts.
     * @param maxPrice: The maximum price for filtering posts.
     * @param district: The district for filtering posts.
     * @param type: The type of post for filtering.
     * @param hasPromotion: Boolean indicating whether to filter posts with promotions.
     */
    override fun fetchData(minPrice: Double?,
                           maxPrice: Double?,
                           district: String?,
                           type: String?,
                           hasPromotion: Boolean?) {
        showLoading(true)

        parentJob = viewModelScope.launch(handler) {
            val postFilterResponse = postFilterRepository.fetchPostFilter(minPrice, maxPrice, district, type, hasPromotion)
            _listPost.postValue(postFilterResponse.data)
        }
        registerJobFinish()
    }

    /**
     * Fetches posts based on the search query (title search).
     * Updates the list of posts with the search results.
     * Prevents fetching data if the "end of list" toast has already been shown.
     *
     * @param titleSearch: The title or keyword to search for in posts.
     */

    override fun fetchData(titleSearch: String){
        if (hasShownEndOfListToast) return
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val postSearchResponse = postSearchRepository.fetchPostSearch(titleSearch)
            _listPost.postValue(postSearchResponse)
        }
        registerJobFinish()
    }
}