package com.fatherofapps.androidbase

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)

    // Create the observer for LiveData
    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            // Store the value from LiveData
            data = value
            // Count down the latch to indicate a change has occurred
            latch.countDown()
            // Remove the observer to avoid memory leaks
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    // Observe the LiveData forever (until we remove the observer)
    this.observeForever(observer)

    try {
        // Invoke the callback after observing
        afterObserve.invoke()

        // Wait for the LiveData to emit a value or timeout
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }
    } finally {
        // Ensure that the observer is removed
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T // Return the LiveData's value
}
