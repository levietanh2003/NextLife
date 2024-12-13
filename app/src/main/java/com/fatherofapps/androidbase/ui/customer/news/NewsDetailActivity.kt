package com.fatherofapps.androidbase.ui.customer.news

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.databinding.ActivityNewsDetailBinding
import com.fatherofapps.androidbase.ui.customer.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityNewsDetailBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        // Nhận newId từ Intent
        val newId = intent.getStringExtra("newId")

        // Kiểm tra newId
        Log.d("ReceivedNewId", "New ID: $newId")

        newId?.let {
            viewModel.fetchExperienceUserById(it)
        }

        // Quan sát LiveData để cập nhật UI khi dữ liệu thay đổi
        viewModel.experienceUser.observe(this) { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    val experienceUser = networkResult.data
                    experienceUser?.let {
                        // Gán dữ liệu vào UI
                        dataBinding.tvTitle.text = it.title // Ví dụ: gán tiêu đề vào TextView
                        val imageUrl = it.postImages.getOrNull(0)?.urlImagePost // Lấy ảnh đầu tiên từ danh sách

                        dataBinding.tvContent2.text = it.description
                        // Nếu có URL, tải ảnh bằng Glide
                        imageUrl?.let { url ->
                            Glide.with(this)
                                .load(url)  // Tải ảnh từ URL
                                .into(dataBinding.imgNewsBackground) // Gán vào ImageView
                        }
                        // Thực hiện các thao tác khác tùy thuộc vào dữ liệu mà bạn nhận được
                    }
                    Log.d("ExperienceUser", "Received experience user: $experienceUser")
                }
                is NetworkResult.Error -> {
                    // Xử lý lỗi (nếu có)
                    Log.e("ExperienceUser", "Error: ${networkResult.exception?.message}")
                }
            }
        }

        dataBinding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

}