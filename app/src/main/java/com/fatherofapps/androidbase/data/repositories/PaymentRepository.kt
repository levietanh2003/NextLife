package com.fatherofapps.androidbase.data.repositories

import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.models.PaymentResponse
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
}