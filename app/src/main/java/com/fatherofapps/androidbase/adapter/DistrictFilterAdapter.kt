package com.fatherofapps.androidbase.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fatherofapps.androidbase.R

class DistrictFilterAdapter(
    private val districts: List<String>,
    private val onDistrictSelected: (String) -> Unit
) : RecyclerView.Adapter<DistrictFilterAdapter.DistrictViewHolder>() {

    private var selectedPosition: Int? = null // Lưu vị trí được chọn

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_district, parent, false)
        return DistrictViewHolder(view)
    }

    override fun onBindViewHolder(holder: DistrictViewHolder, position: Int) {
        val district = districts[position]
        holder.bind(district, position)
    }

    override fun getItemCount() = districts.size

    inner class DistrictViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.spinner_category)

        fun bind(district: String, position: Int) {
            textView.text = district

            // Đổi màu nền và màu chữ khi item được chọn
            if (position == selectedPosition) {
                textView.setBackgroundResource(R.drawable.selected_background)
                textView.setTextColor(Color.WHITE) // Đổi màu chữ thành trắng
            } else {
                textView.setBackgroundResource(R.drawable.default_background)
                textView.setTextColor(Color.BLACK) // Màu chữ mặc định
            }

            itemView.setOnClickListener {
                // Nếu item được click lần nữa, bỏ chọn
                if (selectedPosition == position) {
                    selectedPosition = null
                } else {
                    selectedPosition = position // Cập nhật vị trí đã chọn
                }
                notifyDataSetChanged() // Refresh lại danh sách
                onDistrictSelected(district)
            }
        }
    }
}
