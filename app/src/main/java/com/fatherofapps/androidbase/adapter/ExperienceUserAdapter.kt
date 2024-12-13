package com.fatherofapps.androidbase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.adapter.ProductAdapter.OnItemClickListener

class ExperienceUserAdapter(
    private val experienceNames: List<String>,
    private val imageUrls: List<String>,
    private val context: Context,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ExperienceUserAdapter.ExperienceUserHolder>() {

    class ExperienceUserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val experienceImage: ImageView = itemView.findViewById(R.id.experience_image)
        val experienceName: TextView = itemView.findViewById(R.id.experience_name)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExperienceUserHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_experience, parent, false)
        return ExperienceUserHolder(view)
    }

    override fun onBindViewHolder(
        holder: ExperienceUserHolder,
        position: Int
    ) {
        val nameProduct = experienceNames[position]
        val imageUrl = imageUrls[position]

        // Định dạng đối tượng Date thành chuỗi
        holder.experienceName.text = nameProduct
        // Sử dụng Glide để tải hình ảnh sản phẩm
        Glide.with(context)
            .load(imageUrl)
            .into(holder.experienceImage)


        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(position) // Gọi listener với position và productId
        }
    }

    override fun getItemCount(): Int {
        return experienceNames.size
    }
}