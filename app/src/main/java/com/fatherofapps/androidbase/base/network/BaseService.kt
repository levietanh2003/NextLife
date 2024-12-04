package com.fatherofapps.androidbase.base.network

/**
 * A base class for network-related services providing utility methods for error parsing.
 * This class serves as the foundation for handling errors and exceptions in network operations.
 */
abstract class BaseService {

    /**
     * Parses an error response into a [BaseNetworkException].
     *
     * This method takes the details of an HTTP response error and converts them into a
     * [BaseNetworkException], allowing for structured error handling.
     *
     * @param responseMessage The message from the HTTP response (e.g., error description).
     * @param responseCode The HTTP status code of the error response.
     * @param errorBody The body of the error response in string format, if available.
     * @return A [BaseNetworkException] containing the parsed error details.
     */
    protected fun parseError(
        responseMessage: String?,
        responseCode: Int,
        errorBody: String?
    ): BaseNetworkException {
        val baseNetworkException = BaseNetworkException(responseMessage, responseCode)
        errorBody?.let {
            baseNetworkException.parseFromString(it)
        }
        return baseNetworkException
    }

    /**
     * Converts a [Throwable] into a [NetworkErrorException].
     *
     * This method handles generic network-related exceptions (e.g., timeouts, IO errors)
     * by wrapping them in a [NetworkErrorException] for standardized error management.
     *
     * @param throwable The throwable to be parsed.
     * @return A [NetworkErrorException] with the throwable's message.
     */
    protected fun parseNetworkErrorException(throwable: Throwable): NetworkErrorException {
        return NetworkErrorException(throwable.message)
    }
}
