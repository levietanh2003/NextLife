package com.fatherofapps.androidbase.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatherofapps.androidbase.adapter.PostImageAdapter
import com.fatherofapps.androidbase.base.fragment.BaseFragment
import com.fatherofapps.androidbase.data.models.PostImage
import com.fatherofapps.androidbase.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {


    private lateinit var dataBinding: FragmentHomeBinding

    // anh xa HomeViewModel
    private val viewModel by viewModels<HomeViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // khi vua khoi tao se fetch data len
        viewModel.fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentHomeBinding.inflate(inflater)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    registerAllExceptionEvent(viewModel, viewLifecycleOwner)
    registerObserverLoadingEvent(viewModel, viewLifecycleOwner)

    // Quan sát sự thay đổi trong postPromotional
    viewModel.postPromotional.observe(viewLifecycleOwner) { postPromotional ->
        postPromotional?.let {
            // Lấy danh sách hình ảnh từ PostImage
            val postImages = it.data[0].roomInfo.postImages
            setupRecyclerView(postImages) // Gọi hàm setupRecyclerView
        } ?: run {
            dataBinding.txtResult.text = "Không có dữ liệu"
        }
    }

    // quan sat loading
    viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
        if (isLoading?.peekContent() == true) {
            // Hiển thị loading indicator
            showLoading(true)
        } else {
            // Ẩn loading indicator
            showLoading(false)
        }
    }
}

    // Hàm setup RecyclerView để hiển thị PostImage
    private fun setupRecyclerView(postImages: List<PostImage>) {
        val adapter = PostImageAdapter(postImages) // Tạo adapter với danh sách hình ảnh
        dataBinding.recyclerView.adapter = adapter // Gán adapter cho RecyclerView
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext()) // Định nghĩa LayoutManager
    }
}