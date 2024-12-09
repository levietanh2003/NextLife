package com.fatherofapps.androidbase.data.apis

import com.fatherofapps.androidbase.data.models.PaymentResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PaymentAPI {
    // post payment
    @POST("mobile/update/balance")
    suspend fun paymentPost(
        @Query("amount") amount: String,
        @Query("method") method: String
    ): Response<PaymentResponse>

    // get lich su nap tien
    @GET("order/all")
    suspend fun getPaymentHistory(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("userId") userId: Int
    ): Response<PaymentResponse>

}