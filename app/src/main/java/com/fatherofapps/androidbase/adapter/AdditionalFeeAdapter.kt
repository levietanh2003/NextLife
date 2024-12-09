package com.fatherofapps.androidbase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.common.formatPrice
import com.fatherofapps.androidbase.data.models.AdditionalFee

class AdditionalFeeAdapter(private val fees: List<AdditionalFee>) : RecyclerView.Adapter<AdditionalFeeAdapter.AdditionalFeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdditionalFeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_additional_fee, parent, false)
        return AdditionalFeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdditionalFeeViewHolder, position: Int) {
        val fee = fees[position]
        holder.bind(fee)
    }

    override fun getItemCount(): Int = fees.size

    class AdditionalFeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val feeType: TextView = itemView.findViewById(R.id.feeType)
        private val feeAmount: TextView = itemView.findViewById(R.id.feeAmount)

        fun bind(fee: AdditionalFee) {
            feeType.text = fee.type
            feeAmount.text = formatPrice().formatPriceAdditionalFromString(fee.amount.toString())

        }
    }
}

