package com.fatherofapps.androidbase.ui.customer.payment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel
import com.fatherofapps.androidbase.data.models.Transaction
import com.fatherofapps.androidbase.data.repositories.PaymentRepository
import com.fatherofapps.androidbase.di.AppSharePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val appSharePreference: AppSharePreference
) : BaseViewModel(){
    private var _listHistoryPayment = MutableLiveData<NetworkResult<List<Transaction>>>()

    val listHistoryPayment: LiveData<NetworkResult<List<Transaction>>>
        get() = _listHistoryPayment

    val _balance = MutableLiveData<NetworkResult<Double>>()
    val balance: LiveData<NetworkResult<Double>> get() = _balance

    val _isSuccess = MutableLiveData<Boolean>()
    val isSussces: LiveData<Boolean> get() = _isSuccess


    override fun fetchData() {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val userId = appSharePreference.getIdUser() ?: -1
            if (userId != -1) {
                val result = paymentRepository.getPaymentHistory(userId)
                _listHistoryPayment.postValue(result)
                Log.d("PaymentViewModelResult", "Transaction: ${result}")
            } else {
                Log.e("PaymentViewModel", "Invalid User ID")
            }
        }
        registerJobFinish()
    }

    fun getUserPayment(){
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val result = paymentRepository.getUserPayment()
            _balance.postValue(result)
            Log.d("PaymentViewModelResult", "Transaction: ${result}")

        }
    }

    // postPayment
    fun postPayment(amount: String, method: String) {
        showLoading(true)
        parentJob = viewModelScope.launch(handler) {
            val result = paymentRepository.postPaymentUser(amount, method)
            when (result) {
                is NetworkResult.Success -> {
                    Log.d("PaymentViewModelResult", "Transaction: ${result}")
                    if(result.data.responseCode == 200 && result.data.message == "SUCCESS"){
                        _isSuccess.postValue(true)
                    }else{
                        _isSuccess.postValue(false)
                    }
                }
                is NetworkResult.Error ->{
                    _isSuccess.postValue(false)
                }
            }

            Log.d("PaymentViewModelResult", "Transaction: ${result}")
        }
    }
}