package com.fatherofapps.androidbase.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fatherofapps.androidbase.base.network.BaseNetworkException
import com.fatherofapps.androidbase.base.network.NetworkErrorException
import com.fatherofapps.androidbase.common.Event
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job

/**
 * A base class for ViewModels, providing common properties and methods for handling network exceptions,
 * loading states, and navigation. This class serves as the foundation for ViewModels in the app,
 * encapsulating common tasks like error handling and showing loading states.
 */
open class BaseViewModel : ViewModel() {

    /**
     * A [MutableLiveData] object that holds the network exception of type [BaseNetworkException].
     * This is used to propagate network-related errors in the ViewModel.
     */
    var baseNetworkException = MutableLiveData<Event<BaseNetworkException>>()
        protected set

    /**
     * A [MutableLiveData] object that holds the network exception of type [NetworkErrorException].
     * This is used for general network error handling.
     */
    var networkException = MutableLiveData<Event<NetworkErrorException>>()
        protected set

    /**
     * A [MutableLiveData] object that holds the loading state. When set to true, it indicates a loading state.
     */
    var isLoading = MutableLiveData<Event<Boolean>>()
        protected set

    /**
     * A [MutableLiveData] object used to trigger navigation to a new page (via action ID).
     */
    var onNavigateToPage = MutableLiveData<Event<Int>>()
        protected set

    /**
     * A [MutableLiveData] object used to show error messages via a resource ID.
     */
    var errorMessageResourceId = MutableLiveData<Event<Int>>()
        protected set

    /**
     * A [MutableLiveData] object used to show notification messages via a resource ID.
     */
    var notifyMessageResourceId = MutableLiveData<Event<Int>>()
        protected set

    /**
     * A [MutableLiveData] object that holds the loading state for pagination (e.g., loading more data).
     */
    var isLoadingMore = MutableLiveData<Event<Boolean>>()
        protected set

    /**
     * A job representing the current asynchronous operation. It is used to manage coroutine tasks and
     * track their completion.
     */
    var parentJob: Job? = null
        protected set

    /**
     * Registers a job to trigger when the job finishes, hiding the loading state.
     */
    protected fun registerJobFinish() {
        parentJob?.invokeOnCompletion {
            showLoading(false)
        }
    }

    /**
     * A [CoroutineExceptionHandler] used to handle errors in coroutines and propagate them to error handling methods.
     */
    val handler = CoroutineExceptionHandler { _, exception ->
        parseErrorCallApi(exception)
    }

    /**
     * Shows an error message by posting a resource ID to [errorMessageResourceId].
     *
     * @param messageId The resource ID of the error message to be shown.
     */
    protected fun showError(messageId: Int) {
        errorMessageResourceId.postValue(Event(messageId))
    }

    /**
     * Shows a notification message by posting a resource ID to [notifyMessageResourceId].
     *
     * @param messageId The resource ID of the notification message to be shown.
     */
    protected fun showNotify(messageId: Int) {
        notifyMessageResourceId.postValue(Event(messageId))
    }

    /**
     * Adds a [BaseNetworkException] to the [baseNetworkException] LiveData to propagate the error.
     *
     * @param exception The [BaseNetworkException] to be posted.
     */
    protected fun addBaseNetworkException(exception: BaseNetworkException) {
        baseNetworkException.postValue(Event(exception))
    }

    /**
     * Adds a [NetworkErrorException] to the [networkException] LiveData to propagate the error.
     *
     * @param exception The [NetworkErrorException] to be posted.
     */
    protected fun addNetworkException(exception: NetworkErrorException) {
        networkException.postValue(Event(exception))
    }

    /**
     * Controls the loading state by posting a boolean value to [isLoading] LiveData.
     *
     * @param isShow Boolean flag indicating whether to show or hide the loading state.
     */
    protected fun showLoading(isShow: Boolean) {
        isLoading.postValue(Event(isShow))
    }

    /**
     * Controls the loading state for pagination by posting a boolean value to [isLoadingMore] LiveData.
     *
     * @param isShow Boolean flag indicating whether to show or hide the loading state for more data.
     */
    protected fun showLoadingMore(isShow: Boolean) {
        isLoadingMore.postValue(Event(isShow))
    }

    /**
     * Triggers navigation to a new page by posting the action ID to [onNavigateToPage].
     *
     * @param actionId The action ID representing the page to navigate to.
     */
    protected fun navigateToPage(actionId: Int) {
        onNavigateToPage.postValue(Event(actionId))
    }

    /**
     * Handles errors in API calls by identifying the type of error (either [BaseNetworkException] or [NetworkErrorException])
     * and posting them to the appropriate LiveData.
     *
     * @param e The exception that occurred during the API call.
     */
    protected open fun parseErrorCallApi(e: Throwable) {
        when (e) {
            is BaseNetworkException -> {
                baseNetworkException.postValue(Event(e))
            }
            is NetworkErrorException -> {
                networkException.postValue(Event(e))
            }
            else -> {
                val unknowException = BaseNetworkException()
                unknowException.mainMessage = e.message ?: "Something went wrong"
                baseNetworkException.postValue(Event(unknowException))
            }
        }
    }

    /**
     * This method is used to fetch data. It can be overridden by subclasses to implement actual data fetching logic.
     */
    open fun fetchData() {
        // Subclass should override to implement data fetching logic
    }

    /**
     * This method is used to fetch data by product ID. It can be overridden by subclasses to implement the logic.
     *
     * @param idProduct The product ID for the data to be fetched.
     */
    open fun fetchData(idProduct: String) {
        // Subclass should override to implement data fetching logic by product ID
    }

    /**
     * This method is used to fetch data by page number. It can be overridden by subclasses to implement the logic.
     *
     * @param page The page number for pagination.
     */
    open fun fetchData(page: Int) {
        // Subclass should override to implement data fetching logic by page
    }

    /**
     * This method is used to fetch data with optional filters. It can be overridden by subclasses to implement the logic.
     *
     * @param minPrice The minimum price filter.
     * @param maxPrice The maximum price filter.
     * @param district The district filter.
     * @param type The type filter.
     * @param hasPromotion A boolean indicating if there should be a promotion filter.
     */
    open fun fetchData(
        minPrice: Double? = null,
        maxPrice: Double? = null,
        district: String? = null,
        type: Int? = null,
        hasPromotion: Boolean? = null
    ) {
        // Subclass should override to implement data fetching logic with filters
    }
}
