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
import formatCurrencyFromString
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProductAdapter(
    private val nameProducts: List<String>,
    private val prices: List<String>,
    private val imageUrls: List<String>,
    private val locationProducts: List<String>,
    private val lastModifiedTimestamps: List<Double>,
    private val quantityImage: List<Int>,
    private val context: Context,
    private val itemClickListener: OnItemClickListener,
//    private val scrollListener: OnScrollListener?
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // ViewHolder class chứa các view từ layout
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val productLocation: TextView = itemView.findViewById(R.id.product_location)
        val productLastModified: TextView = itemView.findViewById(R.id.product_last_modified)
        val productQuantity: TextView = itemView.findViewById(R.id.quantityImage)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnScrollListener { // Define the scroll listener interface
        fun onScroll()
    }

    // Hàm này để tạo ra ViewHolder mới khi RecyclerView cần
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_rv_item, parent, false)
        return ProductViewHolder(view)
    }

    // Gán dữ liệu cho các view trong ViewHolder
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val nameProduct = nameProducts[position]
        val price = prices[position]
        val imageUrl = imageUrls[position]
        val locationProduct = locationProducts[position]
        val lastModifiedTimestamp = lastModifiedTimestamps[position]
        val quantityImage = quantityImage[position]
        val lastModifiedDate = Date((lastModifiedTimestamp * 1000).toLong())

        // Định dạng đối tượng Date thành chuỗi
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = format.format(lastModifiedDate)

        holder.productName.text = nameProduct
        holder.productPrice.text = formatCurrencyFromString(price)
        holder.productLocation.text = locationProduct
        holder.productLastModified.text = formattedDate
        holder.productQuantity.text = quantityImage.toString()

        // Sử dụng Glide để tải hình ảnh sản phẩm
        Glide.with(context)
            .load(imageUrl)
            .into(holder.productImage)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(position) // Gọi listener với position
        }

        // Call the scroll listener if the last item is reached
//        if (position == itemCount - 1) {
//            scrollListener?.onScroll()
//        }
    }

    // Trả về số lượng item trong danh sách sản phẩm
    override fun getItemCount(): Int {
        return nameProducts.size
    }
}
