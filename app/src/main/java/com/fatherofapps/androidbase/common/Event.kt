package com.fatherofapps.androidbase.common

import androidx.lifecycle.Observer

/**
 * A wrapper for data that is exposed via a LiveData representing an event.
 *
 * This is useful for situations where you want to handle an event only once, such as navigation
 * or displaying a toast message. The `hasBeenHandled` property ensures that the content is
 * consumed only once.
 *
 * @param T The type of content wrapped by the event.
 * @property content The content of the event.
 */
open class Event<out T>(private val content: T) {

    /**
     * Indicates whether the event has been handled.
     * This is set to `true` once the content has been accessed.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set // Allows external read but prevents external modification

    /**
     * Returns the content if it has not been handled, otherwise returns `null`.
     * Once the content is accessed, it is marked as handled to prevent further use.
     *
     * @return The content if it has not been handled, or `null` otherwise.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content regardless of whether it has been handled or not.
     *
     * @return The content of the event.
     */
    fun peekContent(): T = content
}

/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content
 * has already been handled.
 *
 * [onEventUnhandledContent] is called only if the [Event]'s content has not been handled yet.
 *
 * @param T The type of content wrapped by the event.
 * @param onEventUnhandledContent A lambda function to handle the unhandled content of the event.
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {

    /**
     * Called when the observed [Event] changes.
     * If the content of the [Event] has not been handled, the [onEventUnhandledContent] lambda
     * is invoked with the content.
     *
     * @param value The [Event] containing the content to be handled.
     */
    override fun onChanged(value: Event<T>) {
        value.getContentIfNotHandled()?.let { content ->
            onEventUnhandledContent(content)
        }
    }
}
