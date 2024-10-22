package com.fatherofapps.androidbase.ui.search.dialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.setFragmentResult
import com.fatherofapps.androidbase.databinding.FragmentPriceBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.slider.RangeSlider

class FilterPriceBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentPriceBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var rangeSlider: RangeSlider
    private lateinit var minPrice: EditText
    private lateinit var maxPrice: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPriceBottomSheetBinding.inflate(inflater, container, false)

        rangeSlider = binding.priceRangeSlider
        minPrice = binding.minPrice
        maxPrice = binding.maxPrice

        initializeRangeSlider()
        initializeEditTexts()


        binding.ivClose.setOnClickListener { dismiss() }
        binding.btnPositive.setOnClickListener {
            val minPriceValue = minPrice.text.toString().toIntOrNull() ?: 0
            val maxPriceValue = maxPrice.text.toString().toIntOrNull() ?: 0

            val result = Bundle().apply {
                putString("min_price", (minPriceValue * 10).toString()) // Nhân minPrice với 10
                putString("max_price", (maxPriceValue * 10).toString()) // Nhân maxPrice với 10
            }

            setFragmentResult("filter_request_key", result)
            dismiss()
            Log.d("Price_BottomSheet", "Selected prices: min = ${minPriceValue * 10}, max = ${maxPriceValue * 10}")
        }

        binding.btnNegative.setOnClickListener { dismiss() }

        return binding.root
    }

    private fun initializeRangeSlider() {
        // Set initial values for EditTexts based on RangeSlider's values
        updatePriceInputs()

        rangeSlider.addOnChangeListener { slider, _, fromUser ->
            if (fromUser) updatePriceInputs()
        }
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

    private fun updatePriceInputs() {
        val values = rangeSlider.values
        if (values.size == 2) {
            minPrice.setText(values[0].toInt().toString())
            maxPrice.setText(values[1].toInt().toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
