package com.fatherofapps.androidbase.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.base.activities.BaseActivity
import com.mancj.materialsearchbar.MaterialSearchBar

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private var loadingLayout: FrameLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("Frank","MainActivity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingLayout = findViewById(R.id.loadingLayout)

        showLoading(false)

        // Thiết lập listener cho MaterialSearchBar
        val searchBar = findViewById<MaterialSearchBar>(R.id.searchBar)
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {}

            override fun onSearchConfirmed(text: CharSequence?) {
                // Khi người dùng nhấn Enter hoặc nút tìm kiếm
                val searchQuery = text.toString()

                // Tạo bundle để truyền dữ liệu
                val bundle = Bundle().apply {
                    putString("search_query", searchQuery)
                }
                Log.d("TitleSearch",searchQuery)

                // Điều hướng đến SearchFragment
                findNavController(R.id.container).navigate(R.id.searchFragment, bundle)
            }

            override fun onButtonClicked(buttonCode: Int) {}
        })

    }

    override fun showLoading(isShow: Boolean) {
        loadingLayout?.visibility = if (isShow) View.VISIBLE else View.GONE
    }
}
