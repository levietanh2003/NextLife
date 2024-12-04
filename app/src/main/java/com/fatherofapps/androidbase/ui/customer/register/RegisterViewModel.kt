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

/**
 * RegisterViewModel is a ViewModel class responsible for managing the user registration process.
 * It handles the registration request, updates the registration result, and notifies the UI of the outcome.
 *
 * @param customerRepository The repository that handles the network operations related to customer registration.
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : BaseViewModel() {

    private val _registerResult = MutableLiveData<NetworkResult<RegisterResponse>>()
    val registerResult: LiveData<NetworkResult<RegisterResponse>> get() = _registerResult

    /**
     * Initiates the user registration process.
     * Sends a registration request to the server and updates the result in LiveData.
     *
     * @param request The registration request containing user details (email, password, firstName, lastName, dayOfBirth).
     */
    fun registerUser(request: RegisterRequest) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler){
            val result = customerRepository.postRegister(request)
            _registerResult.postValue(result)
            showLoading(false)
        }
        registerJobFinish()
    }
}
