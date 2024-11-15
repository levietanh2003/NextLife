package com.fatherofapps.androidbase.di

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class AppSharePreference @Inject constructor(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }
}
