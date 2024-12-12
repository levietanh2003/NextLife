package com.fatherofapps.androidbase.ui.customer.myaccount

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.fatherofapps.androidbase.base.activities.BaseActivity
import com.fatherofapps.androidbase.base.dialogs.ConfirmDialog
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.models.user.UserData
import com.fatherofapps.androidbase.databinding.FragmentMyAccountBinding
import com.fatherofapps.androidbase.ui.customer.login.LoginActivity
import com.fatherofapps.androidbase.ui.customer.login.LoginViewModel
import com.fatherofapps.androidbase.ui.customer.payment.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyAccountActivity : BaseActivity() {

    private lateinit var dataBinding: FragmentMyAccountBinding
    private val viewModelMyInfo: MyAccountViewModel by viewModels()
    private val viewModelLogin: LoginViewModel by viewModels()
    private val viewModelPayment: PaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = FragmentMyAccountBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        // Fetch user info if logged in
        if (!viewModelLogin.checkLoginStatus()) {
            navigateToLoginActivity()
        } else {
            // Fetch user account info if logged in
            fetchUserInfo()
        }

        viewModelPayment.getUserPayment()

        // Set the OnClickListener for logout button
        dataBinding.linearLayoutLogout.setOnClickListener {
            performLogout()
        }

        dataBinding.btnHistoryPayment.setOnClickListener {
            val intent = Intent(this, WalletActivity::class.java)
            startActivity(intent)
        }

        dataBinding.btnBack.setOnClickListener {
            onBackPressed() // Hoặc gọi finish() nếu muốn đóng Activity hiện tại
        }

        dataBinding.linearLayoutEditProfile.setOnClickListener {
            val intent = Intent(this, ProfileDetailsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performLogout() {
        // Hiển thị hộp thoại xác nhận
        val confirmDialog = ConfirmDialog(
            context = this,
            callback = object : ConfirmDialog.ConfirmCallback {
                override fun negativeAction() {
                    // Người dùng nhấn "Hủy", không thực hiện hành động gì
                    Toast.makeText(this@MyAccountActivity, "Hủy đăng xuất", Toast.LENGTH_SHORT).show()
                }

                override fun positiveAction() {
                    // Người dùng nhấn "Đồng ý", tiến hành đăng xuất
                    viewModelLogin.logoutUser()
                    navigateToLoginActivity()
                    showNotify("Đăng xuất thành công", "Thông báo")
                }
            },
            title = "Xác nhận",
            message = "Bạn muốn đăng xuất không?",
            positiveButtonTitle = "Đồng ý",
            negativeButtonTitle = "Hủy"
        )
        confirmDialog.show()
    }

    private fun fetchUserInfo() {
        // Call your ViewModel to fetch the user info here
        viewModelMyInfo.fetchMyAccount()

        viewModelMyInfo.myInfoResult.observe(this) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    // Successfully fetched user data
                    val userData = result.data
                    updateUI(userData)
                }
                is NetworkResult.Error -> {
                    // Handle error
                    showNotify("Failed to fetch data: ${result.exception}", "Error")
                    Log.d("MyAccountActivity", "Failed to fetch data: ${result.exception}")
                }
            }
        }
    }

    private fun updateUI(userData: UserData) {
        // Populate the UI with user data
        dataBinding.profileName.text = userData.fullName()
        dataBinding.idUser.text = "Định danh: ${userData.verificationToken}"
        // Add any additional UI updates as necessary
    }

    private fun navigateToLoginActivity() {
        // Navigate to LoginActivity if the user is not logged in
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Optionally close this activity if needed
    }

    private fun showNotify(message: String, title: String) {
        // Show a notification message
        Toast.makeText(this, "$title: $message", Toast.LENGTH_SHORT).show()
    }
}
