package com.fatherofapps.androidbase.ui.details

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatherofapps.androidbase.adapter.AdditionalFeeAdapter
import com.fatherofapps.androidbase.adapter.ImageAdapter
import com.fatherofapps.androidbase.base.fragment.BaseFragment
import com.fatherofapps.androidbase.common.formatPrice
import com.fatherofapps.androidbase.common.hideBottomNavigation
import com.fatherofapps.androidbase.data.models.PostImage
import com.fatherofapps.androidbase.data.models.RoomUtility
import com.fatherofapps.androidbase.databinding.FragmentProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import formatCurrencyFromString

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentProductDetailsBinding
    private var productId : String? = null
    private val viewModel by viewModels<ProductDetailsViewModel>()
    private lateinit var imageAdapter: ImageAdapter
    private var isExpanded = false
    private val MAX_WORD_COUNT = 50

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

        dataBinding.btnToggle.paintFlags = dataBinding.btnToggle.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        return dataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerAllExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(viewModel, viewLifecycleOwner)

        hideBottomNavigation()

        viewModel.promotionalPost.observe(viewLifecycleOwner) { promotionalPost ->
            Log.d("ProductDetailsFragment", "Received promotional post: $promotionalPost")
            promotionalPost?.let {
                dataBinding.tvTitle.text = it.data.title
                dataBinding.tvDescription.text = it.data.description
                dataBinding.tvAddress.text = it.data.roomInfo.address
                dataBinding.tvAddress.paintFlags = dataBinding.tvAddress.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                dataBinding.tvPricePerMeter.text = it.data.roomInfo.type
                dataBinding.txtWaterMoney.text = "Nước: ${formatPrice().formatPriceWaterFromString(it.data.pricingDetails.waterCost.toString())}"
                dataBinding.txtElectricMoney.text = "Điện: ${formatPrice().formatPriceElectricFromString(it.data.pricingDetails.electricityCost.toString())}"
                dataBinding.tvPhoneNumber.text = "Liên hệ: ${it.data.contactInfo}"
                // hien gia dich vu
                val additionalFees = it.data.pricingDetails.additionalFees
                val additionalFeeAdapter = AdditionalFeeAdapter(additionalFees)
                Log.d("TestAdditionalFees", "Received additional fees: $additionalFees")
                dataBinding.rvAvailability.layoutManager = LinearLayoutManager(context)
                dataBinding.rvAvailability.adapter = additionalFeeAdapter

                if (it.data.status == "active") {
                    dataBinding.txtStatus.text = "Trạng thái: Hoạt động"
                }else{
                    dataBinding.txtStatus.text = "Trạng thái: Không hoạt động"
                }

                handleDescriptionText("- Nhà cách phố đi bộ Nguyễn Huệ Quận 1 chỉ 1,9 km di chuyển từ nhà đến trung tâm Quận 1 không quá 7 phút. Nhà mới xây dựng xong mua là có nhà ở trước tết.\n" +
                        "- Hẻm cực rộng thoáng mát, khu sang trọng, dân trí cao đường Huỳnh Tấn Phát, đoạn chân cầu Tân Thuận.\n" +
                        "- Khu vực gần vị trí cầu Thủ Thiêm 4 (Cầu Bến Nghé, sẽ khởi công trong năm 2025) rất đáng tiền để đầu tư sinh lợi.\n" +
                        "- Nhà rộng 5,4m dài 9m diện tích trên sổ công nhận 49m2 thực tế trên 50m2.\n" +
                        "- Đường trước nhà mới làm xong không lo lộ giới, quy hoạch đất ở đô thị, sổ hồng, hoàn công đầy đủ, dễ dàng công chứng, vay trả góp ngân hàng.")
                dataBinding.txtCapacity.text = "Dung tích: ${it.data.roomInfo.capacity}"
                dataBinding.txtWidthRoom.text = "Chiều rộng: ${it.data.roomInfo.width}"
                dataBinding.txtHeightRoom.text = "Chiều dài: ${it.data.roomInfo.height}"
                dataBinding.productPrice.text = "Giá: ${formatCurrencyFromString(it.data.pricingDetails.basePrice.toString())}"
                dataBinding.txtFloor.text = "Tầng: ${promotionalPost.data.roomInfo.floor}"
                dataBinding.txtTotalArea.text = "Diện tích: ${it.data.roomInfo.totalArea} m²"
                dataBinding.txtNumberOfBedrooms.text = "Số phòng ngủ: ${it.data.roomInfo.numberOfBedrooms}"
                dataBinding.txtNumberOfBathrooms.text = "Số phòng tắm: ${it.data.roomInfo.numberOfBathrooms}"

                // Hiển thị thông tin Nội thất và Tiện nghi
                displayFurnitureInfo(it.data.roomUtility)
                displayAmenitiesInfo(it.data.roomUtility)

                // Hiển thị hình ảnh
                setupImageCarousel(it.data.roomInfo.postImages)
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

    private fun setupImageCarousel(postImages: List<PostImage>) {
        // Khởi tạo adapter và gán dữ liệu
        imageAdapter = ImageAdapter(requireActivity(), ArrayList(postImages.map { it.urlImagePost }))
        dataBinding.recyclerCarousel.adapter = imageAdapter
    }

    private fun displayFurnitureInfo(roomUtility: RoomUtility) {
        val furnitureBuilder = StringBuilder()
        roomUtility.furnitureAvailability.forEach { (furniture, available) ->
            furnitureBuilder.append("$furniture: ${if (available) "Có" else "Không"}\n")
        }
        dataBinding.tvFurnitureAvailability.text = furnitureBuilder.toString()
    }

    private fun displayAmenitiesInfo(roomUtility: RoomUtility) {
        val amenitiesBuilder = StringBuilder()
        roomUtility.amenitiesAvailability.forEach { (amenity, available) ->
            amenitiesBuilder.append("$amenity: ${if (available) "Có" else "Không"}\n")
        }
        dataBinding.tvAmenitiesAvailability.text = amenitiesBuilder.toString()
    }

    private fun handleDescriptionText(description: String) {
        val words = description.split(" ")

        if (words.size > MAX_WORD_COUNT) {
            val shortText = words.take(MAX_WORD_COUNT).joinToString(" ") + "..."
            dataBinding.txtDetailedDescription.text = shortText

            dataBinding.btnToggle.visibility = View.VISIBLE
            dataBinding.btnToggle.setOnClickListener {
                isExpanded = !isExpanded
                toggleDescription(description, shortText)
            }
        } else {
            dataBinding.txtDetailedDescription.text = description
            dataBinding.btnToggle.visibility = View.GONE
        }
    }

    private fun toggleDescription(fullText: String, shortText: String) {
        if (isExpanded) {
            dataBinding.txtDetailedDescription.text = fullText
            dataBinding.btnToggle.text = "Thu gọn"
        } else {
            dataBinding.txtDetailedDescription.text = shortText
            dataBinding.btnToggle.text = "Xem thêm"
        }
    }
}
