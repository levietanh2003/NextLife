package com.fatherofapps.androidbase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fatherofapps.androidbase.R

class DistrictFilterAdapter(
    private val districts: List<String>,
    private val onItemClick: (String) -> Unit
    ) : RecyclerView.Adapter<DistrictFilterAdapter.DistrictFilterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DistrictFilterViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_district, parent, false)
        return DistrictFilterViewHolder(view)
    }

    inner class DistrictFilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTextView: TextView = itemView.findViewById(R.id.spinner_category)
        init {
            itemView.setOnClickListener {
                onItemClick(districts[adapterPosition]) // Gọi callback khi nhấn vào item
            }
        }
    }
    override fun onBindViewHolder(
        holder: DistrictFilterAdapter.DistrictFilterViewHolder,
        position: Int
    ) {
        holder.categoryTextView.text = districts[position]
    }

    override fun getItemCount(): Int = districts.size

}