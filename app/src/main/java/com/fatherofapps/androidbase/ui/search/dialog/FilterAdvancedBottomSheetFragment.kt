package com.fatherofapps.androidbase.ui.search.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.adapter.DistrictFilterAdapter
import com.fatherofapps.androidbase.databinding.FragmentFilterAdvancedBinding
import com.fatherofapps.androidbase.ui.search.SearchViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterAdvancedBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentFilterAdvancedBinding? = null
    private val binding get() = _binding!!
    private var address: String? = null
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var districtsFilterAdapter: DistrictFilterAdapter

    val districts = listOf(
        "Quận 1",
        "Quận 2",
        "Quận 3",
        "Quận 4",
        "Quận 5",
        "Quận 6",
        "Quận 7",
        "Quận 8",
        "Quận 9",
        "Quận 10",
        "Quận 11",
        "Quận 12",
        "Tân Bình",
        "Bình Tân",
        "Gò Vấp",
        "Phú Nhuận",
        "Thủ Đức"
    )

    val categoryList = listOf(
        "Căn hộ mini",
        "Căn hộ",
        "Phòng trọ",
        "Căn hộ dịch vụ",
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterAdvancedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewFilterAdvanced)
        // Danh sách các danh mục
        districtsFilterAdapter = DistrictFilterAdapter(districts) { selectedDistrict ->
            // Cập nhật quận đã chọn
            updateSelectedArea(selectedDistrict)
        }
        // Thiết lập layout manager
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = districtsFilterAdapter

        // Setting up the Spinner with hardcoded values
        val priceSortOptions = arrayOf("Giá cao", "Giá thấp")
        val adapterPriceSort = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priceSortOptions)
        adapterPriceSort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sortByPriceSpinner.adapter = adapterPriceSort

        // setting up the Spinner with by created
        val createdDayOptions = arrayOf("1 giờ", "1 ngày", "1 Tháng", "3 tháng")
        val adapterCreatedDay = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, createdDayOptions)
        adapterCreatedDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sortByCreatedSpinner.adapter = adapterCreatedDay

        // setting up Spinner with type values
        val categoryOptions = categoryList
        val adapterCategory = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryOptions)
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sortByCategoriesSpinner.adapter = adapterCategory

        binding.btnPositive.setOnClickListener {
            val minPrice = binding.minPrice.text.toString().toDoubleOrNull()
            val maxPrice = binding.maxPrice.text.toString().toDoubleOrNull()
            val selectedDistrict = address
            val hasPromotion = binding.hasPromotionCheckbox.isChecked

            viewModel.fetchData(minPrice, maxPrice, selectedDistrict, null, hasPromotion)
            dismiss()
        }
    }

    private fun updateSelectedArea(selectedArea: String) {
        address = selectedArea
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}