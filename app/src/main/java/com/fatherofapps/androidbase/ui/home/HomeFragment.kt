package com.fatherofapps.androidbase.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
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
    private var promotionalPostsList: List<PromotionalPost> = emptyList() // Thay thế PromotionalPost bằng kiểu dữ liệu thực tế của bạn


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // khi vua khoi tao se fetch data len
        viewModel.fetchData()
//        viewModel.fetchPromotionalPosts()
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

    // luu danh sach image vao slider
    val imageListt = ArrayList<SlideModel>()
    imageListt.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
    imageListt.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
    imageListt.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

    val imagesSlider = dataBinding.imageSlider
    imagesSlider.setImageList(imageListt)
    imagesSlider.setImageList(imageListt, ScaleTypes.FIT)

    viewModel.promotionalPost.observe(viewLifecycleOwner) { promotionalPosts ->
        promotionalPosts?.let {
            promotionalPostsList = it
            Log.d("Size List", it.size.toString())


            // Tạo các danh sách rỗng để chứa dữ liệu
            val productNames = mutableListOf<String>()
            val productPrices = mutableListOf<String>()
            val productImages = mutableListOf<String>()
            val productAddress = mutableListOf<String>()
            val productLastModified = mutableListOf<Double>()
            val productQuantityImage = mutableListOf<Int>()


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


                Log.d("Size List Image", quantityImage.toString())
                // Ví dụ: Log các giá trị hoặc hiển thị chúng trên UI
                Log.d("PromotionalPost", "Title: $title")
                Log.d("PromotionalPost", "Base Price: $basePrice")
                Log.d("PromotionalPost", "Address: $productAddress")
                images.forEach { imageUrl ->
                    Log.d("PromotionalPost", "Image URL: $imageUrl")
                }
            }

            // Khởi tạo adapter với danh sách sản phẩm
            val productAdapter = ProductAdapter(productNames, productPrices ,productImages ,productAddress , productLastModified , productQuantityImage,requireContext(),  object : ProductAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    // Khi người dùng nhấn vào item, chuyển đến trang chi tiết sản phẩm
                    val productId = promotionalPostsList[position].id
                    val bundle = Bundle().apply {
                        putString("productId", productId) // Thêm productId vào bundle
                    }
                    Log.d("ProductId", "productId: $productId")
//                    navigateToPage(R.id.action_homeFragment_to_productDetailsFragment, bundle)
                    // Trực tiếp điều hướng mà không sử dụng hàm navigateToPage
                    findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment, bundle)
                }
            })

            val gridLayoutManager = GridLayoutManager(requireContext(), 2) // 2 là số cột
            dataBinding.rvOfferProducts.apply {
                layoutManager = gridLayoutManager
                adapter = productAdapter
            }
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
}