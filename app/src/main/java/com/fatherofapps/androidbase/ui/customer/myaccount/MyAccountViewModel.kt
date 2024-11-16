package com.fatherofapps.androidbase.ui.customer.myaccount

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel
import com.fatherofapps.androidbase.data.models.user.UserData
import com.fatherofapps.androidbase.data.repositories.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MyAccountViewModel @Inject constructor(
    private val myAccountRepository: CustomerRepository,
) : BaseViewModel() {

    private val _myAccount = MutableLiveData<NetworkResult<UserData>>()
    val myInfoResult: LiveData<NetworkResult<UserData>> get() = _myAccount

    fun fetchMyAccount() {
        showLoading(true)
            try {
                showLoading(true)
                parentJob = viewModelScope.launch {
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