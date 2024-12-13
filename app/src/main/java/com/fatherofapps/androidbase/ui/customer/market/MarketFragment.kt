package com.fatherofapps.androidbase.ui.customer.market

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.fatherofapps.androidbase.adapter.Article
import com.fatherofapps.androidbase.adapter.ArticleAdapter
import com.fatherofapps.androidbase.adapter.ExperienceUserAdapter
import com.fatherofapps.androidbase.adapter.ProductAdapter
import com.fatherofapps.androidbase.base.fragment.BaseFragment
import com.fatherofapps.androidbase.data.models.NewsData
import com.fatherofapps.androidbase.databinding.FragmentMarketBinding
import com.fatherofapps.androidbase.ui.customer.login.LoginViewModel
import com.fatherofapps.androidbase.ui.customer.news.NewsDetailActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketFragment : BaseFragment() {

    private lateinit var dataBinding: FragmentMarketBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchAllNews()
        viewModel.fetchAllExperienceUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentMarketBinding.inflate(inflater, container, false)

        val viewPager: ViewPager2 = dataBinding.viewPager
        val tabLayout: TabLayout = dataBinding.tabLayout

        viewModel.listNews.observe(viewLifecycleOwner) { newsList ->

            if (newsList.isNotEmpty()) {
                val articles1 = newsList.map { news ->
                    Article(
                        imageUrl = news.postImages.firstOrNull()?.urlImagePost ?: "",
                        title = news.title,
                        content = news.description
                    )
                }

                val adapter = ArticleAdapter(articles1)
                viewPager.adapter = adapter
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = articles[position].title

                }.attach()

                handleLoadMore(articles1, viewPager)
            } else {
                Log.d("MarketFragment", "Danh sách rỗng")
                showNotify("Không có tin tức","Thông báo")
            }
        }

        viewModel.listExperienceUser.observe(viewLifecycleOwner){ experienceList ->
            Log.d("ExperienceList", "Received filtered posts. Count: ${experienceList.size}")
            handleFilteredPosts(experienceList)

        }
        return dataBinding.root
    }

    fun handleLoadMore(articles: List<Article>, viewPager: ViewPager2) {
        // Tạo hiệu ứng tự động chuyển slide sau mỗi 3 giây
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            var currentItem = 0
            override fun run() {
                if (currentItem == articles.size) {
                    currentItem = 0
                }
                viewPager.setCurrentItem(currentItem++, true)
                handler.postDelayed(this, 3000)  // 3000ms = 3s
            }
        }
        handler.postDelayed(runnable, 3000)
    }

    private fun handleFilteredPosts(posts: List<NewsData>) {
        Log.d("Address_SearchFragment", "Handling filtered posts. Count: ${posts.size}")

        if (posts.isEmpty()) {
            // Hiển thị hình ảnh thông báo khi không có kết quả
            showNotify("Không có sản tin tức","Thông báo")
        } else {
            // Ẩn hình ảnh thông báo khi có kết quả
//            noResultsImage.visibility = View.GONE
            dataBinding.recyclerViewShares.visibility = View.VISIBLE

            val productAdapter = ExperienceUserAdapter(
                posts.map { it.title },
                posts.map { it.postImages.getOrNull(0)?.urlImagePost ?: "" },
                requireContext(),
                object : ProductAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        val newId = posts[position].id
                        val bundle = Bundle().apply {
                            putString("newId", newId)  // Đưa newId vào Bundle
                        }
                        val intent = Intent(requireContext(), NewsDetailActivity::class.java).apply {
                            putExtras(bundle)  // Gửi Bundle qua Intent
                        }
                        startActivity(intent)
                        Log.d("NewID", "New ID: $newId")
                    }
                }
            )

            dataBinding.recyclerViewShares.apply {
                layoutManager = GridLayoutManager(requireContext(), 1)
                adapter = productAdapter
            }
        }
    }
}
