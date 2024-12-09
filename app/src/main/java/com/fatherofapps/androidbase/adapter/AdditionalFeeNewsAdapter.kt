package com.fatherofapps.androidbase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.common.formatPrice
import com.fatherofapps.androidbase.data.models.AdditionalFee
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText

class AdditionalFeeNewsAdapter(
    private val fees: MutableList<AdditionalFee>,
    private val onFeeUpdateListener: OnFeeUpdateListener
) : RecyclerView.Adapter<AdditionalFeeNewsAdapter.AdditionalFeeViewNewsHolder>() {

    interface OnFeeUpdateListener {
        fun onFeeUpdated(position: Int, updatedFee: AdditionalFee)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdditionalFeeViewNewsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_additional_fee_new, parent, false)
        return AdditionalFeeViewNewsHolder(view, onFeeUpdateListener)
    }

    override fun onBindViewHolder(holder: AdditionalFeeViewNewsHolder, position: Int) {
        val fee = fees[position]
        holder.bind(fee, position)
    }

    override fun getItemCount(): Int = fees.size

    class AdditionalFeeViewNewsHolder(
        itemView: View,
        private val onFeeUpdateListener: OnFeeUpdateListener
    ) : RecyclerView.ViewHolder(itemView) {
        private val feeType: TextView = itemView.findViewById(R.id.feeType)
        private val feeAmount: TextView = itemView.findViewById(R.id.feeAmount)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkmarkIcon)
        private val editButton: ImageButton = itemView.findViewById(R.id.btn_edit)

        fun bind(fee: AdditionalFee, position: Int) {
            feeType.text = fee.type
            feeAmount.text = formatPrice().formatPriceAdditionalFromString(fee.amount.toString())

            // Checkbox logic
            checkBox.isChecked = fee.isSelected
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                fee.isSelected = isChecked
                updateTextColor(isChecked)
            }
            updateTextColor(fee.isSelected)

            // Edit button logic
            editButton.setOnClickListener {
                showEditFeeBottomSheet(fee, position)
            }
        }

        private fun updateTextColor(isSelected: Boolean) {
            val colorResId = if (isSelected) R.color.color_main else R.color.black
            val color = ContextCompat.getColor(itemView.context, colorResId)
            feeType.setTextColor(color)
            feeAmount.setTextColor(color)
            // Đổi text style thành bold khi checkbox được chọn
            val textStyle = if (isSelected) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL
            feeType.setTypeface(null, textStyle)
            feeAmount.setTypeface(null, textStyle)
        }

        private fun showEditFeeBottomSheet(fee: AdditionalFee, position: Int) {
            val context = itemView.context
            val bottomSheetDialog = BottomSheetDialog(context)
            val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_edit_additionalfee, null)

            val edtFeeType = view.findViewById<TextInputEditText>(R.id.edtFeeType)
            val edtFeeAmount = view.findViewById<TextInputEditText>(R.id.edtFeeAmount)
            val btnSave = view.findViewById<TextView>(R.id.btnSave)
            val btnCancel = view.findViewById<TextView>(R.id.btnCancel)

            // Set initial values
            edtFeeType.setText(fee.type)
            edtFeeAmount.setText(fee.amount.toString())

            btnSave.setOnClickListener {
                val newType = edtFeeType.text.toString().trim()
                val newAmountStr = edtFeeAmount.text.toString().trim()

                if (newType.isNotEmpty() && newAmountStr.isNotEmpty()) {
                    try {
                        val newAmount = newAmountStr.toInt()
                        val updatedFee = AdditionalFee(newType, newAmount)
                        onFeeUpdateListener.onFeeUpdated(position, updatedFee)
                        bottomSheetDialog.dismiss()
                    } catch (e: NumberFormatException) {
                        edtFeeAmount.error = "Invalid amount"
                    }
                } else {
                    if (newType.isEmpty()) edtFeeType.error = "Nhập dịch vụ"
                    if (newAmountStr.isEmpty()) edtFeeAmount.error = "Nhập giá tiền"
                }
            }

            btnCancel.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }
    }
}