package com.fatherofapps.androidbase.ui.customer.news

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatherofapps.androidbase.adapter.ImagesAdapter
import com.fatherofapps.androidbase.base.fragment.BaseFragment
import com.fatherofapps.androidbase.databinding.FragmentNewsBinding
import com.google.android.material.slider.RangeSlider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : BaseFragment() {

    private var _binding: FragmentNewsBinding? = null
    private val dataBinding get() = _binding!!

    // Danh sách để lưu trữ các URI của ảnh đã chọn
    private val selectedImages = mutableListOf<Uri>()

    // Adapter để hiển thị ảnh trong RecyclerView
    private lateinit var imagesAdapter: ImagesAdapter

    private val pickImages = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
        uris?.let { imageUris ->
            // Thêm ảnh vào danh sách
            selectedImages.addAll(imageUris)
            // Cập nhật lại adapter để hiển thị các ảnh mới
            imagesAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Danh sách các loại phòng
        val roomTypes = listOf("Hiện đại", "Tối giản")
        val roomStyles = listOf("Căn hộ", "Phòng trọ", "Căn hộ mini", "Studio")
        val roomStatusShow = listOf("Còn phòng", "Ngừng hoạt động", "Đang thi công")

        // Tạo ArrayAdapter cho AutoCompleteTextView roomTypes
        val adapterRoomTypes = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, roomTypes)
        dataBinding.spinnerRoomType.setAdapter(adapterRoomTypes)

        // Tạo ArrayAdapter cho AutoCompleteTextView roomStyles
        val adapterRoomStyles = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, roomStyles)
        dataBinding.spinnerRoomStyle.setAdapter(adapterRoomStyles)

        val adapterRoomStatusShow = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, roomStatusShow)
        dataBinding.spinnerActive.setAdapter(adapterRoomStatusShow)

        // Cấu hình RecyclerView với LinearLayoutManager theo chiều ngang
        dataBinding.recyclerViewImages.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Tạo adapter với hàm xử lý xóa ảnh
        imagesAdapter = ImagesAdapter(selectedImages) { imageUri ->
            // Xóa ảnh khỏi danh sách và cập nhật lại adapter
            selectedImages.remove(imageUri)
            imagesAdapter.notifyDataSetChanged()
        }
        dataBinding.recyclerViewImages.adapter = imagesAdapter

        // Lắng nghe sự kiện kéo basePriceRangeSlider
        dataBinding.basePriceRangeSlider.setupPriceRangeListener(dataBinding.txtBasePrice)
        dataBinding.electricityCostRangeSlider.setupPriceRangeListener(dataBinding.txtElectricCost)
        dataBinding.waterCostRangeSlider.setupPriceRangeListener(dataBinding.txtWaterCost)

        dataBinding.selectedAddImage.setOnClickListener {
            pickImages.launch("image/*")
        }

        dataBinding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun RangeSlider.setupPriceRangeListener(priceTextView: TextView) {
        this.addOnChangeListener { slider, _, _ ->
            if (slider.values.isNotEmpty()) {
                val selectedValue = slider.values[0]

                // Format giá trị để hiển thị với dấu phẩy và đơn vị VND
                val formattedPrice = String.format("%,d", selectedValue.toInt())

                // Cập nhật TextView để hiển thị giá trị đã chọn
                val priceText = "$formattedPrice VND"
                priceTextView.text = priceText
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
