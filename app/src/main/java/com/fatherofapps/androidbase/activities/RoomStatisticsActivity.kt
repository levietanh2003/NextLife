package com.fatherofapps.androidbase.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.base.activities.BaseActivity
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.ui.home.HomeViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "RoomStatisticsActivity"

@AndroidEntryPoint
class RoomStatisticsActivity : BaseActivity() {
    private lateinit var barChart: BarChart
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Activity starting")
        try {
            setContentView(R.layout.activity_room_statistics)
            Log.d(TAG, "onCreate: Layout set successfully")

            barChart = findViewById(R.id.barChart)
            Log.d(TAG, "onCreate: BarChart view found")

            setupBarChart()
            Log.d(TAG, "onCreate: BarChart setup completed")

            setupObservers()
            Log.d(TAG, "onCreate: Observers setup completed")

            // Fetch data when activity starts
            Log.d(TAG, "onCreate: Initiating data fetch")
            viewModel.fetchFeaturedPosts()

        } catch (e: Exception) {
            Log.e(TAG, "onCreate: Error initializing activity", e)
        }
    }

    private fun setupObservers() {
        Log.d(TAG, "setupObservers: Setting up data observers")

        viewModel.postFeatured.observe(this, Observer { posts ->
            Log.d(TAG, "promotionalPost observer: Received ${posts.size} posts")
            processDataAndUpdateChart(posts)
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            Log.d(TAG, "isLoading observer: Loading state = $isLoading")
        })
    }

    private fun setupBarChart() {
        Log.d(TAG, "setupBarChart: Configuring chart properties")
        try {
            barChart.apply {
                description.isEnabled = false
                setDrawGridBackground(false)

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    granularity = 1f
                    setDrawGridLines(false)
                }

                axisLeft.apply {
                    axisMinimum = 0f
                    setDrawGridLines(true)
                    setDrawAxisLine(true)
                }
                axisRight.isEnabled = false

                setPinchZoom(true)
                setScaleEnabled(true)

                legend.apply {
                    isEnabled = true
                    form = com.github.mikephil.charting.components.Legend.LegendForm.SQUARE
                }

                animateY(1000)
            }
            Log.d(TAG, "setupBarChart: Chart configuration completed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "setupBarChart: Error setting up chart", e)
        }
    }

    private fun processDataAndUpdateChart(posts: List<PromotionalPost>) {
        Log.d(TAG, "processDataAndUpdateChart: Starting data processing for ${posts.size} posts")
        try {
            val districtPrices = mutableMapOf<String, MutableList<Float>>()

            // Process data
            posts.forEach { post ->
                try {
                    val district = post.roomInfo.address
                    val basePrice = post.pricingDetails.basePrice.toFloat()
                    districtPrices.getOrPut(district) { mutableListOf() }.add(basePrice)
                    Log.d(TAG, "Processing post: District=$district, Price=$basePrice")
                } catch (e: Exception) {
                    Log.e(TAG, "Error processing individual post", e)
                }
            }

            Log.d(TAG, "Processed data for ${districtPrices.size} districts")

            // Calculate averages and create chart entries
            val entries = ArrayList<BarEntry>()
            val districts = ArrayList<String>()

            districtPrices.entries.forEachIndexed { index, entry ->
                val averagePrice = entry.value.average().toFloat()
                entries.add(BarEntry(index.toFloat(), averagePrice))
                districts.add(entry.key)
                Log.d(TAG, "District: ${entry.key}, Average Price: $averagePrice")
            }

            // Create dataset
            val dataSet = BarDataSet(entries, "Giá phòng trung bình theo quận (đồng '000)")
            dataSet.apply {
                colors = listOf(
                    Color.rgb(64, 89, 128),
                    Color.rgb(149, 165, 124),
                    Color.rgb(217, 184, 162),
                    Color.rgb(191, 134, 134),
                    Color.rgb(179, 48, 80)
                )
                valueTextSize = 12f
            }

            val barData = BarData(dataSet)
            barChart.data = barData
            barChart.xAxis.valueFormatter = IndexAxisValueFormatter(districts)
            barChart.xAxis.labelRotationAngle = 45f

            Log.d(TAG, "Chart data set and formatted successfully")

            barChart.invalidate()
            Log.d(TAG, "Chart refreshed with animation")

        } catch (e: Exception) {
            Log.e(TAG, "processDataAndUpdateChart: Error updating chart", e)
        }
    }
}