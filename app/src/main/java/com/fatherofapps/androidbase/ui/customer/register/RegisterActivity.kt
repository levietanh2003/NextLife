package com.fatherofapps.androidbase.ui.customer.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.fatherofapps.androidbase.base.activities.BaseActivity
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.models.user.RegisterRequest
import com.fatherofapps.androidbase.databinding.FragmentRegisterBinding
import com.fatherofapps.androidbase.ui.customer.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {
    private lateinit var dataBinding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout và gán ViewModel
        dataBinding = FragmentRegisterBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this

        setupClickListeners()
        observeLoginResult()

        dataBinding.tvDoYouHaveAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setupClickListeners() {
        dataBinding.buttonRegisterRegister.setOnClickListener {
            val email = dataBinding.edEmailRegister.text.toString().trim()
            val password = dataBinding.edPasswordRegister.text.toString().trim()
            val firstName = dataBinding.edFirstNameRegister.text.toString().trim()
            val lastName = dataBinding.edLastNameRegister.text.toString().trim()
            val dayOfBirth = dataBinding.edDayOfBirth.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty() && dayOfBirth.isNotEmpty()) {
                val registerRequest = RegisterRequest(email, password, firstName, lastName, dayOfBirth)
                viewModel.registerUser(registerRequest)
            }else{
                showNotifyDialog("Vui lòng điền đầy đủ thông tin","Thông báo","OK")
            }
        }
    }

    private fun observeLoginResult() {
        viewModel.registerResult.observe(this) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    // Show success message
                    showNotifyDialog("Đăng ký thành công","Thông báo","OK")
                    finish()
                }
                is NetworkResult.Error -> {
                    // Show error message
                    showErrorDialog("Đăng ký thật bại")
                }
            }
        }
    }
}