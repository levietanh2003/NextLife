package com.fatherofapps.androidbase.ui.interfaces

import android.widget.EditText
import com.google.android.material.slider.RangeSlider

interface PriceRangeHandler {
    fun initializeRangeSlider(rangeSlider: RangeSlider, minPrice: EditText, maxPrice: EditText)
    fun setupPriceTextWatchers(rangeSlider: RangeSlider, minPrice: EditText, maxPrice: EditText)
    fun updatePriceInputs(rangeSlider: RangeSlider, minPrice: EditText, maxPrice: EditText)
}