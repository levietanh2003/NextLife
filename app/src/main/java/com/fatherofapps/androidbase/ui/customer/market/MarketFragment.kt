package com.fatherofapps.androidbase.ui.customer.market

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.fatherofapps.androidbase.adapter.Article
import com.fatherofapps.androidbase.adapter.ArticleAdapter
import com.fatherofapps.androidbase.base.fragment.BaseFragment
import com.fatherofapps.androidbase.base.network.NetworkResult
import com.fatherofapps.androidbase.common.hideBottomNavigation
import com.fatherofapps.androidbase.databinding.FragmentMarketBinding
import com.fatherofapps.androidbase.ui.customer.login.LoginViewModel
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentMarketBinding.inflate(inflater, container, false)

        val viewPager: ViewPager2 = dataBinding.viewPager
        val tabLayout: TabLayout = dataBinding.tabLayout
        // Danh sách bài viết mẫu
//        val articles = listOf(
//            Article(
//                "https://firebasestorage.googleapis.com/v0/b/datpt-ce669.appspot.com/o/carousel%2F9a94da50-8d25-4476-8c4d-3af6a82bad43_noitang-210915-1442804718143.jpg?alt=media",
//                "Bài viết 1",
//                "Đây là nội dung tóm tắt bài viết 1."
//            ),
//            Article(
//                "https://firebasestorage.googleapis.com/v0/b/datpt-ce669.appspot.com/o/carousel%2Fb2fb4b40-1f13-419c-aa71-3558cc827de1_photd-tu1-1673254645362582917776.jpg?alt=media",
//                "Bài viết 2",
//                "Đây là nội dung tóm tắt bài viết 2."
//            ),
//            Article(
//                "https://firebasestorage.googleapis.com/v0/b/datpt-ce669.appspot.com/o/carousel%2Fd3e01b4a-13ac-4036-a057-b6f08d99ffdd_images.jpg?alt=media",
//                "Bài viết 3",
//                "Đây là nội dung tóm tắt bài viết 3."
//            )
//        )

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
            }
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
}
