package com.fatherofapps.androidbase.ui.customer.myaccount

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel
import com.fatherofapps.androidbase.data.models.user.UserData
import com.fatherofapps.androidbase.data.repositories.CustomerRepository
import com.fatherofapps.androidbase.di.AppSharePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * MyAccountViewModel is a ViewModel class responsible for managing the user's account information.
 * It fetches the user's account details from the repository and provides the result to the UI.
 *
 * @param myAccountRepository The repository that handles the network operations related to fetching user account information.
 */
@HiltViewModel
class MyAccountViewModel @Inject constructor(
    private val myAccountRepository: CustomerRepository,
    private val appSharePreference: AppSharePreference
) : BaseViewModel() {

    private val _myAccount = MutableLiveData<NetworkResult<UserData>>()
    val myInfoResult: LiveData<NetworkResult<UserData>> get() = _myAccount

    /**
     * Fetches the user's account information from the repository.
     * It triggers a network request to retrieve the user's data and updates the LiveData with the result.
     */
    fun fetchMyAccount() {
        showLoading(true)
            try {
                showLoading(true)
                parentJob = viewModelScope.launch {
                    val myInfoResponse = myAccountRepository.getInfoUser()
                    _myAccount.postValue(myInfoResponse)
                    // Nếu lấy thông tin thành công, lưu ID người dùng
                    if (myInfoResponse is NetworkResult.Success) {
                        myInfoResponse.data?.let { userData ->
                            appSharePreference.saveIdUser(userData.id)
                        }
                    }
                    Log.e("MyAccountViewModel", myInfoResponse.toString())
                }

            } catch (e: Exception) {
                Log.d("MyAccountViewModel", e.toString())
            } finally {
                showLoading(false)
            }
    }

}