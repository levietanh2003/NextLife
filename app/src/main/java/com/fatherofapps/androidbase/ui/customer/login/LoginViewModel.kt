package com.fatherofapps.androidbase.ui.customer.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel
import com.fatherofapps.androidbase.data.models.user.LogOutResponses
import com.fatherofapps.androidbase.data.models.user.LoginRequest
import com.fatherofapps.androidbase.data.models.user.LoginResponse
import com.fatherofapps.androidbase.data.repositories.CustomerRepository
import com.fatherofapps.androidbase.di.AppSharePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val appSharePreference: AppSharePreference
) : BaseViewModel() {
    // Các phương thức và thuộc tính liên quan đến giao diện đăng nhập
    private val _loginResult = MutableLiveData<NetworkResult<LoginResponse>>()
    val loginResult: LiveData<NetworkResult<LoginResponse>> get() = _loginResult

    // Biến LiveData để theo dõi trạng thái đăng nhập thành công
    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

    private val _logOutResult = MutableLiveData<NetworkResult<LogOutResponses>>()
    val logOutResult: LiveData<NetworkResult<LogOutResponses>> get() = _logOutResult

    // Biến LiveData để theo dõi trạng thái đăng xuất thành công
    private val _logOutSuccess = MutableLiveData<Boolean>()
    val logOutSuccess: LiveData<Boolean> get() = _logOutSuccess

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun checkLoginStatus(): Boolean {
        return appSharePreference.isLoggedIn()
    }

    fun loginUser(request: LoginRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler){
            val result = customerRepository.postLogin(request)
            _loginResult.postValue(result)
            // Phát tín hiệu đăng nhập thành công
            if (result is NetworkResult.Success) {
                val token = result.data.data.token
                // luu token vao share preference
                appSharePreference.saveToken(token)
                appSharePreference.setLoginState(true)
                _loginSuccess.postValue(true)
            }
            Log.d("LoginViewModel", "Login result: $result")// Cập nhật kết quả vào LiveData
            showLoading(false)
        }
        registerJobFinish()
    }

    fun logoutUser() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val token = appSharePreference.getToken()
            Log.d("Token_LogOut", token.toString())
            val result = customerRepository.logOut(token.toString())
            appSharePreference.clearUserSession()
            appSharePreference.setLoginState(false)
            _logOutSuccess.postValue(true)
            Log.d("LoginViewModel", "Token cleared and logout successful")
//            if (result is NetworkResult.Success && result.data.message == "Successfully" && result.data.responseCode == 200) {
//
//            } else {
//                _logOutSuccess.postValue(false)
//            }
            showLoading(false)
        }
    }

}