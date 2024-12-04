package com.fatherofapps.androidbase.ui.details

import android.graphics.Color
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
import com.fatherofapps.androidbase.data.models.PostImage
import com.fatherofapps.androidbase.data.models.RoomUtility
import com.fatherofapps.androidbase.databinding.FragmentProductDetailsBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint
import formatCurrencyFromString

private const val TAG = "ProductDetailsFragment"

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentProductDetailsBinding
    private var productId : String? = null
    private val viewModel by viewModels<ProductDetailsViewModel>()
    private lateinit var imageAdapter: ImageAdapter
    private var isExpanded = false
    private lateinit var pieChart: PieChart
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

        // Initialize pieChart before using it
        pieChart = dataBinding.pieChart

        viewModel.promotionalPost.observe(viewLifecycleOwner) { promotionalPost ->
            Log.d("ProductDetailsFragment", "Received promotional post: $promotionalPost")
            promotionalPost?.let {
                dataBinding.tvTitle.text = it.title
                dataBinding.tvDescription.text = it.description
                dataBinding.tvAddress.text = it.roomInfo.address
                dataBinding.tvAddress.paintFlags = dataBinding.tvAddress.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                dataBinding.tvPricePerMeter.text = it.roomInfo.type
                dataBinding.txtWaterMoney.text = "Nước: ${formatPrice().formatPriceWaterFromString(it.pricingDetails.waterCost.toString())}"
                dataBinding.txtElectricMoney.text = "Điện: ${formatPrice().formatPriceElectricFromString(it.pricingDetails.electricityCost.toString())}"
                dataBinding.tvPhoneNumber.text = "Liên hệ: ${it.contactInfo}"
                // hien gia dich vu
                val additionalFees = it.pricingDetails.additionalFees
                val additionalFeeAdapter = AdditionalFeeAdapter(additionalFees)
                Log.d("TestAdditionalFees", "Received additional fees: $additionalFees")
                dataBinding.rvAvailability.layoutManager = LinearLayoutManager(context)
                dataBinding.rvAvailability.adapter = additionalFeeAdapter

                if (it.status == "active") {
                    dataBinding.txtStatus.text = "Trạng thái: Hoạt động"
                }else{
                    dataBinding.txtStatus.text = "Trạng thái: Không hoạt động"
                }

                handleDescriptionText("- Nhà cách phố đi bộ Nguyễn Huệ Quận 1 chỉ 1,9 km di chuyển từ nhà đến trung tâm Quận 1 không quá 7 phút. Nhà mới xây dựng xong mua là có nhà ở trước tết.\n" +
                        "- Hẻm cực rộng thoáng mát, khu sang trọng, dân trí cao đường Huỳnh Tấn Phát, đoạn chân cầu Tân Thuận.\n" +
                        "- Khu vực gần vị trí cầu Thủ Thiêm 4 (Cầu Bến Nghé, sẽ khởi công trong năm 2025) rất đáng tiền để đầu tư sinh lợi.\n" +
                        "- Nhà rộng 5,4m dài 9m diện tích trên sổ công nhận 49m2 thực tế trên 50m2.\n" +
                        "- Đường trước nhà mới làm xong không lo lộ giới, quy hoạch đất ở đô thị, sổ hồng, hoàn công đầy đủ, dễ dàng công chứng, vay trả góp ngân hàng.")
                dataBinding.txtCapacity.text = "Dung tích: ${it.roomInfo.capacity}"
                dataBinding.txtWidthRoom.text = "Chiều rộng: ${it.roomInfo.width}"
                dataBinding.txtHeightRoom.text = "Chiều dài: ${it.roomInfo.height}"
                dataBinding.productPrice.text = "Giá: ${formatCurrencyFromString(it.pricingDetails.basePrice.toString())}"
                dataBinding.txtFloor.text = "Tầng: ${promotionalPost.roomInfo.floor}"
                dataBinding.txtTotalArea.text = "Diện tích: ${it.roomInfo.totalArea} m²"
                dataBinding.txtNumberOfBedrooms.text = "Số phòng ngủ: ${it.roomInfo.numberOfBedrooms}"
                dataBinding.txtNumberOfBathrooms.text = "Số phòng tắm: ${it.roomInfo.numberOfBathrooms}"

                // Hiển thị thông tin Nội thất và Tiện nghi
                displayFurnitureInfo(it.roomUtility)
                displayAmenitiesInfo(it.roomUtility)

                // show image
                setupImageCarousel(it.roomInfo.postImages)

                // setup pie char with product details
                processDataAndUpdatePieChart(it.pricingDetails.basePrice,it.pricingDetails.electricityCost, it.pricingDetails.waterCost)
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

    // chart pie info price
    private fun processDataAndUpdatePieChart(basePrice: Int, electricityCost: Int, waterCost: Int) {
        try {
            // Calculate total prices
            val totalPrices = basePrice + electricityCost + waterCost

            Log.d(TAG, "ProcessData: $totalPrices, $basePrice, $electricityCost, $waterCost")

            // Prepare pie chart entries
            val entries = ArrayList<PieEntry>()
            entries.add(PieEntry(basePrice.toFloat(), "Giá gốc"))
            entries.add(PieEntry(electricityCost.toFloat(), "Điện"))
            entries.add(PieEntry(waterCost.toFloat(), "Nước"))

            // Create pie chart dataset
            val dataSet = PieDataSet(entries, "Chi phí phòng")
            dataSet.apply {
                colors = listOf(
                    Color.rgb(64, 89, 128),   // Blue for base price
                    Color.rgb(149, 165, 124), // Green for electricity
                    Color.rgb(217, 184, 162)  // Soft orange for water
                )
                valueTextSize = 12f
                valueTextColor = Color.WHITE
            }

            // Configure pie chart
            pieChart.apply {
                description.isEnabled = false
                setUsePercentValues(true)
                setEntryLabelColor(Color.WHITE)
                setEntryLabelTextSize(12f)
                setDrawEntryLabels(true)

                legend.apply {
                    isEnabled = true
                    verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                    horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                    orientation = Legend.LegendOrientation.HORIZONTAL
                }

                // Create pie data
                val pieData = PieData(dataSet)
                data = pieData

                // Animate the chart
                animateY(1000)

                // Highlight the first entry
                highlightValue(null)

                // Refresh the chart
                invalidate()
            }

            Log.d(TAG, "Pie chart updated successfully. Total prices: $totalPrices")

        } catch (e: Exception) {
            Log.e(TAG, "processDataAndUpdatePieChart: Error updating pie chart", e)
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
