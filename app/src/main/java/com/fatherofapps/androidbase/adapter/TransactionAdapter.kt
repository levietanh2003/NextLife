package com.fatherofapps.androidbase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.data.models.Transaction
import java.text.NumberFormat
import java.util.Locale

class TransactionAdapter(private val transactions: List<Transaction>) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.bind(transaction)
    }

    override fun getItemCount(): Int = transactions.size

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTransactionId: TextView = itemView.findViewById(R.id.tvTransactionId)
        private val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        private val tvMethod: TextView = itemView.findViewById(R.id.tvMethod)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        private val tvCreatedBy: TextView = itemView.findViewById(R.id.tvCreatedBy)

        // Khai báo numberFormat trong TransactionViewHolder
        private val numberFormat: NumberFormat = NumberFormat.getNumberInstance(Locale("vi", "VN"))

        fun bind(transaction: Transaction) {
            tvTransactionId.text = "Mã giao dịch: ${transaction.id}"
            tvAmount.text = "Số tiền: ${numberFormat.format(transaction.amount)} VNĐ" // Định dạng số tiền
            tvMethod.text = "Phương thức: ${transaction.method}"
            tvStatus.text = "Trạng thái: ${transaction.status}"
            tvCreatedBy.text = "Người tạo: ${transaction.createdBy}"
        }
    }
}


//class TransactionAdapter(private val transactions: List<Transaction>) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
//        return TransactionViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
//        val transaction = transactions[position]
//        holder.bind(transaction)
//    }
//
//    override fun getItemCount(): Int = transactions.size
//
//    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val tvTransactionId: TextView = itemView.findViewById(R.id.tvTransactionId)
//        private val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
//        private val tvMethod: TextView = itemView.findViewById(R.id.tvMethod)
//        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
//        private val tvCreatedBy: TextView = itemView.findViewById(R.id.tvCreatedBy)
//
//        fun bind(transaction: Transaction) {
//            tvTransactionId.text = "Mã giao dịch: ${transaction.id}"
//            tvAmount.text = "Số tiền: ${transaction.amount} VNĐ"
//            tvMethod.text = "Phương thức: ${transaction.method}"
//            tvStatus.text = "Trạng thái: ${transaction.status}"
//            tvCreatedBy.text = "Người tạo: ${transaction.createdBy}"
//        }
//    }
//}
