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

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val postFilterRepository: PostFilterRepository,
    private val postSearchRepository: PostSearchRepository

) : BaseViewModel() {

    private var hasShownEndOfListToast= false

    // post fillter
    private var _listPost = MutableLiveData<List<PromotionalPost>>()
    val getPost: LiveData<List<PromotionalPost>>
        get() = _listPost

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