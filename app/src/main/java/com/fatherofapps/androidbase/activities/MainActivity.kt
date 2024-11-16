package com.fatherofapps.androidbase.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.base.activities.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mancj.materialsearchbar.MaterialSearchBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private var loadingLayout: FrameLayout? = null
    private var bottomNavigationView: BottomNavigationView? = null
    private var fabPostNews: FloatingActionButton? = null
    private lateinit var navController: NavController
    private var isNavigationVisible = true
    private var isAnimating = false

    // Coroutine scope for handling animations
    private val animationScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            Log.d("MainActivity", "onCreate started")

            initViews()
            setupNavigation()
            setupSearchBar()
            setupChatButton()
            setupNavigationVisibility()

            Log.d("MainActivity", "onCreate completed")
        } catch (e: Exception) {
            Log.e("MainActivity", "Error in onCreate: ${e.message}", e)
            showError(e.message ?: "An error occurred")
        }
    }

    private fun initViews() {
        loadingLayout = findViewById(R.id.loadingLayout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fabPostNews = findViewById(R.id.fabPostNews)
        navController = findNavController(R.id.container)
    }


    private fun setupNavigation() {
        try {
            bottomNavigationView?.let { bottomNav ->
                bottomNav.setupWithNavController(navController)

                // Set up custom navigation listener
                bottomNav.setOnItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.homeFragment -> {
                            navController.navigate(R.id.homeFragment)
                            true
                        }
                        R.id.marketFragment -> {
                            navController.navigate(R.id.marketFragment)
                            true
                        }
                        R.id.newsFragment -> {
                            navController.navigate(R.id.newsFragment)
                            true
                        }
                        R.id.myAccountFragment -> {
                            navController.navigate(R.id.myAccountFragment)
                            true
                        }
                        else -> false
                    }
                }
            }

            fabPostNews?.setOnClickListener {
                // Add your post news logic here
                // For example: navController.navigate(R.id.createPostFragment)
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error in setupNavigation: ${e.message}", e)
        }
    }

    private fun setupNavigationVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            handleDestinationChange(destination)
        }
    }

    private fun handleDestinationChange(destination: NavDestination) {
        when (destination.id) {
            R.id.productDetailsFragment,
            R.id.myAccountFragment -> hideNavigationWithAnimation()
            else -> showNavigationWithAnimation()
        }
    }

    private fun hideNavigationWithAnimation() {
        if (!isNavigationVisible || isAnimating) return

        isAnimating = true
        isNavigationVisible = false

        animationScope.launch {
            try {
                bottomNavigationView?.let { bottomNav ->
                    bottomNav.animate()
                        .translationY(bottomNav.height.toFloat())
                        .setDuration(200)
                        .withEndAction {
                            bottomNav.isVisible = false
                            isAnimating = false
                        }
                        .start()
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error in hideNavigation: ${e.message}", e)
                isAnimating = false
            }
        }
    }

    private fun showNavigationWithAnimation() {
        if (isNavigationVisible || isAnimating) return

        isAnimating = true
        isNavigationVisible = true

        animationScope.launch {
            try {
                bottomNavigationView?.let { bottomNav ->
                    bottomNav.isVisible = true
                    bottomNav.animate()
                        .translationY(0f)
                        .setDuration(200)
                        .withEndAction {
                            isAnimating = false
                        }
                        .start()

                    fabPostNews?.let { fab ->
                        fab.isVisible = true
                        fab.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(200)
                            .start()
                    }
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error in showNavigation: ${e.message}", e)
                isAnimating = false
            }
        }
    }

    fun toggleNavigationVisibility(show: Boolean) {
        if (show) showNavigationWithAnimation() else hideNavigationWithAnimation()
    }

    private fun setupSearchBar() {
        try {
            val searchBar = findViewById<MaterialSearchBar>(R.id.searchBar)
            searchBar?.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
                override fun onSearchStateChanged(enabled: Boolean) {}

                override fun onSearchConfirmed(text: CharSequence?) {
                    text?.let { searchQuery ->
                        val bundle = Bundle().apply {
                            putString("search_query", searchQuery.toString())
                        }
                        navController?.navigate(R.id.searchFragment, bundle)
                    }
                }

                override fun onButtonClicked(buttonCode: Int) {}
            })
        } catch (e: Exception) {
            Log.e("MainActivity", "Error in setupSearchBar: ${e.message}", e)
        }
    }

    private fun setupChatButton() {
        try {
            findViewById<ImageView>(R.id.btn_chat)?.setOnClickListener {
                hideNavigationWithAnimation()
                startActivity(Intent(this, ChatActivity::class.java))
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error in setupChatButton: ${e.message}", e)
        }
    }


    override fun showLoading(isShow: Boolean) {
        loadingLayout?.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        Log.e("MainActivity", "Error: $message")
        // Add your error handling UI logic here
    }

    override fun onDestroy() {
        super.onDestroy()
        animationScope.cancel()
    }
}