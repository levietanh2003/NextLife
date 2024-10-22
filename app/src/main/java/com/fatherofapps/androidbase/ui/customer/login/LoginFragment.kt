package com.fatherofapps.androidbase.ui.customer.login

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.fatherofapps.androidbase.base.fragment.BaseFragment
import com.fatherofapps.androidbase.databinding.FragmentLoginBinding


class LoginFragment : BaseFragment() {
    private lateinit var dataBinding : FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // khi vua khoi tao se fetch data len

    }
}