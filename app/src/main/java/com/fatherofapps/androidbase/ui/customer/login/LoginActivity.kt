package com.fatherofapps.androidbase.ui.customer.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.fatherofapps.androidbase.base.activities.BaseActivity
import com.fatherofapps.androidbase.base.network.NetworkResult

import com.fatherofapps.androidbase.data.models.user.LoginRequest
import com.fatherofapps.androidbase.databinding.FragmentLoginBinding
import com.fatherofapps.androidbase.ui.customer.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private lateinit var dataBinding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout và gán ViewModel
        dataBinding = FragmentLoginBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this

        setupClickListeners()
        observeLoginResult()

        dataBinding.tvDontHaveAccount.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupClickListeners() {
        // Thiết lập nút đăng nhập
        dataBinding.btnLoginLogin.setOnClickListener {
            val email = dataBinding.edEmailLogin.text.toString().trim()
            val password = dataBinding.edPasswordLogin.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val loginRequest = LoginRequest(email, password)
                viewModel.loginUser(loginRequest)

            } else {
                // Hiển thị thông báo lỗi nếu email hoặc mật khẩu trống
            }
        }
    }

    private fun observeLoginResult() {
        // Observe the login result from the ViewModel
        viewModel.loginResult.observe(this) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    // Show success message
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()

                    // Close the LoginActivity and return to the previous screen
                    finish()
                }
                is NetworkResult.Error -> {
                    // Show error message
                    Toast.makeText(this, "Đăng nhập thất bại: ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
