package com.fatherofapps.androidbase.ui.customer.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fatherofapps.androidbase.base.fragment.BaseFragment
import com.fatherofapps.androidbase.databinding.FragmentMarketBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentMarketBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentMarketBinding.inflate(inflater, container, false)

        // Khởi tạo NavigationBarController

        // Ẩn navigation bar
        return dataBinding.root
    }
}