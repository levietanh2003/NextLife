package com.fatherofapps.androidbase.ui.search.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment // Thay đổi bằng binding của bạn
import com.fatherofapps.androidbase.databinding.DialogFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


class FilterAreaDialogFragment : BottomSheetDialogFragment() {

    private var _binding: DialogFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Thiết lập sự kiện cho các nút trong Dialog
        binding.btnNegative.setOnClickListener {
            dismiss() // Đóng dialog khi nhấn nút Hủy bỏ
        }

        binding.ivClose.setOnClickListener {
            dismiss()
        }

        binding.btnPositive.setOnClickListener {
            // Xử lý logic khi nhấn nút Áp dụng
            dismiss() // Đóng dialog
        }
    }

//    override fun onStart() {
//        super.onStart()
//        dialog?.let {
//            val width = ViewGroup.LayoutParams.WRAP_CONTENT // Hoặc kích thước cụ thể
//            val height = ViewGroup.LayoutParams.WRAP_CONTENT // Hoặc kích thước cụ thể
//            it.window?.setLayout(width, height)
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
