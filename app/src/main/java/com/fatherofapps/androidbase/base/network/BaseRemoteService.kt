package com.fatherofapps.androidbase.base.network

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * A base class for remote service implementations that handle network API calls.
 * This class provides a generic mechanism for making safe and structured API requests,
 * handling errors gracefully, and wrapping responses in a [NetworkResult].
 */
open class BaseRemoteService : BaseService() {

    /**
     * Makes a safe network API call and processes the response.
     *
     * This method wraps the result of the API call in a [NetworkResult] to provide a
     * structured way to handle success or failure.
     *
     * @param T The type of the expected response body.
     * @param call A suspend function representing the API call to be executed.
     * @return A [NetworkResult] containing either the success response or an error.
     */
    protected suspend fun <T : Any> callApi(call: suspend () -> Response<T>): NetworkResult<T> {
        val response: Response<T>
        try {
            // Execute the API call
            response = call.invoke()
        } catch (t: Throwable) {
            // Log and handle any network-related errors
            Log.e("BaseRemoteService", "Error while calling API: ${t.message}")
            return NetworkResult.Error(parseNetworkErrorException(t))
        }

        // Check the response
        return if (response.isSuccessful) {
            if (response.body() == null) {
                // Handle case where response body is unexpectedly null
                Log.e("BaseRemoteService", "Response without body")
                NetworkResult.Error(
                    BaseNetworkException(
                        responseMessage = "Response without body",
                        responseCode = 200
                    )
                )
            } else {
                // Return successful response
                NetworkResult.Success(response.body()!!)
            }
        } else {
            // Handle unsuccessful response
            val errorBody = response.errorBody()?.string() ?: ""
            Log.e(
                "BaseRemoteService",
                "API Error: ${response.message()} - Code: ${response.code()} - Body: $errorBody"
            )
            NetworkResult.Error(parseError(response.message(), response.code(), errorBody))
        }
    }
}
