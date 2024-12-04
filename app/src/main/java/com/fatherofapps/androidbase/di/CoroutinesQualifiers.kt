package com.fatherofapps.androidbase.di

import javax.inject.Qualifier

/**
 * Qualifier annotation for providing the default coroutine dispatcher.
 * This dispatcher is optimized for performing CPU-intensive tasks.
 *
 * @see kotlinx.coroutines.Dispatchers.Default
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultDispatcher

/**
 * Qualifier annotation for providing the IO coroutine dispatcher.
 * This dispatcher is optimized for offloading blocking IO tasks such as
 * network or disk operations.
 *
 * @see kotlinx.coroutines.Dispatchers.IO
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IoDispatcher

/**
 * Qualifier annotation for providing the main coroutine dispatcher.
 * This dispatcher is confined to the main thread and is used for UI interactions.
 *
 * @see kotlinx.coroutines.Dispatchers.Main
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MainDispatcher

/**
 * Qualifier annotation for providing the main immediate coroutine dispatcher.
 * This dispatcher is confined to the main thread but executes tasks immediately
 * when already on the main thread, avoiding unnecessary dispatching.
 *
 * @see kotlinx.coroutines.Dispatchers.Main.immediate
 */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainImmediateDispatcher