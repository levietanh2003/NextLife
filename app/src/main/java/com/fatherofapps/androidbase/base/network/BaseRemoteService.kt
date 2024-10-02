package com.fatherofapps.androidbase.base.network

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

open class BaseRemoteService : BaseService() {

    protected suspend fun <T : Any> callApi(call: suspend () -> Response<T>): NetworkResult<T> {
        val response: Response<T>
//        try {
//            response = call.invoke()
//        } catch (t: Throwable) {
//            // in lỗi
//            t.printStackTrace()
//            return NetworkResult.Error(parseNetworkErrorException(t))
//        }
        try {
            response = call.invoke()
        } catch (t: Throwable) {
            Log.e("BaseRemoteService", "Error while calling API: ${t.message}")
            return NetworkResult.Error(parseNetworkErrorException(t))
        }

        // Kiểm tra phản hồi
        if (response.isSuccessful) {
            if (response.body() == null) {
                Log.e("BaseRemoteService", "Response without body")
                return NetworkResult.Error(BaseNetworkException(responseMessage = "Response without body", responseCode = 200))
            } else {
                return NetworkResult.Success(response.body()!!)
            }
        } else {
            val errorBody = response.errorBody()?.string() ?: ""
            Log.e("BaseRemoteService", "API Error: ${response.message()} - Code: ${response.code()} - Body: $errorBody")
            return NetworkResult.Error(parseError(response.message(), response.code(), errorBody))
        }

//        return if (response.isSuccessful) {
//            if (response.body() == null) {
//                NetworkResult.Error(BaseNetworkException(responseMessage =  "Response without body", responseCode = 200))
//            } else {
//                NetworkResult.Success(response.body()!!)
//            }
//        } else {
//            val errorBody = response.errorBody()?.string() ?: ""
//            NetworkResult.Error(parseError(response.message(), response.code(), errorBody))
//        }
    }

}