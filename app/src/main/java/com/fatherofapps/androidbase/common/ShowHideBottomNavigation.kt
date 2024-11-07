package com.fatherofapps.androidbase.common

import android.view.View
import androidx.fragment.app.Fragment
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.activities.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

fun Fragment.hideBottomNavigation() {
    val bottomNavigationView = (activity as MainActivity).findViewById<BottomNavigationView>(
        R.id.bottomNavigationView
    )
    bottomNavigationView.visibility = View.GONE

    val fabPostNews = (activity as MainActivity).findViewById<FloatingActionButton>(R.id.fabPostNews)
    fabPostNews.visibility = View.GONE
}

fun Fragment.showBottomNavigation(){
    val bottomNavigationView = (activity as MainActivity).findViewById<BottomNavigationView>(
        R.id.bottomNavigationView
    )
    bottomNavigationView.visibility = View.VISIBLE

    val fabPostNews = (activity as MainActivity).findViewById<FloatingActionButton>(R.id.fabPostNews)
    fabPostNews.visibility = View.VISIBLE
}