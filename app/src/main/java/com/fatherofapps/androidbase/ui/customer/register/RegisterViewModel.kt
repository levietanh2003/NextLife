package com.fatherofapps.androidbase.ui.customer.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.models.user.RegisterRequest
import com.fatherofapps.androidbase.data.models.user.RegisterResponse
import com.fatherofapps.androidbase.data.repositories.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : BaseViewModel() {

//    private val _registerResult = MutableLiveData<NetworkResult<RegisterRequest>>()
//    val registerResult: LiveData<NetworkResult<RegisterRequest>> get() = _registerResult
    private val _registerResult = MutableLiveData<NetworkResult<RegisterResponse>>()
    val registerResult: LiveData<NetworkResult<RegisterResponse>> get() = _registerResult

    fun registerUser(request: RegisterRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler){
            val result = customerRepository.postRegister(request)
            _registerResult.postValue(result)  // Cập nhật kết quả vào LiveData
            showLoading(false)  // Ẩn trạng th
        }
        registerJobFinish()
    }
}
