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

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        registerAllExceptionEvent(viewModel, viewLifecycleOwner)
//        registerObserverLoadingEvent(viewModel, viewLifecycleOwner)
//
//        viewModel.postPromotional.observe(viewLifecycleOwner) { postPromotional ->
//            // Kiểm tra nếu postPromotional không phải là null
//            postPromotional?.let {
//                // Cập nhật UI với dữ liệu từ PostData
//                dataBinding.txtResult.text = it.data.toString()// hoặc bất kỳ trường nào bạn muốn hiển thị
//                // Nếu bạn có nhiều trường muốn hiển thị, bạn có thể thêm vào đây
//                // dataBinding.txtDescription.text = it.description
//                // Hoặc bất kỳ trường nào khác từ PostData
//            } ?: run {
//                // Xử lý trường hợp không có dữ liệu
//                dataBinding.txtResult.text = "Không có dữ liệu"
//            }
//        }
//    }
//override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//    registerAllExceptionEvent(viewModel, viewLifecycleOwner)
//    registerObserverLoadingEvent(viewModel, viewLifecycleOwner)

//    viewModel.postPromotional.observe(viewLifecycleOwner) { postPromotional ->
//        // Kiểm tra nếu postPromotional không phải là null
//        postPromotional?.let {
//            // Cập nhật UI với dữ liệu từ PostData
//            dataBinding.txtResult.text = it.data.toString() // hoặc bất kỳ trường nào bạn muốn hiển thị
//
//            // Lấy danh sách PostImage từ dữ liệu
//            val postImages = it.data[0].roomInfo.postImages
//
//            // Hiển thị PostImage trong RecyclerView (giả sử bạn đã có RecyclerView)
//            setupRecyclerView(postImages)
//        } ?: run {
//            // Xử lý trường hợp không có dữ liệu
//            dataBinding.txtResult.text = "Không có dữ liệu"
//        }
////    }
//    viewModel.postPromotional.observe(viewLifecycleOwner) { postPromotional ->
//        postPromotional?.let {
//            val image = it.data[0].roomInfo.postImages
//            val adapter = PostImageAdapter(image) // Lấy danh sách hình ảnh từ Post
//            dataBinding.recyclerView.adapter = adapter // Gán adapter cho RecyclerView
//            Log.d("Hinh anh", listOf(image).toString())
//        } ?: run {
//            dataBinding.txtResult.text = "Không có dữ liệu"
//        }
//    }
//
//}

//    // Hàm setup RecyclerView để hiển thị PostImage
//    private fun setupRecyclerView(postImages: List<PostImage>) {
//        // Giả sử bạn đã định nghĩa RecyclerView trong layout của bạn và tạo Adapter
//        val adapter = PostImageAdapter(postImages)
//        dataBinding.recyclerView.adapter = adapter
//        dataBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
//    }
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    registerAllExceptionEvent(viewModel, viewLifecycleOwner)
    registerObserverLoadingEvent(viewModel, viewLifecycleOwner)

    viewModel.postPromotional.observe(viewLifecycleOwner) { postPromotional ->
        postPromotional?.let {
            // Lấy danh sách hình ảnh từ PostImage
            val postImages = it.data[0].roomInfo.postImages

            setupRecyclerView(postImages) // Gọi hàm setupRecyclerView
        } ?: run {
            dataBinding.txtResult.text = "Không có dữ liệu"
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