package com.fatherofapps.androidbase.ui.search.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.adapter.DistrictFilterAdapter
import com.fatherofapps.androidbase.base.dialogs.NotifyDialog
import com.fatherofapps.androidbase.databinding.DialogFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterAreaBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: DialogFilterBinding? = null
    private val binding get() = _binding!!
    private var address: String? = null
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewCategories)


        // Danh sách các danh mục
        districtsFilterAdapter = DistrictFilterAdapter(districts) { selectedDistrict ->
            updateSelectedArea(selectedDistrict) // Cập nhật quận đã chọn
        }


        // Thiết lập layout manager
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = districtsFilterAdapter

        // Thiết lập sự kiện cho các nút trong Dialog
        binding.btnNegative.setOnClickListener {
            dismiss()
        }

        binding.ivClose.setOnClickListener {
            dismiss()
        }

        binding.btnPositive.setOnClickListener {

            // kiem tra nguoi dung da chon chua
            if(address == null) {
                NotifyDialog(
                    requireContext(),
                    title = "Thông báo",
                    message = "Vui lòng chọn quận",
                    textButton = "Đồng ý"
                ).show()
                return@setOnClickListener
            }else{
                val result = Bundle().apply {
                    putString("selected_address", address) // Thêm productId vào bundle
                }
                setFragmentResult("filter_request_key", result)
                dismiss()
                Log.d("Address_BottomSheet", "Selected address: $address")
            }
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
