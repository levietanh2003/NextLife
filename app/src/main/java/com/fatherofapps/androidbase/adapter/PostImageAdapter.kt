package com.fatherofapps.androidbase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fatherofapps.androidbase.data.models.PostImage
import com.fatherofapps.androidbase.databinding.ItemPostImageBinding // Import cho ItemPostImageBinding

class PostImageAdapter(private val postImages: List<PostImage>) : RecyclerView.Adapter<PostImageAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPostImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPostImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postImage = postImages[position]
        holder.binding.apply {
            // Sử dụng Glide để tải hình ảnh vào ImageView
            Glide.with(imageView.context)
                .load(postImage.urlImagePost)
                .into(imageView)
        }
    }

    override fun getItemCount(): Int {
        return postImages.size
    }
}
