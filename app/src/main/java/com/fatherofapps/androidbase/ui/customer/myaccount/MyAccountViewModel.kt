package com.fatherofapps.androidbase.ui.customer.myaccount

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.base.network.UserInfoState
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.data.models.user.RegisterResponse
import com.fatherofapps.androidbase.data.models.user.UserData
import com.fatherofapps.androidbase.data.models.user.UserResponse
import com.fatherofapps.androidbase.data.repositories.CustomerRepository
import com.fatherofapps.androidbase.di.AppSharePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MyAccountViewModel @Inject constructor(
    private val myAccountRepository: CustomerRepository,
    private val appSharePreference: AppSharePreference
) : BaseViewModel() {

    private val _myAccount = MutableLiveData<NetworkResult<UserData>>()
    val myInfoResult: LiveData<NetworkResult<UserData>> get() = _myAccount

//    private val _myAccountState = MutableStateFlow<UserInfoState>(UserInfoState.Initial)
//    val myAccountState: StateFlow<UserInfoState> = _myAccountState.asStateFlow()


//    fun fetchMyAccount(token : String) {
//        showLoading(true)
//        parentJob = viewModelScope.launch(handler) {
//            val myInfoResponse = myAccountRepository.getInfoUser(token)
//
//            _myAccount.postValue(myInfoResponse)
//            Log.e("MyAccountViewModel", myInfoResponse.toString())
//        }
//    }

    fun fetchMyAccount() {
        viewModelScope.launch {
            try {
                showLoading(true)
                parentJob = viewModelScope.launch {
//                    val token = appSharePreference.getToken().toString()
                    val myInfoResponse = myAccountRepository.getInfoUser()
                    _myAccount.postValue(myInfoResponse)
                    Log.e("MyAccountViewModel", myInfoResponse.toString())
                }

            } catch (e: Exception) {
                Log.d("MyAccountViewModel", e.toString())
            } finally {
                showLoading(false)
            }
        }
    }
}