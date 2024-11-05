package com.fatherofapps.androidbase.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.base.activities.BaseActivity
import com.fatherofapps.androidbase.common.AppSharePreference
import com.fatherofapps.androidbase.ui.customer.login.LoginFragment

import com.mancj.materialsearchbar.MaterialSearchBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private var loadingLayout: FrameLayout? = null

//    @Inject
//    lateinit var appSharePreference: AppSharePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("Frank", "MainActivity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingLayout = findViewById(R.id.loadingLayout)

        showLoading(false)

        // Kiểm tra người dùng đã đăng nhập chưa
//        if (!isUserLoggedIn()) {
//            showLoginFragment()
//        }

        // Thiết lập listener cho MaterialSearchBar
        val searchBar = findViewById<MaterialSearchBar>(R.id.searchBar)
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {}

            override fun onSearchConfirmed(text: CharSequence?) {
                // Khi người dùng nhấn Enter hoặc nút tìm kiếm
                val searchQuery = text.toString()

                // Tạo bundle để truyền dữ liệu
                val bundle = Bundle().apply {
                    putString("search_query", searchQuery)
                }
                Log.d("TitleSearch", searchQuery)

                // Điều hướng đến SearchFragment
                findNavController(R.id.container).navigate(R.id.searchFragment, bundle)
            }

            override fun onButtonClicked(buttonCode: Int) {}
        })

        // Thiết lập sự kiện OnClickListener cho btn_chat
        val btnChat = findViewById<ImageView>(R.id.btn_chat)
        btnChat.setOnClickListener {
            // Khởi chạy ChatActivity
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }

//    private fun isUserLoggedIn(): Boolean {
//        // Sử dụng AppSharePreference để kiểm tra trạng thái đăng nhập
//        return appSharePreference.getSharedPreferences().getBoolean("isLoggedIn", false)
//    }

//    private fun showLoginFragment() {
//        // Hiển thị LoginFragment
//        val loginFragment = LoginFragment()
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container, loginFragment) // Thay thế nội dung của FragmentContainerView
//            .commit()
//    }

    override fun showLoading(isShow: Boolean) {
        loadingLayout?.visibility = if (isShow) View.VISIBLE else View.GONE
    }
}
