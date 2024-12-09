package com.fatherofapps.androidbase.ui.customer.payment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.data.models.Transaction
import com.fatherofapps.androidbase.data.models.TransactionData
import com.fatherofapps.androidbase.data.repositories.PaymentRepository
import com.fatherofapps.androidbase.data.repositories.PromotionalPostDetailRepository
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

//    fun getTransactionList(): List<Transaction> {
//        return (_listHistoryPayment.value as? NetworkResult.Success)?.data?.transactions ?: emptyList()
//    }

}