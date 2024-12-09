package com.fatherofapps.androidbase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fatherofapps.androidbase.R

class ArticleAdapter(private val articles: List<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgArticle: ImageView = view.findViewById(R.id.imgArticle)
        val txtTitle: TextView = view.findViewById(R.id.txtTitle)
        val txtContent: TextView = view.findViewById(R.id.txtContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_market_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        Glide.with(holder.itemView.context).load(article.imageUrl).into(holder.imgArticle)
        holder.txtTitle.text = article.title
        holder.txtContent.text = article.content
    }

    override fun getItemCount(): Int = articles.size
}
