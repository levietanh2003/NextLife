package com.fatherofapps.androidbase.base.bottomSheet

import android.view.View
import com.fatherofapps.androidbase.ui.interfaces.DefaultPriceRangeHandler
import com.fatherofapps.androidbase.ui.interfaces.PriceRangeHandler
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseFilterBottomSheetFragment : BottomSheetDialogFragment() {
    protected val priceRangeHandler: PriceRangeHandler = DefaultPriceRangeHandler()

    protected fun setupCloseButton(view: View) {
        view.findViewById<View>(getCloseButtonId())?.setOnClickListener { dismiss() }
    }

    protected abstract fun getCloseButtonId(): Int
}