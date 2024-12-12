package com.fatherofapps.androidbase.data.repositories

import android.util.Log
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.models.PaymentResponse
import com.fatherofapps.androidbase.data.models.Transaction
import com.fatherofapps.androidbase.data.models.user.LogOutResponses
import com.fatherofapps.androidbase.data.services.PaymentRemoteService
import com.fatherofapps.androidbase.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PaymentRepository@Inject constructor(
    private val paymentRemoteService: PaymentRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    // fun post payment user
    suspend fun postPaymentUser(amount: String, method: String): NetworkResult<LogOutResponses> = withContext(dispatcher) {
        paymentRemoteService.postPayment(amount, method)
    }

    // get lich su nap tien cua user
    suspend fun getPaymentHistory(userId: Int): NetworkResult<List<Transaction>> = withContext(dispatcher) {
        when (val result = paymentRemoteService.getPaymentHistory(userId)) {
            is NetworkResult.Success -> {
                // Giả sử result.data là TransactionResponse, trong đó chứa danh sách Transaction
                NetworkResult.Success(result.data.data.data)
            }
            is NetworkResult.Error -> {
                NetworkResult.Error(result.exception)
            }
        }
    }

    // get tien cua user
//    suspend fun getUserPayment(): NetworkResult<Double> = withContext(dispatcher) {
//        when (val result = paymentRemoteService.getUserPayment()) {
//            is NetworkResult.Success -> {
//                NetworkResult.Success(result.data.data.balance)
//            }
//
//            is NetworkResult.Error -> {
//                NetworkResult.Error(result.exception)
//            }
//        }
//    }
    suspend fun getUserPayment(): NetworkResult<Double> = withContext(dispatcher) {
        when (val result = paymentRemoteService.getUserPayment()) {
            is NetworkResult.Success -> {
                // In log từng bước
                Log.d("DEBUG_GetUser", "API response: ${result.data}")
                val data = result.data.data
                Log.d("DEBUG_GetUser", "Extracted data: $data")
                val balance = data?.balance
                Log.d("DEBUG_GetUser", "Extracted balance: $balance")

                if (balance != null) {
                    NetworkResult.Success(balance)
                } else {
                    NetworkResult.Error(Exception("Balance is null"))
                }
            }

            is NetworkResult.Error -> {
                Log.e("ERROR", "API call failed", result.exception)
                NetworkResult.Error(result.exception)
            }
        }
    }

}
