package com.fatherofapps.androidbase.common

import android.util.Log
import com.fatherofapps.androidbase.BuildConfig

/**
 * Utility object for logging messages in the application.
 * Logs are only printed in the debug build to prevent sensitive information
 * from being exposed in the production environment.
 */
object Logger {

    /**
     * Logs a message with the specified tag.
     * Logs are only output if the app is running in debug mode (`BuildConfig.DEBUG` is true).
     *
     * @param tag The tag used to identify the source of the log message.
     * @param message The message to be logged.
     */
    fun log(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }

}