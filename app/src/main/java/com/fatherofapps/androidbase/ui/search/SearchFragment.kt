package com.fatherofapps.androidbase.ui.search

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.adapter.ProductAdapter
import com.fatherofapps.androidbase.adapter.ProductHorizontalAdapter
import com.fatherofapps.androidbase.base.fragment.BaseFragment
import com.fatherofapps.androidbase.common.Logger
import com.fatherofapps.androidbase.common.showBottomNavigation
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.databinding.FragmentSearchBinding
import com.fatherofapps.androidbase.ui.search.dialog.FilterAdvancedBottomSheetFragment
import com.fatherofapps.androidbase.ui.search.dialog.FilterAreaBottomSheetFragment
import com.fatherofapps.androidbase.ui.search.dialog.FilterPriceBottomSheetFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

/**
 * Fragment for handling the search functionality in the application.
 *
 * This fragment allows users to:
 * - Search for products or posts based on various filters such as price, area, and other advanced filters.
 * - Retrieve and display results dynamically from a ViewModel.
 * - Display a message or image if no results are found.
 *
 * @constructor This fragment requires `FragmentSearchBinding` for UI interactions and uses `SearchViewModel` to fetch and manage data.
 */
@AndroidEntryPoint
class SearchFragment : BaseFragment() {
    private lateinit var dataBinding: FragmentSearchBinding
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var minPrice: Double? = null
    private var maxPrice: Double? = null
    private var district: String? = null
    private var type: Int? = null
    private var hasPromotion: Boolean? = null
    private lateinit var titleSearch: String
    private lateinit var noResultsImage: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Khởi tạo FusedLocationProviderClient
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // lấy vị trí của người dùng
//        getUserLocation()

        // Handle search query
        arguments?.getString("search_query")?.takeIf { it.isNotEmpty() }?.let { searchQuery ->
            titleSearch = searchQuery
            Log.d("Title_SearchFragment", "Search query received: $titleSearch")
            viewModel.fetchData(titleSearch = titleSearch)
        } ?: run {
            // If no search query, fetch data with existing filters
            viewModel.fetchData(minPrice, maxPrice, district, 0, hasPromotion)
        }

        // Fragment result listener for filters
        parentFragmentManager.setFragmentResultListener("filter_request_key", this) { _, bundle ->
            handleFilterResults(bundle)
        }
    }

    /**
     * Retrieves the user's current location using `FusedLocationProviderClient`.
     *
     * - If location permission is not granted, it requests the necessary permissions.
     * - Updates the displayed address based on the location.
     * @throws Exception if an error occurs during location retrieval.
     */
    private fun getUserLocation() {
        try {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )

                Logger.log("Location_Permission", "Location permission not granted, requesting permission.")
                return
            }

            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    val address = addresses?.get(0)?.getAddressLine(0)
                    dataBinding.titleAddress.text = address ?: "Vị trí không xác định"

                    // Log ra vị trí lấy được
                    Logger.log("Location_Info", "Latitude: ${location.latitude}, Longitude: ${location.longitude}, Address: $address")
                } else {
                    Logger.log("Location_Error", "Không lấy được vị trí hiện tại.")
                }
            }.addOnFailureListener { exception ->
                Logger.log("Location_Error", "Lỗi khi lấy vị trí: ${exception.message}")
            }
        } catch (e: Exception) {
            Logger.log("Location_Error", "Đã xảy ra lỗi trong getUserLocation(): ${e.message}")
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentSearchBinding.inflate(inflater)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = viewModel


        noResultsImage = dataBinding.noResultsImage

        dataBinding.btnFilterPrice.setOnClickListener{
            openBottomSheetFilterPrice()
        }


        dataBinding.btnFilterAdvanced.setOnClickListener {
            openBottomSheetFilterAdvanced()
        }


        dataBinding.btnFilterArea.setOnClickListener {
            openBottomSheetFilterArea()
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerAllExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(viewModel, viewLifecycleOwner)

        // Tạo adapter cho Spinner sắp xếp
        val sortOptions = arrayOf(
            "Mặc định",
            "Thấp đến Cao",
            "Cao đến Thấp"
        )

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            sortOptions
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dataBinding.spinnerSort.adapter = spinnerAdapter

        val spinnerCategory = arrayOf(
            "Căn hộ",
            "Căn hộ mini",
            "Căn hộ dịch vụ",
            "Phòng trọ"
        )

        val spinnerCategoryAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinnerCategory
        )
        spinnerCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dataBinding.spinnerCategory.adapter = spinnerCategoryAdapter

        dataBinding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        // filter by "Căn hộ"
                        viewModel.fetchData(type = 1)
                    }
                    1 -> {
                        // filter by "Căn hộ mini
                        viewModel.fetchData(type = 2)
                    }
                    2 -> {
                        // filter by "Căn hộ dịch vụ"
                        viewModel.fetchData(type = 3)
                    }
                    3 -> {
                        // filter by "Phòng trọ"
                        viewModel.fetchData(type = 4)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Lắng nghe sự kiện chọn spinner sắp xếp theo giá
        dataBinding.spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        // Mặc định - load lại dữ liệu gốc
                        viewModel.fetchData(
                            minPrice = minPrice,
                            maxPrice = maxPrice,
                            district = district,
                            type = type,
                            hasPromotion = hasPromotion
                        )
                    }
                    1 -> {
                        // Sắp xếp giá tăng dần
                        viewModel.sortProductsByPrice(true)
                    }
                    2 -> {
                        // Sắp xếp giá giảm dần
                        viewModel.sortProductsByPrice(false)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        dataBinding.btnFilterAdvanced.setOnClickListener {
            openBottomSheetFilterAdvanced()
        }

        viewModel.getPost.observe(viewLifecycleOwner) { posFilter ->
            Log.d("Address_SearchFragment", "Received filtered posts. Count: ${posFilter.size}")
            handleFilteredPosts(posFilter)
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

    private fun openBottomSheetFilterArea() {
        val bottomSheetFragment = FilterAreaBottomSheetFragment()
        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
    }

    private fun openBottomSheetFilterPrice() {
        val bottomSheetFragment = FilterPriceBottomSheetFragment()
        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
    }

    private fun openBottomSheetFilterAdvanced() {
        val bottomSheetFragment = FilterAdvancedBottomSheetFragment()
        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
    }

    private fun handleFilterResults(bundle: Bundle) {
        // Handle district filter
        bundle.getString("selected_address")?.let { selectedDistrict ->
            district = selectedDistrict
            dataBinding.titleAddress.text = district
            Log.d("District_SearchFragment", district.toString())
            viewModel.fetchData(district = district)
        }

        // Handle price filters
        val minPriceValue = bundle.getString("min_price")?.toDoubleOrNull()
        val maxPriceValue = bundle.getString("max_price")?.toDoubleOrNull()
        if (minPriceValue != null && maxPriceValue != null) {
            minPrice = minPriceValue
            maxPrice = maxPriceValue
            Log.d("Min_Max_SearchFragment", "minPrice: $minPrice, maxPrice: $maxPrice")
            viewModel.fetchData(minPrice = minPrice, maxPrice = maxPrice)
        }

        // Handle advanced filters
        val minPriceAdvanced = bundle.getString("min_price_Advanced")?.toDoubleOrNull()
        val maxPriceAdvanced = bundle.getString("max_price_Advanced")?.toDoubleOrNull()
        val districtAdvanced = bundle.getString("selected_district_Advanced")
        val categoryAdvanced = bundle.getString("category_Advanced")
        val hasPromotionAdvanced = bundle.getBoolean("has_promotion_Advanced")

        if (minPriceAdvanced != null && maxPriceAdvanced != null &&
            !districtAdvanced.isNullOrEmpty() && !categoryAdvanced.isNullOrEmpty()) {
            if(categoryAdvanced == "Căn hộ"){
                type = 1
            }else if(categoryAdvanced == "Căn hộ mini"){
                type = 2
            }
            minPrice = minPriceAdvanced
            maxPrice = maxPriceAdvanced
            district = districtAdvanced
            type = type
            hasPromotion = hasPromotionAdvanced

            Log.d("FillAdvanced", "minPrice: $minPrice, maxPrice: $maxPrice, " +
                    "district: $district, type: $type, hasPromotion: $hasPromotion")

            viewModel.fetchData(
                minPrice = minPrice,
                maxPrice = maxPrice,
                type = type,
                district = district,
                hasPromotion = hasPromotion
            )
        }
    }


    /**
     * Handles the filtered posts by updating the UI.
     *
     * - Displays the results in a RecyclerView.
     * - Shows a message if no results are found.
     *
     * @param posts The list of filtered posts to display.
     */
    private fun handleFilteredPosts(posts: List<PromotionalPost>) {
        Log.d("Address_SearchFragment", "Handling filtered posts. Count: ${posts.size}")

        if (posts.isEmpty()) {
            // Hiển thị hình ảnh thông báo khi không có kết quả
            noResultsImage.visibility = View.VISIBLE
            dataBinding.rvProductFilter.visibility = View.GONE
            showNotify("Không có sản phẩm","Thông báo")
        } else {
            // Ẩn hình ảnh thông báo khi có kết quả
            noResultsImage.visibility = View.GONE
            dataBinding.rvProductFilter.visibility = View.VISIBLE

            val productAdapter = ProductHorizontalAdapter(
                posts.map { it.title },
                posts.map { it.pricingDetails.basePrice.toString() },
                posts.map { it.roomInfo.postImages.getOrNull(0)?.urlImagePost ?: "" },
                posts.map { it.roomInfo.address },
                posts.map { it.lastModifiedDate },
                posts.map { it.roomInfo.postImages.size },
                requireContext(),
                object : ProductAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        val productId = posts[position].id
                        val bundle = Bundle().apply {
                            putString("productId", productId)
                        }
                        findNavController().navigate(
                            R.id.action_searchFragment_to_productDetailsFragment, bundle
                        )
                    }
                }
            )

            dataBinding.rvProductFilter.apply {
                layoutManager = GridLayoutManager(requireContext(), 1)
                adapter = productAdapter
            }
        }
    }
}