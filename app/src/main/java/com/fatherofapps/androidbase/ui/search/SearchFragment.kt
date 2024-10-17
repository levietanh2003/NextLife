package com.fatherofapps.androidbase.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fatherofapps.androidbase.base.fragment.BaseFragment
import com.fatherofapps.androidbase.databinding.FragmentSearchBinding
import com.fatherofapps.androidbase.ui.search.dialog.FilterAreaDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment() {
    private lateinit var dataBinding: FragmentSearchBinding
    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // khi vua khoi tao se fetch data len

//        viewModel.fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentSearchBinding.inflate(inflater)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = viewModel

        // Thiết lập sự kiện nhấn cho btn_filter_area
        dataBinding.btnFilterArea.setOnClickListener {
            // Khởi tạo và hiển thị dialog
            val filterDialog = FilterAreaDialogFragment()
            filterDialog.show(childFragmentManager, "FilterAreaDialogFragment")
        }

        return dataBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}