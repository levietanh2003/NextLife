package com.fatherofapps.androidbase.ui.customer.news

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatherofapps.androidbase.adapter.AdditionalFeeNewsAdapter
import com.fatherofapps.androidbase.adapter.ImagesAdapter
import com.fatherofapps.androidbase.base.activities.BaseActivity
import com.fatherofapps.androidbase.base.dialogs.ConfirmDialog
import com.fatherofapps.androidbase.data.models.AdditionalFee
import com.fatherofapps.androidbase.data.models.PostImage
import com.fatherofapps.androidbase.data.models.PricingDetails
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.data.models.RoomInfo
import com.fatherofapps.androidbase.data.models.RoomUtility
import com.fatherofapps.androidbase.databinding.FragmentNewsBinding
import com.google.android.material.slider.RangeSlider
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsFragment : BaseActivity(), AdditionalFeeNewsAdapter.OnFeeUpdateListener {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var additionalFeeNewsAdapter: AdditionalFeeNewsAdapter
    private val postImages = mutableListOf<PostImage>()


    private val dummyFees = mutableListOf(
        AdditionalFee("Phí vệ sinh", 100),
        AdditionalFee("Phí bảo trì", 200),
        AdditionalFee("Phí gửi xe", 50),
        AdditionalFee("Phí nuôi thú", 50),
        AdditionalFee("Phí an ninh", 50),
        AdditionalFee("Phí an ninh", 50),
        AdditionalFee("Phí an ninh", 50)

    )

    // Danh sách để lưu trữ các URI của ảnh đã chọn
    private val selectedImages = mutableListOf<Uri>()

    // Adapter để hiển thị ảnh trong RecyclerView
    private lateinit var imagesAdapter: ImagesAdapter

//    private val pickImages = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
//        uris?.let { imageUris ->
//            // Thêm ảnh vào danh sách
//            selectedImages.addAll(imageUris)
//            // Cập nhật lại adapter để hiển thị các ảnh mới
//            imagesAdapter.notifyDataSetChanged()
//        }
//    }
    private val pickImages = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
        uris?.let { imageUris ->
            imageUris.forEach { uri ->
                val fileName = uri.lastPathSegment ?: "Unknown"
                val fileType = contentResolver.getType(uri) ?: "Unknown"
                val fileUrl = uri.toString()

                // Tạo đối tượng PostImage
                val postImage = PostImage(
                    name = fileName,
                    type = fileType,
                    urlImagePost = fileUrl
                )

                // Lưu vào danh sách postImages
                postImages.add(postImage)
            }

            // Thêm ảnh vào danh sách URI để hiển thị
            selectedImages.addAll(imageUris)
            imagesAdapter.notifyDataSetChanged()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Danh sách các loại phòng
        val roomTypes = listOf("Hiện đại", "Tối giản")
        val roomStyles = listOf("Căn hộ", "Phòng trọ", "Căn hộ mini", "Studio")
        val roomStatusShow = listOf("Còn phòng", "Ngừng hoạt động", "Đang thi công")

        // Tạo ArrayAdapter cho AutoCompleteTextView roomTypes
        val adapterRoomTypes = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, roomTypes)
        binding.spinnerRoomType.setAdapter(adapterRoomTypes)

        // Tạo ArrayAdapter cho AutoCompleteTextView roomStyles
        val adapterRoomStyles = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, roomStyles)
        binding.spinnerRoomStyle.setAdapter(adapterRoomStyles)

        val adapterRoomStatusShow = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, roomStatusShow)
        binding.spinnerActive.setAdapter(adapterRoomStatusShow)

        // Cấu hình RecyclerView với LinearLayoutManager theo chiều ngang
        binding.recyclerViewImages.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Tạo adapter với hàm xử lý xóa ảnh
        imagesAdapter = ImagesAdapter(selectedImages) { imageUri ->
            // Xóa ảnh khỏi danh sách và cập nhật lại adapter
            selectedImages.remove(imageUri)
            imagesAdapter.notifyDataSetChanged()
        }

        // set up dich vu adapter
        additionalFeeNewsAdapter = AdditionalFeeNewsAdapter(dummyFees, this)

        // Gán adapter vào RecyclerView
        binding.recyclerServices.layoutManager = LinearLayoutManager(this)
        binding.recyclerServices.adapter = additionalFeeNewsAdapter
        binding.recyclerViewImages.adapter = imagesAdapter

        // Lắng nghe sự kiện kéo basePriceRangeSlider
        binding.basePriceRangeSlider.setupPriceRangeListener(binding.txtBasePrice)
        binding.electricityCostRangeSlider.setupPriceRangeListener(binding.txtElectricCost)
        binding.waterCostRangeSlider.setupPriceRangeListener(binding.txtWaterCost)

        binding.selectedAddImage.setOnClickListener {
            pickImages.launch("image/*")
        }

        binding.btnPostNew.setOnClickListener {
            validateInputUser(true)
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
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

    private fun validateInputUser(isValidate: Boolean) {
        // Lấy dữ liệu từ các trường nhập liệu
        val titlePostNew = binding.edtTitle.text.toString().trim()
        val address = binding.edtAddress.text.toString().trim()
        val phone = binding.edtPhone.text.toString().trim()
        val descriptionTitle = binding.edtIntroPostNews.text.toString().trim()
        val roomType = binding.spinnerRoomType.text.toString().trim()
        val roomStyle = binding.spinnerRoomStyle.text.toString().trim()
        val active = binding.spinnerActive.text.toString().trim()
        val length = binding.edtLength.text.toString().trim()
        val width = binding.edtWidth.text.toString().trim()
        val capacity = binding.edtCapacity.text.toString().trim()
        val descriptionRoom = binding.edtIntroRoom.text.toString().trim()
        val numberOfBathRoom = binding.spinnerNumberOfBathRoom.text.toString().trim().toInt()
        val numberOfBedRoom = binding.spinnerNumberOfBedRoom.text.toString().trim().toInt()
        val numberFloor = binding.edtFloor.text.toString().trim().toInt()
        val nameRoom = binding.edtNameRoom.text.toString().trim()

        // Kiểm tra giá trị của các slider không được là 0
        val basePrice = binding.basePriceRangeSlider.values.firstOrNull() ?: 0f
        val electricCost = binding.electricityCostRangeSlider.values.firstOrNull() ?: 0f
        val waterCost = binding.waterCostRangeSlider.values.firstOrNull() ?: 0f

        // Biến cờ để theo dõi trạng thái hợp lệ
        var isValid = true

        // Kiểm tra từng trường
        if (titlePostNew.isEmpty()) {
            isValid = false
            binding.edtTitle.error = "Nhập tiêu đề"
        }

        if (numberFloor <= 0) {
            isValid = false
            binding.edtFloor.error = "Kiểm tra số tầng"
        }

        if (address.isEmpty()) {
            isValid = false
            binding.edtAddress.error = "Địa chỉ không được để trống"
        }

        if(nameRoom.isEmpty()){
            isValid = false
            binding.edtNameRoom.error = "Nhập tên phòng"
        }

        if (phone.isEmpty() || !phone.matches(Regex("^\\d{10,11}\$"))) {
            isValid = false
            binding.edtPhone.error = "Số điện thoại không hợp lệ (10-11 chữ số)"
        }

        if (descriptionTitle.isEmpty()) {
            isValid = false
        }
        if (descriptionRoom.isEmpty()) {
            isValid = false
        }


        if (length.isEmpty() || length.toFloatOrNull() == null || length.toFloat() <= 0) {
            isValid = false
            binding.edtLength.error = "Chiều dài không hợp lệ"
        }

        if (width.isEmpty() || width.toFloatOrNull() == null || width.toFloat() <= 0) {
            isValid = false
            binding.edtWidth.error = "Chiều rộng không hợp lệ"
        }

        if (capacity.isEmpty() || capacity.toIntOrNull() == null || capacity.toInt() <= 0) {
            isValid = false
            binding.edtCapacity.error = "Nhập số người ở"
        }

        if (basePrice <= 0) {
            isValid = false
            binding.txtBasePrice.error = "Giá cơ bản không được để 0 VND"
        }

        if (electricCost <= 0) {
            isValid = false
            binding.txtElectricCost.error = "Chi phí điện không được để 0 VND"
        }

        if (waterCost <= 0) {
            isValid = false
            binding.txtWaterCost.error = "Chi phí nước không được để 0 VND"
        }

        // Kiểm tra danh sách ảnh đã chọn
        if (selectedImages.isEmpty()) {
            isValid = false
            showNotifyDialog("Vui lòng chọn ít nhất 1 ảnh!", "Thông báo","OK")
        }

        // Hiển thị thông báo nếu không hợp lệ
        if (!isValid) {
            showNotifyDialog("Vui lòng kiểm tra và nhập đầy đủ thông tin!", "Thông báo","OK")
        }

        // Xử lý kết quả kiểm tra
        if (isValidate && isValid) {
            val totalArea = length.toDouble() * width.toDouble()
            // Nếu hợp lệ, thực hiện các hành động tiếp theo
            val availableFromDate = (System.currentTimeMillis() / 1000).toDouble()
            val product =
                PromotionalPost(
                    roomId = "",
                    title = titlePostNew,
                    description =  descriptionTitle,
                    roomInfo = RoomInfo(
                        name = nameRoom,
                        address = address,
                        description = descriptionRoom,
                        style = roomStyle,
                        type = roomType,
                        height = length.toDouble(),
                        width = width.toDouble(),
                        capacity = capacity.toInt(),
                        totalArea = totalArea,
                        floor = numberFloor.toString(),
                        numberOfBedrooms = numberOfBedRoom,
                        numberOfBathrooms = numberOfBathRoom,
                        availableFromDate = availableFromDate,
                        postImages = postImages,
                        ),
                    roomUtility = RoomUtility(
                        amenitiesAvailability = mapOf(),
                        furnitureAvailability = mapOf()
                    ),
                    createdBy = "",
                    statusShow = active,
                    contactInfo = phone,
                    additionalDetails = "",
                    pricingDetails = PricingDetails(
                        basePrice = basePrice.toInt(),
                        electricityCost = electricCost.toInt(),
                        waterCost = waterCost.toInt(),
                        additionalFees = dummyFees
                    ),
                    fixPrice = 0,
                    modifiedBy = "",
                    id = null,
                    lastModifiedDate = availableFromDate,
                    createdDate = availableFromDate,
                    status = "pending",
                    created = "")

            Log.d("product", "validateInputUser: $product")

            performPostNew()

        } else if (!isValidate) {
            // Nếu không cần validate, thực hiện hành động tiếp theo
            // (Ví dụ: lưu tạm dữ liệu mà không cần kiểm tra)
        }
    }

//    override fun onFeeUpdated(position: Int, updatedFee: AdditionalFee) {
//        // Update the fee in the list and notify the adapter
//        val adapter = binding.recyclerServices.adapter as? AdditionalFeeAdapter
//        adapter?.let {
//            it.notifyItemChanged(position)
//        }
//    }

    override fun onFeeUpdated(position: Int, updatedFee: AdditionalFee) {
        // Update the specific fee in the list
        if (position in dummyFees.indices) {
            dummyFees[position] = updatedFee

            // Notify the adapter that the item has changed
            additionalFeeNewsAdapter.notifyItemChanged(position)
        }
    }

    private fun performPostNew() {
        // Hiển thị hộp thoại xác nhận
        val confirmDialog = ConfirmDialog(
            context = this,
            callback = object : ConfirmDialog.ConfirmCallback {
                override fun negativeAction() {
                    // Người dùng nhấn "Hủy", không thực hiện hành động gì
                }

                override fun positiveAction() {
                    // Người dùng nhấn "Đồng ý", tiến hành đăng xuất
                    showNotifyDialog("Đăng bài thành công", "Thông báo","OK")
                }
            },
            title = "Xác nhận",
            message = "Bạn muốn đăng bài không?",
            positiveButtonTitle = "Đồng ý",
            negativeButtonTitle = "Hủy"
        )
        confirmDialog.show()
    }
}
