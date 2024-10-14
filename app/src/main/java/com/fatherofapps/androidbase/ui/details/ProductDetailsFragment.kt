package com.fatherofapps.androidbase.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fatherofapps.androidbase.adapter.ImageAdapter
import com.fatherofapps.androidbase.base.fragment.BaseFragment
import com.fatherofapps.androidbase.data.models.PostImage
import com.fatherofapps.androidbase.databinding.FragmentProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentProductDetailsBinding
    private var productId : String? = null
    private val viewModel by viewModels<ProductDetailsViewModel>()
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // fetch data
        productId = arguments?.getString("productId")
        if(productId != null) {
            viewModel.fetchData(productId!!)
        } else {
            Log.d("ProductId", "productId is null")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        dataBinding = FragmentProductDetailsBinding.inflate(inflater)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerAllExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(viewModel, viewLifecycleOwner)

        viewModel.promotionalPost.observe(viewLifecycleOwner) { promotionalPost ->
            Log.d("ProductDetailsFragment", "Received promotional post: $promotionalPost")
            promotionalPost?.let {
                dataBinding.tvTitle.text = it.data.title
                dataBinding.tvDescription.text = it.data.description
                dataBinding.tvAddress.text = it.data.roomInfo.address
                dataBinding.tvPricePerMeter.text = it.data.roomInfo.type
                if (it.data.status == "active") {
                    dataBinding.txtStatus.text = "Trạng thái: Hoạt động"
                }else{
                    dataBinding.txtStatus.text = "Trạng thái: Không hoạt động"
                }

                dataBinding.txtCapacity.text = "Dung tích: ${it.data.roomInfo.capacity}"
                dataBinding.txtWidthRoom.text = "Chiều rộng: ${it.data.roomInfo.width}"
                dataBinding.txtHeightRoom.text = "Chiều dài: ${it.data.roomInfo.height}"
                dataBinding.productPrice.text = "Giá: ${it.data.pricingDetails.basePrice} VNĐ"
                dataBinding.txtFloor.text = "Tầng: ${promotionalPost.data.roomInfo.floor}"
                dataBinding.txtTotalArea.text = "Diện tích: ${it.data.roomInfo.totalArea} m²"
                dataBinding.txtNumberOfBedrooms.text = "Số phòng ngủ: ${it.data.roomInfo.numberOfBedrooms}"
                dataBinding.txtNumberOfBathrooms.text = "Số phòng tắm: ${it.data.roomInfo.numberOfBathrooms}"


                // Hiển thị hình ảnh
                setupImageCarousel(it.data.roomInfo.postImages)
            }
        }


//        // Observe promotional post data
//        viewModel.promotionalPost.observe(viewLifecycleOwner) { promotionalPost ->
//            promotionalPost?.let {
//                // Hiển thị thông tin lấy từ API lên UI
//
//                dataBinding.txtTitle.text = it.data.id
//            }
//        }

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

    private fun setupImageCarousel(postImages: List<PostImage>) {
        // Khởi tạo adapter và gán dữ liệu
//        imageAdapter = ImageAdapter(requireContext(), ArrayList(postImages.map { it.urlImagePost }))
        imageAdapter = ImageAdapter(requireActivity(), ArrayList(postImages.map { it.urlImagePost }))
        dataBinding.recyclerCarousel.adapter = imageAdapter
    }
}
