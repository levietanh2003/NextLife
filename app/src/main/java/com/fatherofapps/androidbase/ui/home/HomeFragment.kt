package com.fatherofapps.androidbase.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.adapter.ProductAdapter
import com.fatherofapps.androidbase.base.fragment.BaseFragment
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var dataBinding: FragmentHomeBinding
    // anh xa HomeViewModel
    private val viewModel by viewModels<HomeViewModel>()
    private var promotionalPostsList: List<PromotionalPost> = emptyList()
    private var featuredPostsList: List<PromotionalPost> = emptyList()
    // Tạo các danh sách rỗng để chứa dữ liệu
    private val productNames = mutableListOf<String>()
    private val productPrices = mutableListOf<String>()
    private val productImages = mutableListOf<String>()
    private val productAddress = mutableListOf<String>()
    private val productLastModified = mutableListOf<Double>()
    private val productQuantityImage = mutableListOf<Int>()

    private val productNamesFeatured = mutableListOf<String>()
    private val productPricesFeatured = mutableListOf<String>()
    private val productImagesFeatured = mutableListOf<String>()
    private val productAddressFeatured = mutableListOf<String>()
    private val productLastModifiedFeatured = mutableListOf<Double>()
    private val productQuantityImageFeatured = mutableListOf<Int>()


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

        dataBinding.btnTypeOfDishBreads.setOnClickListener {
            // Điều hướng đến SearchFragment
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
        return dataBinding.root
    }


override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    registerAllExceptionEvent(viewModel, viewLifecycleOwner)
    registerObserverLoadingEvent(viewModel, viewLifecycleOwner)

    // luu danh sach image vao slider
    val imageListt = ArrayList<SlideModel>()
    imageListt.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
    imageListt.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
    imageListt.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

    val imagesSlider = dataBinding.imageSlider
    imagesSlider.setImageList(imageListt)
    imagesSlider.setImageList(imageListt, ScaleTypes.FIT)


    dataBinding.btnTypeOfDishBreads.setOnClickListener{
        findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
    }

    viewModel.postFeatured.observe(viewLifecycleOwner) { featuredPosts ->
        featuredPosts?.let {
            featuredPostsList = featuredPostsList + it
            Log.d("SizeList_Future", featuredPostsList.size.toString())

            for (post in it) {
                val title = post.title
                val basePrice = post.pricingDetails.basePrice
                val images = post.roomInfo.postImages.map { image -> image.urlImagePost }
                val address = post.roomInfo.address
                val lastModified = post.lastModifiedDate
                val quantityImage = images.size


                // Thêm dữ liệu vào các danh sách
                productNamesFeatured.add(title)
                productPricesFeatured.add(basePrice.toString())
                productAddressFeatured.add(address)
                productLastModifiedFeatured.add(lastModified)
                productQuantityImageFeatured.add(quantityImage)
                // Chuyển basePrice thành chuỗi nếu cần
                if (images.isNotEmpty()) {
                    productImagesFeatured.add(images[0]) // Chọn ảnh đầu tiên, bạn có thể thay đổi logic này
                } else {
                    productImagesFeatured.add("") // Nếu không có ảnh thì thêm chuỗi rỗng
                }
            }

            // Khởi tạo adapter với danh sách sản phẩm
            val productAdapter = ProductAdapter(
                productNames,
                productPrices,
                productImages,
                productAddress,
                productLastModified,
                productQuantityImage,
                requireContext(),
                object : ProductAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        // Điều hướng đến trang chi tiết sản phẩm
                        val productId = promotionalPostsList[position].id
                        val bundle = Bundle().apply {
                            putString("productId", productId)
                        }
                        findNavController().navigate(
                            R.id.action_homeFragment_to_productDetailsFragment,
                            bundle
                        )
                    }
                })

            val gridLayoutManager = GridLayoutManager(requireContext(), 2) // 2 là số cột
            dataBinding.rvBestProducts.apply {
                layoutManager = gridLayoutManager
                adapter = productAdapter
            }
        }
    }

    // Thêm listener cho sự kiện scroll
    dataBinding.rvBestProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            if (layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount - 1) {
                // Load more data when the last item is visible
                viewModel.fetchData()
            }
        }
    })
    viewModel.promotionalPost.observe(viewLifecycleOwner) { promotionalPosts ->
        promotionalPosts?.let {
            promotionalPostsList = promotionalPostsList + it
            Log.d("Size List", promotionalPostsList.size.toString())

            // Duyệt qua từng phần tử trong promotionalPosts
            for (post in it) {
                val title = post.title
                val basePrice = post.pricingDetails.basePrice
                val images = post.roomInfo.postImages.map { image -> image.urlImagePost }
                val address = post.roomInfo.address
                val lastModified = post.lastModifiedDate
                val quantityImage = images.size


                // Thêm dữ liệu vào các danh sách
                productNames.add(title)
                productPrices.add(basePrice.toString())
                productAddress.add(address)
                productLastModified.add(lastModified)
                productQuantityImage.add(quantityImage)
                // Chuyển basePrice thành chuỗi nếu cần
                if (images.isNotEmpty()) {
                    productImages.add(images[0]) // Chọn ảnh đầu tiên, bạn có thể thay đổi logic này
                } else {
                    productImages.add("") // Nếu không có ảnh thì thêm chuỗi rỗng
                }
            }

            // Khởi tạo adapter với danh sách sản phẩm
            val productAdapter = ProductAdapter(
                productNames,
                productPrices,
                productImages,
                productAddress,
                productLastModified,
                productQuantityImage,
                requireContext(),
                object : ProductAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        // Điều hướng đến trang chi tiết sản phẩm
                        val productId = promotionalPostsList[position].id
                        val bundle = Bundle().apply {
                            putString("productId", productId)
                        }
                        findNavController().navigate(
                            R.id.action_homeFragment_to_productDetailsFragment,
                            bundle
                        )
                    }
                })


            val gridLayoutManager = GridLayoutManager(requireContext(), 2) // 2 là số cột
            dataBinding.rvOfferProducts.apply {
                layoutManager = gridLayoutManager
                adapter = productAdapter
            }
        }
    }
    // Thêm listener cho sự kiện scroll
    dataBinding.rvOfferProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            if (layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount - 1) {
                // Load more data when the last item is visible
                viewModel.fetchData()
            }
        }
    })

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
}