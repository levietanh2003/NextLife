package com.fatherofapps.androidbase.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fatherofapps.androidbase.databinding.ItemImageBinding

class ImagesAdapter(private val images: MutableList<Uri>, private val onDelete: (Uri) -> Unit) : RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUri = images[position]

        Glide.with(holder.itemView.context)
            .load(imageUri)
            .into(holder.binding.imageView)

        // Xử lý sự kiện khi nhấn vào icon xóa
        holder.binding.deleteIcon.setOnClickListener {
            onDelete(imageUri)
        }
    }

    override fun getItemCount(): Int = images.size

    class ImageViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)
}

