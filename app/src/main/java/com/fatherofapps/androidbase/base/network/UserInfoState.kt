package com.fatherofapps.androidbase.base.network

import com.fatherofapps.androidbase.data.models.user.UserData

// State class for UI
sealed class UserInfoState {
    object Initial : UserInfoState()
    data class Success(val userData: UserData) : UserInfoState()
    data class Error(val message: String) : UserInfoState()
}