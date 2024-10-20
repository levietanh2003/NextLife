package com.fatherofapps.androidbase.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.data.repositories.PostFilterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val postFilterRepository: PostFilterRepository

) : BaseViewModel() {

    private var _postFilter = MutableLiveData<List<PromotionalPost>>()
    val postFilter: LiveData<List<PromotionalPost>>
        get() = _postFilter

    override fun fetchData(minPrice: Double?,
                           maxPrice: Double?,
                           district: String?,
                           type: String?,
                           hasPromotion: Boolean?) {
        showLoading(true)

        parentJob = viewModelScope.launch(handler) {
            val postFilterResponse = postFilterRepository.fetchPostFilter(minPrice, maxPrice, district, type, hasPromotion)
            _postFilter.postValue(postFilterResponse.data)
        }
        registerJobFinish()
    }
}