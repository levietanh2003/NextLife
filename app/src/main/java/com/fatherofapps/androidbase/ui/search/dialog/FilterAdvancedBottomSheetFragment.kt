package com.fatherofapps.androidbase.ui.search.dialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.adapter.DistrictFilterAdapter
import com.fatherofapps.androidbase.databinding.FragmentFilterAdvancedBinding
import com.fatherofapps.androidbase.ui.search.SearchViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.slider.RangeSlider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterAdvancedBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentFilterAdvancedBinding? = null
    private val binding get() = _binding!!
    private var address: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var minPrice: EditText
    private lateinit var maxPrice: EditText
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var rangeSlider: RangeSlider
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

        rangeSlider = binding.priceRangeSlider
        minPrice = binding.minPrice
        maxPrice = binding.maxPrice
        recyclerView = binding.recyclerViewFilterAdvanced
        binding.ivClose.setOnClickListener { dismiss() }
        // Danh sách các danh mục
        districtsFilterAdapter = DistrictFilterAdapter(districts) { selectedDistrict ->
            // Cập nhật quận đã chọn
            updateSelectedArea(selectedDistrict)
        }
        initializeRangeSlider()
        initializeEditTexts()
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
            val minPriceValue = minPrice.text.toString().toIntOrNull() ?: 0
            val maxPriceValue = maxPrice.text.toString().toIntOrNull() ?: 0
            val selectedDistrict = address
            val category = binding.sortByCategoriesSpinner.selectedItem.toString()
            val hasPromotion = binding.hasPromotionCheckbox.isChecked

            val result = Bundle().apply {
                putString("selected_district_Advanced", selectedDistrict.toString())
                putString("category_Advanced", category)
                putBoolean("has_promotion_Advanced", hasPromotion)
                putString("min_price_Advanced", (minPriceValue * 10).toString()) // Nhân minPrice với 10
                putString("max_price_Advanced", (maxPriceValue * 10).toString()) // Nhân maxPrice với 10
            }
            Log.d("FillAdvanced1", "minPrice: $minPriceValue, maxPrice: $maxPriceValue, district: $selectedDistrict, type: $category, hasPromotion: $hasPromotion")


            setFragmentResult("filter_request_key", result)
            dismiss()
            Log.d("Price_BottomSheet", "Selected prices: min = ${minPriceValue * 10}, max = ${maxPriceValue * 10}")
            dismiss()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun updateSelectedArea(selectedArea: String) {
        address = selectedArea
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeEditTexts() {
        minPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val minValue = s.toString().toIntOrNull() ?: 0
                if (minValue < rangeSlider.values[1]) {
                    rangeSlider.setValues(minValue.toFloat(), rangeSlider.values[1])
                } else {
                    minPrice.setText(rangeSlider.values[0].toInt().toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        maxPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val maxValue = s.toString().toIntOrNull() ?: rangeSlider.valueTo.toInt()
                if (maxValue > rangeSlider.values[0]) {
                    rangeSlider.setValues(rangeSlider.values[0], maxValue.toFloat())
                } else {
                    maxPrice.setText(rangeSlider.values[1].toInt().toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
    private fun initializeRangeSlider() {
        // Set initial values for EditTexts based on RangeSlider's values
        updatePriceInputs()

        rangeSlider.addOnChangeListener { slider, _, fromUser ->
            if (fromUser) updatePriceInputs()
        }
    }

    private fun updatePriceInputs() {
        val values = rangeSlider.values
        if (values.size == 2) {
            minPrice.setText(values[0].toInt().toString())
            maxPrice.setText(values[1].toInt().toString())
        }
    }
}