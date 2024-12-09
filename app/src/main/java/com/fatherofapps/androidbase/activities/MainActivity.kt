package com.fatherofapps.androidbase.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.base.activities.BaseActivity
import com.fatherofapps.androidbase.ui.customer.myaccount.MyAccountActivity
import com.fatherofapps.androidbase.ui.customer.news.NewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mancj.materialsearchbar.MaterialSearchBar
import dagger.hilt.android.AndroidEntryPoint

@Suppress("UNREACHABLE_CODE")
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private var loadingLayout: FrameLayout? = null
    private var bottomNavigationView: BottomNavigationView? = null
    private var fabPostNews: FloatingActionButton? = null
    private var searchBar: MaterialSearchBar? = null
    private var btnBell: ImageView? = null
    private var btnChat: ImageView? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("Frank","MainActivity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingLayout = findViewById(R.id.loadingLayout)
        loadingLayout = findViewById(R.id.loadingLayout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fabPostNews = findViewById(R.id.fabPostNews)
        searchBar = findViewById(R.id.searchBar)
        btnBell = findViewById(R.id.btn_bell)
        btnChat = findViewById(R.id.btn_chat)

        // Setup Navigation Controller
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

        // Setup BottomNavigationView with NavController
        bottomNavigationView?.setupWithNavController(navController)

//        searchBar?.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
//            override fun onSearchStateChanged(enabled: Boolean) {}
//
//            override fun onButtonClicked(buttonCode: Int) {
//                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {
//                    // Handle navigation button click if needed
//                }
//            }
//
//            override fun onSearchConfirmed(text: CharSequence?) {
//                if (!text.isNullOrBlank()) {
//                    val bundle = Bundle().apply {
//                        putString("search_query", text.toString())
//                    }
//                    navController.navigate(R.id.searchFragment, bundle)
//                }
//            }
//        })
        searchBar?.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {}

            override fun onButtonClicked(buttonCode: Int) {}

            override fun onSearchConfirmed(text: CharSequence?) {
                if (!text.isNullOrBlank()) {
                    val bundle = Bundle().apply {
                        putString("search_query", text.toString())
                    }
                    navController.navigate(R.id.searchFragment, bundle)
                }
            }
        })

        // Hide BottomNavigationView in specific fragments
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.marketFragment -> {
                    bottomNavigationView?.visibility = View.GONE
                    fabPostNews?.visibility = View.GONE
                }
                else -> {
                    bottomNavigationView?.visibility = View.VISIBLE
                    fabPostNews?.visibility = View.VISIBLE
                }
            }
        }

        // Handle FAB Click
        fabPostNews?.setOnClickListener {
            val intentNewActivity = Intent(this, NewsFragment::class.java)
            startActivity(intentNewActivity)
        }

        // Optional: Custom navigation handling
        bottomNavigationView?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuHome -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.menuMarket -> {
                    navController.navigate(R.id.marketFragment)
                    true
                }
                R.id.menuNews -> {
                    // Navigate to news management fragment
                    // navController.navigate(R.id.newsManagementFragment)
                    true
                }
                R.id.menuMyAccount -> {
                    // Navigate to account fragment
                    // navController.navigate(R.id.accountFragment)
                    Log.e("Frank","OnClickListener success")
                    val intent = Intent(this, MyAccountActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        // Optional: Handle notification and chat button clicks
        btnBell?.setOnClickListener {
            // Navigate to notifications or show notifications
            // navController.navigate(R.id.notificationsFragment)
        }

        btnChat?.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }

    }

    override fun showLoading(isShow: Boolean) {
        loadingLayout?.visibility = if (isShow) View.VISIBLE else View.GONE
    }
}