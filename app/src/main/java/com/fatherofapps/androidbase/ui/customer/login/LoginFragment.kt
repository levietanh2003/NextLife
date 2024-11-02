package com.fatherofapps.androidbase.ui.customer.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.base.fragment.BaseFragment
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.databinding.FragmentLoginBinding
import com.fatherofapps.androidbase.data.models.user.LoginRequest

class LoginFragment : BaseFragment() {
    private lateinit var dataBinding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentLoginBinding.inflate(inflater, container, false)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this

//        setupObservers()

//        dataBinding.btnLoginLogin.setOnClickListener {
//            val email = dataBinding.edEmailLogin.text.toString().trim()
//            val password = dataBinding.edPasswordLogin.text.toString().trim()
//
//            if (email.isNotEmpty() && password.isNotEmpty()) {
//                val loginRequest = LoginRequest(email, password)
//                viewModel.loginUser(loginRequest)
//            } else {
//                // Hiển thị thông báo lỗi nếu email hoặc mật khẩu trống
//                showNotify("Thông báo", "Vui lòng nhập email và mật khẩu")
//            }
//        }

        return dataBinding.root
    }

//    private fun setupObservers() {
//        viewModel.loginSuccess.observe(viewLifecycleOwner, Observer { isSuccess ->
//            if (isSuccess == true) {
//                // Chuyển hướng đến màn hình chính hoặc màn hình khác sau khi đăng nhập thành công
//                findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
//            }
//        })
//
//        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
//            when (result) {
//                is NetworkResult.Success -> {
//                    // Xử lý thành công, ví dụ hiển thị thông báo thành công
//                    showNotify("Đăng ký thành công", "Thông báo")
//                }
//
//                is NetworkResult.Error -> {
//                    // Xử lý lỗi, ví dụ hiển thị thông báo lỗi
//                    showErrorMessage("Đăng ký thất bại: ${result.exception?.message ?: "Có lỗi xảy ra."}")
//                }
//            }
//        }
//    }
}
