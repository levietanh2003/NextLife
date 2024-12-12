package com.fatherofapps.androidbase.ui.customer.payment

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.adapter.TransactionAdapter
import com.fatherofapps.androidbase.base.activities.BaseActivity
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.databinding.ActivityTransactionBinding
import com.fatherofapps.androidbase.databinding.FragmentMyAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionActivity : BaseActivity() {
    private lateinit var dataBinding: ActivityTransactionBinding
    private val paymentViewModel by viewModels<PaymentViewModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        recyclerView = dataBinding.recyclerView

        // Set LayoutManager cho RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Lắng nghe kết quả trả về từ ViewModel
        paymentViewModel.fetchData()

        dataBinding.btnBack.setOnClickListener {
            onBackPressed() // Hoặc gọi finish() nếu muốn đóng Activity hiện tại
        }

        paymentViewModel.listHistoryPayment.observe(this) { result ->
            Log.d("TransactionData", "Transaction: ${result}")
            when (result) {
                is NetworkResult.Success -> {
                    val transactions = result.data
                    Log.d("TransactionData", "Transaction: ${transactions}")

                    // Cập nhật giao diện với danh sách transactions
                    transactions?.let {
                        adapter = TransactionAdapter(it)
                        recyclerView.adapter = adapter
                    }
                }
                is NetworkResult.Error -> {
                    // Hiển thị thông báo lỗi
                    Log.e("PaymentViewModel", "Error: ${result}")
                }
            }
        }
    }
}
