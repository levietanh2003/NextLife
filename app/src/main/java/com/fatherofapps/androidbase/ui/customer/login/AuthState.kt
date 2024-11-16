package com.fatherofapps.androidbase.ui.customer.login

sealed class AuthState {
    object Initial : AuthState()
    object LoggedIn : AuthState()
    object LoggedOut : AuthState()
}