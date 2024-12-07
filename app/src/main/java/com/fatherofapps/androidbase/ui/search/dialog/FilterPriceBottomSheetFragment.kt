package com.fatherofapps.androidbase.ui.search.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import com.fatherofapps.androidbase.base.bottomSheet.BaseFilterBottomSheetFragment
import com.fatherofapps.androidbase.base.dialogs.NotifyDialog
import com.fatherofapps.androidbase.databinding.FragmentPriceBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterPriceBottomSheetFragment : BaseFilterBottomSheetFragment() {
    private var _binding: FragmentPriceBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPriceBottomSheetBinding.inflate(inflater, container, false)

        setupUI()
        setupCloseButton(binding.root)

        return binding.root
    }

    private fun setupUI() {
        priceRangeHandler.initializeRangeSlider(
            binding.priceRangeSlider,
            binding.minPrice,
            binding.maxPrice
        )
        priceRangeHandler.setupPriceTextWatchers(
            binding.priceRangeSlider,
            binding.minPrice,
            binding.maxPrice
        )

        binding.btnPositive.setOnClickListener {
            handlePositiveAction()
        }

        binding.btnNegative.setOnClickListener { dismiss() }
    }

    private fun handlePositiveAction() {
        val minPriceValue = binding.minPrice.text.toString().toIntOrNull() ?: 0
        val maxPriceValue = binding.maxPrice.text.toString().toIntOrNull() ?: 0

        // Kiểm tra nếu giá trị nhỏ nhất hoặc lớn nhất bằng 0
        if (minPriceValue == 0 || maxPriceValue == 0) {
            NotifyDialog(
                requireContext(),
                title = "Thông báo",
                message = "Giá trị không thể bằng 0",
                textButton = "Đồng ý"
            ).show()
            return
        }

        val result = Bundle().apply {
            putString("min_price", (minPriceValue * 10).toString())
            putString("max_price", (maxPriceValue * 10).toString())
        }

        setFragmentResult("filter_request_key", result)
        Log.d("Price_BottomSheet", "Selected prices: min = ${minPriceValue * 10}, max = ${maxPriceValue * 10}")
        dismiss()
    }

    override fun getCloseButtonId() = binding.ivClose.id

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
