package com.fatherofapps.androidbase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fatherofapps.androidbase.R // Đảm bảo import tài nguyên của dự án của bạn

class ImageAdapter(
    private val context: Context,
    private val arrayList: ArrayList<String>
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.image_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Glide.with(context)
//            .load(arrayList[position])
//            .into(holder.imageView)
//
//        holder.itemView.setOnClickListener {
//            onItemClickListener?.onClick(holder.imageView, arrayList[position]) // Sử dụng toán tử an toàn
//        }
        Glide.with(context)
            .load(arrayList[position])
            .into(holder.imageView)


//        holder.itemView.setOnClickListener {
//            // Mở dialog với hình ảnh lớn
//            val dialogFragment = FullScreenImageDialogFragment.newInstance(arrayList[position])
//            dialogFragment.show((context as AppCompatActivity).supportFragmentManager, "FullScreenImage")
//        }

        holder.itemView.setOnClickListener {
            val dialog = FullScreenImageDialogFragment.newInstance(arrayList[position])
            dialog.show((context as AppCompatActivity).supportFragmentManager, "FullScreenImageDialogFragment")
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.list_item_image) // Sử dụng ID từ tài nguyên của bạn
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onClick(imageView: ImageView, path: String)
    }
}
