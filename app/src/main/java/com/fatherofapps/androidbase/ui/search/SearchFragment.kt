package com.fatherofapps.androidbase.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.adapter.ProductAdapter
import com.fatherofapps.androidbase.adapter.ProductHorizontalAdapter
import com.fatherofapps.androidbase.base.fragment.BaseFragment
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.databinding.FragmentSearchBinding
import com.fatherofapps.androidbase.ui.search.dialog.FilterAreaDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment() {
    private lateinit var dataBinding: FragmentSearchBinding
    private val viewModel by viewModels<SearchViewModel>()
    private var minPrice: Double? = null
    private var maxPrice: Double? = null
    private var district: String? = null
    private var type: String? = null
    private var hasPromotion: Boolean? = null
    private lateinit var titleSearch: String
    private lateinit var noResultsImage: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        titleSearch = arguments?.getString("search_query").toString()
//         xu lý fetch data theo titleSearch
        viewModel.fetchData(titleSearch)
        if(titleSearch.isEmpty()){
            viewModel.fetchData(titleSearch)
        }

        parentFragmentManager.setFragmentResultListener("filter_request_key", this) { _, bundle ->
            district = bundle.getString("selected_address")
            // Cập nhật UI hoặc thực hiện các thao tác cần thiết với selectedAddress
            dataBinding.titleAddress.text = district
            Log.d("Title_SearchFragment", titleSearch)
            viewModel.fetchData(district = district)
        }
        // Initial data fetch
//        viewModel.fetchData(minPrice, maxPrice, district, type, hasPromotion)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentSearchBinding.inflate(inflater)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = viewModel

        // Khởi tạo ImageView thông báo
        noResultsImage = dataBinding.noResultsImage

        // Thiết lập sự kiện nhấn cho btn_filter_area
        dataBinding.btnFilterArea.setOnClickListener {

            openBottomSheet()
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerAllExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(viewModel, viewLifecycleOwner)

//        dataBinding.spinnerCategory.setOnClickListener {
//        }



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

    private fun openBottomSheet() {
        val bottomSheetFragment = FilterAreaDialogFragment()
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
//    private fun handleFilteredPosts(posts: List<PromotionalPost>) {
//        Log.d("Address_SearchFragment", "Handling filtered posts. Count: ${posts.size}")
//        posts.let {
//            val productAdapter = ProductHorizontalAdapter(
//                it.map { post -> post.title },
//                it.map { post -> post.pricingDetails.basePrice.toString() },
//                it.map { post -> post.roomInfo.postImages.getOrNull(0)?.urlImagePost ?: "" },
//                it.map { post -> post.roomInfo.address },
//                it.map { post -> post.lastModifiedDate },
//                it.map { post -> post.roomInfo.postImages.size },
//                requireContext(),
//                object : ProductAdapter.OnItemClickListener {
//                    override fun onItemClick(position: Int) {
//                        val productId = posts[position].id
//                        val bundle = Bundle().apply {
//                            putString("productId", productId)
//                        }
//                        findNavController().navigate(
//                            R.id.action_searchFragment_to_productDetailsFragment, bundle
//                        )
//                    }
//                }
//            )
//            dataBinding.rvProductFilter.apply {
//                layoutManager = GridLayoutManager(requireContext(), 1)
//                adapter = productAdapter
//            }
//        }
//    }
}