package com.fatherofapps.androidbase.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.base.activities.BaseActivity
import com.fatherofapps.androidbase.data.models.PromotionalPost
import com.fatherofapps.androidbase.ui.home.HomeViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "RoomStatisticsActivity"

@AndroidEntryPoint
class RoomStatisticsActivity : BaseActivity() {
    private lateinit var barChart: BarChart
    private lateinit var pieChart: PieChart
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Activity starting")
        try {

            showLoading(true)
            setContentView(R.layout.activity_room_statistics)
            Log.d(TAG, "onCreate: Layout set successfully")

            barChart = findViewById(R.id.barChart)
            Log.d(TAG, "onCreate: BarChart view found")

            setupBarChart()
            Log.d(TAG, "onCreate: BarChart setup completed")


            Log.d(TAG, "onCreate: Observers setup completed")

            pieChart = findViewById(R.id.pieChartRoomTypes)
            setupPieChart()
            setupObservers()
            Log.d(TAG, "onCreate: PieChart view found")

            // Fetch data when activity starts
            Log.d(TAG, "onCreate: Initiating data fetch")
            viewModel.fetchAllProduct()


        } catch (e: Exception) {
            Log.e(TAG, "onCreate: Error initializing activity", e)
        }
    }

    private fun setupObservers() {
        Log.d(TAG, "setupObservers: Setting up data observers")

        viewModel.allPost.observe(this) { posts ->
            Log.d(TAG, "promotionalPost observer: Received ${posts.size} posts")
            processDataAndUpdateChart(posts)
            processDataUpdateChartPieStyle(posts)
            showLoading(false)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(true)
        }
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
    override fun showLoading(isLoading: Boolean) {
        val loadingLayout = findViewById<View>(R.id.loadingLayout)
        loadingLayout?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupPieChart() {
        Log.d(TAG, "setupPieChart: Configuring pie chart properties")
        try {

            pieChart.apply {
                description.isEnabled = false
                setUsePercentValues(true)
                setDrawEntryLabels(true)
                setEntryLabelColor(Color.BLACK)
                setEntryLabelTextSize(12f)

                legend.apply {
                    isEnabled = true
                    form = com.github.mikephil.charting.components.Legend.LegendForm.CIRCLE
                    horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                }

                animateY(1000)
            }
            Log.d(TAG, "setupPieChart: Pie chart configuration completed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "setupPieChart: Error setting up pie chart", e)
        }
    }

    // tỉ lệ style phòng trong hệ thống
    private fun processDataUpdateChartPieStyle(posts: List<PromotionalPost>) {
        Log.d(TAG, "processDataUpdateChartPieStyle: Starting data processing for room types")

        try {
            // Đếm số lượng từng loại phòng
            val roomTypeCounts = mutableMapOf<String, Int>()

            posts.forEach { post ->
                try {
                    val roomType = post.roomInfo.type // Giả sử có thuộc tính roomType
                    roomTypeCounts[roomType] = (roomTypeCounts[roomType] ?: 0) + 1
                    Log.d(TAG, "Processing room type: $roomType")
                } catch (e: Exception) {
                    Log.e(TAG, "Error processing individual post room type", e)
                }
            }

            // Tạo danh sách entries cho PieChart
            val entries = ArrayList<PieEntry>()
            val totalPosts = posts.size

            roomTypeCounts.forEach { (roomType, count) ->
                val percentage = (count.toFloat() / totalPosts) * 100
                entries.add(PieEntry(percentage, "$roomType (${count}/${totalPosts})"))
                Log.d(TAG, "Room Type: $roomType, Count: $count, Percentage: $percentage%")
            }

            // Tạo dataset
            val dataSet = PieDataSet(entries, "Phân loại phòng")
            dataSet.apply {
                colors = ColorTemplate.MATERIAL_COLORS.toList()
                valueTextColor = Color.BLACK
                valueTextSize = 12f
            }

            val pieData = PieData(dataSet)
            pieChart.data = pieData

            Log.d(TAG, "Pie chart data set and formatted successfully")

            pieChart.invalidate()
            Log.d(TAG, "Pie chart refreshed with animation")

        } catch (e: Exception) {
            Log.e(TAG, "processDataUpdateChartPieStyle: Error updating pie chart", e)
        }
    }


    // avg price in address
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