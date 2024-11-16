package com.fatherofapps.androidbase.di

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSharePreference @Inject constructor(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    // luu token user
    fun saveToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    // lay token cua user
    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    // kiem tra trang thai login duoi dang set true khi login
    fun setLoginState(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("is_logged_in", isLoggedIn).apply()
    }

    // kiem tra trang thai login duoi dang kiem tra co token khong
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getString("token", null) != null
    }

    fun clearUserSession() {
        sharedPreferences.edit().apply {
            remove("token")
            remove("is_logged_in")
            apply()
        }
    }
}
