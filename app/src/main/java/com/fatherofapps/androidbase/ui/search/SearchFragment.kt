package com.fatherofapps.androidbase.ui.search

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.adapter.ProductAdapter
import com.fatherofapps.androidbase.adapter.ProductHorizontalAdapter
import com.fatherofapps.androidbase.base.fragment.BaseFragment
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.data.models.user.LoginRequest
import com.fatherofapps.androidbase.databinding.FragmentSearchBinding
import com.fatherofapps.androidbase.ui.customer.login.LoginViewModel
import com.fatherofapps.androidbase.ui.customer.myaccount.MyAccountViewModel
import com.fatherofapps.androidbase.ui.customer.register.RegisterViewModel
import com.fatherofapps.androidbase.ui.search.dialog.FilterAdvancedBottomSheetFragment
import com.fatherofapps.androidbase.ui.search.dialog.FilterAreaBottomSheetFragment
import com.fatherofapps.androidbase.ui.search.dialog.FilterPriceBottomSheetFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class SearchFragment : BaseFragment() {
    private lateinit var dataBinding: FragmentSearchBinding
    private val viewModel by viewModels<SearchViewModel>()
    private val viewModel1 by viewModels<RegisterViewModel>()
    private val viewModelMyInfo: MyAccountViewModel by viewModels()
    private val viewModel2 by viewModels<LoginViewModel>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var minPrice: Double? = null
    private var maxPrice: Double? = null
    private var district: String? = null
    private var type: String? = null
    private var hasPromotion: Boolean? = null
    private lateinit var titleSearch: String
    private lateinit var noResultsImage: ImageView
    private var token: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Khởi tạo FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // lấy vị trí của người dùng
        getUserLocation()
        // Nhận search query từ MainActivity
        val titleSearchMainActivity = arguments?.getString("search_query").toString()

        if (!titleSearchMainActivity.isNullOrEmpty()) {
            titleSearch = titleSearchMainActivity
            Log.d("Title_SearchFragment", "Search query received: $titleSearch")

            // Fetch dữ liệu theo từ khóa tìm kiếm
            viewModel.fetchData(titleSearch = titleSearch)
        }else{
            viewModel.fetchData(minPrice, maxPrice, district, type, hasPromotion)
        }

        // Listener cho filter dialog:
        parentFragmentManager.setFragmentResultListener("filter_request_key", this) { _, bundle ->
            // Xử lý district
            val selectedDistrict = bundle.getString("selected_address")
            if (selectedDistrict != null) {
                district = selectedDistrict
                dataBinding.titleAddress.text = district
                Log.d("District_SearchFragment", district.toString())
                viewModel.fetchData(district = district)
            }

            // Xử lý minPrice và maxPrice
            val minPriceValue = bundle.getString("min_price")?.toDoubleOrNull()
            val maxPriceValue = bundle.getString("max_price")?.toDoubleOrNull()
            if (minPriceValue != null && maxPriceValue != null) {
                minPrice = minPriceValue
                maxPrice = maxPriceValue
                Log.d("Min_Max_SearchFragment", "minPrice: $minPrice, maxPrice: $maxPrice")
                viewModel.fetchData(minPrice = minPrice, maxPrice = maxPrice)
            }
        }
    }

    private fun getUserLocation() {
        try {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
                Log.d("Location_Permission", "Location permission not granted, requesting permission.")
                return
            }

            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    val address = addresses?.get(0)?.getAddressLine(0)
                    dataBinding.titleAddress.text = address ?: "Vị trí không xác định"

                    // Log ra vị trí lấy được
                    Log.d("Location_Info", "Latitude: ${location.latitude}, Longitude: ${location.longitude}, Address: $address")
                } else {
                    Log.e("Location_Error", "Không lấy được vị trí hiện tại.")
                }
            }.addOnFailureListener { exception ->
                Log.e("Location_Error", "Lỗi khi lấy vị trí: ${exception.message}")
            }
        } catch (e: Exception) {
            Log.e("Location_Error", "Đã xảy ra lỗi trong getUserLocation(): ${e.message}")
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

        dataBinding.spinnerCategory.setOnClickListener {

//            val loginRequest = LoginRequest(
//                email = "levietanhzz108@gmail.com",
//                password = "01082003"
//            )
//
//            viewModel2.loginUser(loginRequest)
//
//            viewModel2.loginResult.observe(viewLifecycleOwner) { result ->
//                when (result) {
//                    is NetworkResult.Success -> {
//                        token = result.data.data.token
//                        Log.d("Token_SearchFragment", token.toString())
//
//
//                        // Xử lý thành công, ví dụ hiển thị thông báo thành công
//                        showNotify("Đăng nhập thành công","Thông báo")
//                    }
//                    is NetworkResult.Error -> {
//                        // Xử lý lỗi, ví dụ hiển thị thông báo lỗi
//                        showErrorMessage("Đăng ký thất bại: ${result.exception?.message ?: "Có lỗi xảy ra."}")
//                    }
//                }
//            }
//
//            // test get info user
//            viewModelMyInfo.fetchMyAccount()
//
//            viewModelMyInfo.myInfoResult.observe(viewLifecycleOwner) { result ->
//                if (result == null) {
//                    showNotify("Lấy thông tin thất bại","Thông báo")
//                }else{
//                    showNotify("Lấy thông tin thành công","Thông báo")
//                }
//            }
            // Gọi hàm đăng ký với dữ liệu cứng
//            val registerRequest = RegisterRequest(
//                email = "avanh090@gmail.com",
//                password = "01082003",
//                firstName = "Le",
//                lastName = "Anh",
//                dayOfBirth = "2003-08-01"
//            )
//
//            viewModel1.registerUser(registerRequest)
//
//            // Đăng ký observer cho registerResult
//            viewModel1.registerResult.observe(viewLifecycleOwner) { result ->
//                when (result) {
//                    is NetworkResult.Success -> {
//                        // Xử lý thành công, ví dụ hiển thị thông báo thành công
//                        showNotify("Đăng ký thành công","Thông báo")
//                    }
//                    is NetworkResult.Error -> {
//                        // Xử lý lỗi, ví dụ hiển thị thông báo lỗi
//                        showErrorMessage("Đăng ký thất bại: ${result.exception?.message ?: "Có lỗi xảy ra."}")
//                    }
//                }
//            }
        }

//        dataBinding.tvOfferProducts.setOnClickListener{
//            viewModel2.logoutUser(token.toString())
//            Log.d("Token_LogOut", token.toString())
//            viewModel2.logOutSuccess.observe(viewLifecycleOwner) { result ->
//                when (result) {
//                    true -> {
//                        // Xử lý thành công, ví dụ hiển thị thông báo thành công
//                        showNotify("Đăng xuất thành công","Thông báo")
//                    }
//                    false -> {
//                        showNotify("Đăng xuất không thành công","Thông báo")
//                    }
//                }
//            }
//        }
        dataBinding.btnFilterArea.setOnClickListener {
            openBottomSheetFilterArea()
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerAllExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(viewModel, viewLifecycleOwner)

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

    private fun handleFilteredPosts(posts: List<PromotionalPost>) {
        Log.d("Address_SearchFragment", "Handling filtered posts. Count: ${posts.size}")

        if (posts.isEmpty()) {
            // Hiển thị hình ảnh thông báo khi không có kết quả
            noResultsImage.visibility = View.VISIBLE
            dataBinding.rvProductFilter.visibility = View.GONE
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