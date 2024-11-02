package com.fatherofapps.androidbase.ui.customer.login

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel
import com.fatherofapps.androidbase.common.AppSharePreference
import com.fatherofapps.androidbase.data.models.user.LoginRequest
import com.fatherofapps.androidbase.data.models.user.LoginResponse
import com.fatherofapps.androidbase.data.models.user.RegisterRequest
import com.fatherofapps.androidbase.data.models.user.RegisterResponse
import com.fatherofapps.androidbase.data.repositories.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
//    private val appSharePreference: AppSharePreference
) : BaseViewModel() {
    // Các phương thức và thuộc tính liên quan đến giao diện đăng nhập
    private val _loginResult = MutableLiveData<NetworkResult<LoginResponse>>()
    val loginResult: LiveData<NetworkResult<LoginResponse>> get() = _loginResult

    // Biến LiveData để theo dõi trạng thái đăng nhập thành công
    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

    fun loginUser(request: LoginRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler){
            val result = customerRepository.postLogin(request)
            _loginResult.postValue(result)
            // Phát tín hiệu đăng nhập thành công
            if (result is NetworkResult.Success) {
                _loginSuccess.postValue(true)
                // Lưu trạng thái đăng nhập
//                appSharePreference.getSharedPreferences().edit()
//                    .putBoolean("isLoggedIn", true)
//                    .apply()
            }
            Log.d("LoginViewModel", "Login result: $result")// Cập nhật kết quả vào LiveData
            showLoading(false)
        }
        registerJobFinish()
    }
}