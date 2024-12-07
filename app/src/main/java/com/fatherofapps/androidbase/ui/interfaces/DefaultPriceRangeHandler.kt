package com.fatherofapps.androidbase.ui.interfaces

import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.slider.RangeSlider

class DefaultPriceRangeHandler : PriceRangeHandler {
    override fun initializeRangeSlider(rangeSlider: RangeSlider, minPrice: EditText, maxPrice: EditText) {
        updatePriceInputs(rangeSlider, minPrice, maxPrice)
        rangeSlider.addOnChangeListener { slider, _, fromUser ->
            if (fromUser) updatePriceInputs(slider, minPrice, maxPrice)
        }
    }

    override fun setupPriceTextWatchers(rangeSlider: RangeSlider, minPrice: EditText, maxPrice: EditText) {
        minPrice.doAfterTextChanged { s ->
            val minValue = s.toString().toIntOrNull() ?: 0
            if (minValue < rangeSlider.values[1]) {
                rangeSlider.setValues(minValue.toFloat(), rangeSlider.values[1])
            } else {
                minPrice.setText(rangeSlider.values[0].toInt().toString())
            }
        }

        maxPrice.doAfterTextChanged { s ->
            val maxValue = s.toString().toIntOrNull() ?: rangeSlider.valueTo.toInt()
            if (maxValue > rangeSlider.values[0]) {
                rangeSlider.setValues(rangeSlider.values[0], maxValue.toFloat())
            } else {
                maxPrice.setText(rangeSlider.values[1].toInt().toString())
            }
        }
    }

    override fun updatePriceInputs(rangeSlider: RangeSlider, minPrice: EditText, maxPrice: EditText) {
        val values = rangeSlider.values
        if (values.size == 2) {
            minPrice.setText(values[0].toInt().toString())
            maxPrice.setText(values[1].toInt().toString())
        }
    }
}