package com.fatherofapps.androidbase.base.activities

import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.fatherofapps.androidbase.base.dialogs.ConfirmDialog
import com.fatherofapps.androidbase.base.dialogs.ErrorDialog
import com.fatherofapps.androidbase.base.dialogs.NotifyDialog

/**
 * BaseActivity is a base class that provides common functionality for displaying dialogs,
 * such as loading, error, notification, and confirmation dialogs, across the application.
 * It extends [AppCompatActivity] and provides methods for managing different types of UI dialogs.
 */
open class BaseActivity : AppCompatActivity() {

    /**
     * Show or hide the loading UI.
     * Override this method to provide your custom implementation for showing loading state.
     *
     * @param isShow A boolean indicating whether the loading state should be shown or hidden.
     */
    open fun showLoading(isShow: Boolean) {
        // Custom implementation for showing loading can be added here.
    }

    /**
     * Displays an error dialog with the provided error message.
     *
     * @param message The error message to be displayed in the dialog.
     */
    open fun showErrorDialog(message: String) {
        val errorDialog = ErrorDialog(this, message)
        errorDialog.show()
        errorDialog.window?.setGravity(Gravity.CENTER)
        errorDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    /**
     * Displays a notification dialog with a title and message. It optionally includes a button.
     *
     * @param titleResourceId The resource ID of the title for the notification dialog.
     * @param messageResourceId The resource ID of the message for the notification dialog.
     * @param textButtonResourceId The resource ID for the button text (optional).
     */
    open fun showNotifyDialog(
        titleResourceId: Int,
        messageResourceId: Int,
        textButtonResourceId: Int = -1
    ) {
        val title = getString(titleResourceId)
        val message = getString(messageResourceId)
        val textButton = if (textButtonResourceId == -1) null else getString(textButtonResourceId)
        showNotifyDialog(message, title, textButton)
    }

    /**
     * Displays a notification dialog with a title and message. Optionally includes a button.
     *
     * @param message The message to be displayed in the notification dialog.
     * @param title The title to be displayed in the notification dialog.
     * @param textButton The text for the button (optional).
     */
    open fun showNotifyDialog(message: String, title: String, textButton: String? = null) {
        val notifyDialog = NotifyDialog(this, title, message, textButton)
        notifyDialog.show()
        notifyDialog.window?.setGravity(Gravity.CENTER)
        notifyDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    /**
     * Displays a confirmation dialog with a title, message, and buttons.
     * This method accepts resource IDs for the dialog title, message, and button text.
     *
     * @param titleResourceId The resource ID of the title for the confirmation dialog.
     * @param messageResourceId The resource ID of the message for the confirmation dialog.
     * @param positiveTitleResourceId The resource ID of the positive button text.
     * @param negativeTitleResourceId The resource ID of the negative button text.
     * @param textButtonResourceId The resource ID for the button text (optional).
     * @param callback A callback to be triggered upon confirmation action.
     */
    open fun showConfirmDialog(
        titleResourceId: Int,
        messageResourceId: Int = -1,
        positiveTitleResourceId: Int,
        negativeTitleResourceId: Int,
        textButtonResourceId: Int = -1,
        callback: ConfirmDialog.ConfirmCallback
    ) {
        val title = getString(titleResourceId)
        val message = if (messageResourceId != -1) getString(messageResourceId) else null
        val negativeButtonTitle = getString(negativeTitleResourceId)
        val positiveButtonTitle = getString(positiveTitleResourceId)
        val textButton = if (textButtonResourceId == -1) null else getString(textButtonResourceId)

        showConfirmDialog(
            title,
            message,
            negativeButtonTitle,
            positiveButtonTitle,
            textButton,
            callback
        )
    }

    /**
     * Displays a confirmation dialog with a title, message, and buttons.
     *
     * @param title The title of the confirmation dialog.
     * @param message The message to be displayed in the confirmation dialog.
     * @param positiveButtonTitle The text for the positive button.
     * @param negativeButtonTitle The text for the negative button.
     * @param textButton The text for the additional button (optional).
     * @param callback A callback to be triggered upon confirmation action.
     */
    open fun showConfirmDialog(
        title: String,
        message: String?,
        positiveButtonTitle: String,
        negativeButtonTitle: String,
        textButton: String?,
        callback: ConfirmDialog.ConfirmCallback
    ) {
        val confirmDialog = ConfirmDialog(
            context = this,
            title = title,
            message = message,
            positiveButtonTitle = positiveButtonTitle,
            negativeButtonTitle = negativeButtonTitle,
            callback = callback
        )
        confirmDialog.show()
        confirmDialog.window?.setGravity(Gravity.CENTER)
        confirmDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}
