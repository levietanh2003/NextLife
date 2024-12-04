package com.fatherofapps.androidbase.common

import okhttp3.Interceptor
import okhttp3.Response

/**
 * An interceptor for adding an Authorization header to HTTP requests.
 * This interceptor attaches a Bearer token to the request if a valid token is provided.
 *
 * @property token The authentication token to be included in the Authorization header.
 */
class AuthInterceptor(private val token: String?) : Interceptor {

    /**
     * Intercepts HTTP requests to modify them by adding the Authorization header.
     *
     * @param chain The interceptor chain containing the request to be processed.
     * @return The modified HTTP response after adding the Authorization header.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .apply {
                token?.let {
                    addHeader("Authorization", "Bearer $it")
                }
            }
            .build()
        return chain.proceed(request)
    }
}
