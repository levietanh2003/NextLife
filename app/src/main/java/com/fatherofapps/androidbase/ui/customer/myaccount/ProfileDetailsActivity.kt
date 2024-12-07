package com.fatherofapps.androidbase.ui.customer.myaccount

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.fatherofapps.androidbase.base.activities.BaseActivity
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.models.user.UserData
import com.fatherofapps.androidbase.databinding.ActivityProfileDetailsBinding
import com.fatherofapps.androidbase.ui.customer.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import formatBirthDate

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class ProfileDetailsActivity : BaseActivity() {

    private lateinit var dataBinding: ActivityProfileDetailsBinding
    private val viewModelMyInfo: MyAccountViewModel by viewModels()
    private val viewModelLogin: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        // Observe user account info LiveData
        viewModelMyInfo.myInfoResult.observe(this) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    // Handle success, update UI with user data
                    val userData = result.data
                    updateUI(userData)
                }
                is NetworkResult.Error -> {
                    // Handle error, show error message
                    showNotify("Lỗi khi tải thông tin", "Thông báo")
                }
            }
        }

        // Fetch user info if logged in
        if (!viewModelLogin.checkLoginStatus()) {
            showNotify("Chưa đăng nhập", "Thông báo")
        } else {
            // Fetch user account info
            viewModelMyInfo.fetchMyAccount()
        }

        dataBinding.btnBack.setOnClickListener {
            onBackPressed() // Hoặc gọi finish() nếu muốn đóng Activity hiện tại
        }
    }

    private fun updateUI(userData: UserData) {
        // Populate the UI with user data
        dataBinding.edtFullName.setText(userData.fullName())
        dataBinding.edtEmail.setText(userData.email)
        dataBinding.edtBirthDate.setText(formatBirthDate(userData.dayOfBirth))
    }

    private fun showNotify(message: String, title: String) {
        // Show a notification message
        Toast.makeText(this, "$title: $message", Toast.LENGTH_SHORT).show()
    }

    private fun updateInfo(){
        val fullName = dataBinding.edtFullName.text.toString()
        val address = dataBinding.edtAddress.text.toString()
        val phone = dataBinding.edtPhone.text.toString()
        val intro = dataBinding.edtIntro.text.toString()

        if(fullName.isEmpty() || address.isEmpty() || phone.isEmpty() || intro.isEmpty()){
            showNotifyDialog("Vui lòng nhập đầy đủ thông tin", "Thông báo", "OK")
        }else{
            // xu ly update info
        }

    }
}
