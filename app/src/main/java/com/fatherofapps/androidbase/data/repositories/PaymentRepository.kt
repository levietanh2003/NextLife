package com.fatherofapps.androidbase.data.repositories

import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.models.PaymentResponse
import com.fatherofapps.androidbase.data.models.PostData
import com.fatherofapps.androidbase.data.models.Transaction
import com.fatherofapps.androidbase.data.models.TransactionData
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
    suspend fun postPaymentUser(amount: String, method: String): NetworkResult<PaymentResponse> = withContext(dispatcher) {
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
}
