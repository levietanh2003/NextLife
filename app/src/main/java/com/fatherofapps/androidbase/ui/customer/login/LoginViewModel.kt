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

/**
 * LoginViewModel is a ViewModel class that manages operations and states related to user login and logout.
 * It is responsible for sending login and logout requests to the repository, storing the results, and notifying the UI about the status.
 *
 * @param customerRepository The repository containing methods for handling user login and logout.
 * @param appSharePreference The class used to store user information in SharedPreferences, such as the token and login state.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val appSharePreference: AppSharePreference
) : BaseViewModel() {
    // LiveData to observe the login result
    private val _loginResult = MutableLiveData<NetworkResult<LoginResponse>>()
    val loginResult: LiveData<NetworkResult<LoginResponse>> get() = _loginResult

    // LiveData to observe login success status
    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

    // LiveData to observe the logout result
    private val _logOutResult = MutableLiveData<NetworkResult<LogOutResponses>>()
    val logOutResult: LiveData<NetworkResult<LogOutResponses>> get() = _logOutResult

    // LiveData to observe logout success status
    private val _logOutSuccess = MutableLiveData<Boolean>()
    val logOutSuccess: LiveData<Boolean> get() = _logOutSuccess

    // StateFlow to track user authentication state (login, logout)
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    /**
     * Checks the user's login status by verifying the information stored in SharedPreferences.
     *
     * @return The login status of the user (true if logged in, false otherwise).
     */
    fun checkLoginStatus(): Boolean {
        return appSharePreference.isLoggedIn()
    }

    /**
     * Performs user login.
     * Sends a login request to the server and updates the result in LiveData.
     * Saves the token and login state in SharedPreferences if login is successful.
     *
     * @param request The login request containing the username and password.
     */
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
            Log.d("LoginViewModel", "Login result: $result")
            showLoading(false)
        }
        registerJobFinish()
    }

    /**
     * Performs user logout.
     * Clears login information from SharedPreferences and sends a logout request to the server.
     */
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
            showLoading(false)
        }
        registerJobFinish()
    }
}