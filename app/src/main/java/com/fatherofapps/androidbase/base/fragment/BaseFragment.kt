package com.fatherofapps.androidbase.base.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.base.activities.BaseActivity
import com.fatherofapps.androidbase.base.network.BaseNetworkException
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel
import com.fatherofapps.androidbase.common.EventObserver

/**
 * A base fragment class that provides common functionality for handling navigation,
 * displaying loading indicators, showing error messages, and observing network or ViewModel events.
 *
 * This class is intended to be used as a parent class for other fragments in the application.
 * It facilitates handling common tasks such as:
 * - Navigating between fragments
 * - Showing and hiding loading indicators
 * - Displaying error and notification dialogs
 * - Observing network and loading events in the ViewModel
 */
open class BaseFragment : Fragment() {

    /**
     * Navigates to a destination defined by the specified action ID, passing a bundle of data.
     *
     * @param actionId The action ID for the navigation.
     * @param bundle The bundle of data to pass to the destination.
     */
    protected fun navigateToPage(actionId: Int, bundle: Bundle) {
        findNavController().navigate(actionId, bundle)
    }

    /**
     * Navigates to a destination defined by the specified action ID without passing any data.
     *
     * @param actionId The action ID for the navigation.
     */
    protected fun navigateToPage(actionId: Int) {
        findNavController().navigate(actionId)
    }

    /**
     * Shows or hides a loading screen in the activity.
     *
     * @param isShow Boolean indicating whether to show or hide the loading screen.
     */
    protected fun showLoading(isShow: Boolean) {
        val activity = requireActivity()
        if (activity is BaseActivity) {
            activity.showLoading(isShow)
        }
    }

    /**
     * Displays an error message based on a network exception.
     *
     * @param e The network exception containing the error information.
     */
    protected fun showErrorMessage(e: BaseNetworkException) {
        showErrorMessage(e.mainMessage)
    }

    /**
     * Displays an error message from a resource ID.
     *
     * @param messageId The resource ID of the error message.
     */
    protected fun showErrorMessage(messageId: Int) {
        val message = requireContext().getString(messageId)
        showErrorMessage(message)
    }

    /**
     * Displays an error message from a string.
     *
     * @param message The error message to display.
     */
    protected fun showErrorMessage(message: String) {
        val activity = requireActivity()
        if (activity is BaseActivity) {
            activity.showErrorDialog(message)
        }
    }

    /**
     * Displays a notification message with a title and content.
     *
     * @param title The title of the notification.
     * @param message The message content of the notification.
     */
    protected fun showNotify(title: String?, message: String) {
        val activity = requireActivity()
        if (activity is BaseActivity) {
            activity.showNotifyDialog(title ?: getDefaultNotifyTitle(), message)
        }
    }

    /**
     * Displays a notification message using resource IDs for both the title and message content.
     *
     * @param titleId The resource ID for the title.
     * @param messageId The resource ID for the message content.
     */
    protected fun showNotify(titleId: Int = R.string.default_notify_title, messageId: Int) {
        val activity = requireActivity()
        if (activity is BaseActivity) {
            activity.showNotifyDialog(titleId, messageId)
        }
    }

    /**
     * Registers an observer to handle network exception events in the ViewModel.
     *
     * @param viewModel The ViewModel to observe.
     * @param viewLifecycleOwner The lifecycle owner of the fragment.
     */
    protected fun registerObserverExceptionEvent(
        viewModel: BaseViewModel,
        viewLifecycleOwner: LifecycleOwner
    ) {
        viewModel.baseNetworkException.observe(viewLifecycleOwner, EventObserver {
            showErrorMessage(it)
        })
    }

    /**
     * Registers an observer to handle network exception events in the ViewModel.
     * Displays a notification if a network error occurs.
     *
     * @param viewModel The ViewModel to observe.
     * @param viewLifecycleOwner The lifecycle owner of the fragment.
     */
    protected fun registerObserverNetworkExceptionEvent(
        viewModel: BaseViewModel,
        viewLifecycleOwner: LifecycleOwner
    ) {
        viewModel.networkException.observe(viewLifecycleOwner, EventObserver {
            showNotify(getDefaultNotifyTitle(), it.message ?: "Network error")
        })
    }

    /**
     * Registers an observer to handle error message events in the ViewModel.
     * Displays the error message in a dialog.
     *
     * @param viewModel The ViewModel to observe.
     * @param viewLifecycleOwner The lifecycle owner of the fragment.
     */
    protected fun registerObserverMessageEvent(
        viewModel: BaseViewModel,
        viewLifecycleOwner: LifecycleOwner
    ) {
        viewModel.errorMessageResourceId.observe(viewLifecycleOwner, EventObserver { message ->
            showErrorMessage(message)
        })
    }

    /**
     * Registers an observer to handle loading more data events in the ViewModel.
     *
     * @param viewModel The ViewModel to observe.
     * @param viewLifecycleOwner The lifecycle owner of the fragment.
     */
    protected fun registerObserverLoadingMoreEvent(viewModel: BaseViewModel, viewLifecycleOwner: LifecycleOwner) {
        viewModel.isLoadingMore.observe(viewLifecycleOwner, EventObserver { isShow ->
            showLoadingMore(isShow)
        })
    }

    /**
     * Displays a loading indicator for loading more data.
     *
     * @param isShow Boolean indicating whether to show or hide the loading indicator.
     */
    protected fun showLoadingMore(isShow: Boolean) {
        // Add functionality to show or hide loading more indicator if needed
    }

    /**
     * Retrieves the default notification title from the resources.
     *
     * @return The default notification title.
     */
    private fun getDefaultNotifyTitle(): String {
        return getString(R.string.default_notify_title)
    }

    /**
     * Registers all exception event observers for a ViewModel in one call.
     *
     * @param viewModel The ViewModel to observe.
     * @param viewLifecycleOwner The lifecycle owner of the fragment.
     */
    protected fun registerAllExceptionEvent(viewModel: BaseViewModel, viewLifecycleOwner: LifecycleOwner) {
        registerObserverExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverNetworkExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverMessageEvent(viewModel, viewLifecycleOwner)
    }

    /**
     * Registers an observer to handle loading state events in the ViewModel.
     * Displays a loading indicator based on the loading state.
     *
     * @param viewModel The ViewModel to observe.
     * @param viewLifecycleOwner The lifecycle owner of the fragment.
     */
    protected fun registerObserverLoadingEvent(viewModel: BaseViewModel, viewLifecycleOwner: LifecycleOwner) {
        viewModel.isLoading.observe(viewLifecycleOwner, EventObserver { isShow ->
            showLoading(isShow)
        })
    }
}
