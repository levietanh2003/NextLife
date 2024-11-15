package com.fatherofapps.androidbase.common

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class AppSharePreference(private val context: Context) {
    companion object {
        const val APP_SHARE_KEY = "com.fatherofapps.androidbase"
    }

    fun getSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(APP_SHARE_KEY, Context.MODE_PRIVATE)
    }
}
