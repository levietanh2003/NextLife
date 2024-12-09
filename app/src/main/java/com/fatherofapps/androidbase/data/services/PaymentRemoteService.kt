package com.fatherofapps.androidbase.data.services

import com.fatherofapps.androidbase.base.network.BaseRemoteService
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.apis.PaymentAPI
import com.fatherofapps.androidbase.data.models.PaymentResponse
import javax.inject.Inject


class PaymentRemoteService@Inject constructor(private val paymentAPI: PaymentAPI)  : BaseRemoteService() {

    // post payment
    suspend fun postPayment(amount: String, method: String): NetworkResult<PaymentResponse> {
        return callApi { paymentAPI.paymentPost(amount,method) }
    }

    // get lich su nap tien cua user
    suspend fun getPaymentHistory(userId: Int): NetworkResult<PaymentResponse> {
        return callApi { paymentAPI.getPaymentHistory(1, 10, userId) }
    }
}