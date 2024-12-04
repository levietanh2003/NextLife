package com.fatherofapps.androidbase.di

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A singleton class responsible for managing the app's shared preferences, particularly
 * related to user authentication and session management.
 *
 * This class provides methods to save, retrieve, and clear user-related information
 * such as the authentication token and login state.
 *
 * It ensures that the app can persist essential data (such as tokens) across app sessions
 * and also allows for checking the user's login state.
 *
 * @param context The application context used to access shared preferences.
 */
@Singleton
class AppSharePreference @Inject constructor(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    /**
     * Saves the authentication token to shared preferences.
     *
     * @param token The authentication token to save.
     */
    fun saveToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    /**
     * Retrieves the authentication token from shared preferences.
     *
     * @return The authentication token if it exists, otherwise null.
     */
    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    /**
     * Saves the login state to shared preferences, indicating whether the user is logged in or not.
     *
     * @param isLoggedIn A boolean value representing the login state (true for logged in, false for logged out).
     */
    fun setLoginState(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("is_logged_in", isLoggedIn).apply()
    }

    /**
     * Checks whether the user is logged in by verifying the existence of a stored authentication token.
     *
     * @return True if the user is logged in (i.e., the token exists), otherwise false.
     */
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getString("token", null) != null
    }

    /**
     * Clears the user session by removing the authentication token and login state from shared preferences.
     */
    fun clearUserSession() {
        sharedPreferences.edit().apply {
            remove("token")
            remove("is_logged_in")
            apply()
        }
    }
}

