package com.fatherofapps.androidbase.ui.customer.myaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.fatherofapps.androidbase.base.fragment.BaseFragment

import com.fatherofapps.androidbase.databinding.FragmentMyAccountBinding

class MyAccountFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentMyAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentMyAccountBinding.inflate(inflater, container, false)

        // Khởi tạo NavigationBarController

        // Ẩn navigation bar
        return dataBinding.root
    }
}