package com.fatherofapps.androidbase.data.apis

import com.fatherofapps.androidbase.data.models.PaymentResponse
import com.fatherofapps.androidbase.data.models.user.LogOutResponses
import com.fatherofapps.androidbase.data.models.user.PaymentUserResponse
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
    ): Response<LogOutResponses>

    // get lich su nap tien
    @GET("order/all")
    suspend fun getPaymentHistory(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("userId") userId: Int
    ): Response<PaymentResponse>

    // get so tien cua user
    @GET("userPayment/getUserPayment")
    suspend fun getUserPayment() : Response<PaymentUserResponse>
}