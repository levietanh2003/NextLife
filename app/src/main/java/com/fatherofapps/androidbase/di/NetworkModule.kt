package com.fatherofapps.androidbase.di

import android.util.Log
import com.fatherofapps.androidbase.BuildConfig
import com.fatherofapps.androidbase.data.apis.*

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * This module provides network-related dependencies such as Retrofit, OkHttpClient,
 * Moshi, and API services to be used throughout the application.
 *
 * It contains all the necessary configurations to handle network requests, including
 * the setup of base URLs, converters, logging, and interceptors.
 *
 * This module is installed in the SingletonComponent, meaning that the provided instances
 * will be shared across the entire app lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    /**
     * Provides the [PostPromotionalAPI] service to interact with the promotional post API.
     *
     * @param retrofit The Retrofit instance used for making network requests.
     * @return The [PostPromotionalAPI] instance.
     */
    @Provides
    fun providePostPromotionalAPI(@Named("MainSite") retrofit: Retrofit): PostPromotionalAPI {
        return retrofit.create(PostPromotionalAPI::class.java)
    }

    /**
     * Provides the [PromotionalPostDetailAPI] service to interact with the promotional post details API.
     *
     * @param retrofit The Retrofit instance used for making network requests.
     * @return The [PromotionalPostDetailAPI] instance.
     */
    @Provides
    fun providePromotionalPostDetailAPI(@Named("MainSite") retrofit: Retrofit): PromotionalPostDetailAPI {
        return retrofit.create(PromotionalPostDetailAPI::class.java)
    }

    /**
     * Provides the [PostFeaturedAPI] service to interact with the featured posts API.
     *
     * @param retrofit The Retrofit instance used for making network requests.
     * @return The [PostFeaturedAPI] instance.
     */
    @Provides
    fun providePostFeaturedAPI(@Named("MainSite") retrofit: Retrofit): PostFeaturedAPI {
        return retrofit.create(PostFeaturedAPI::class.java)
    }

    /**
     * Provides the [PostFilterAPI] service to interact with the post filter API.
     *
     * @param retrofit The Retrofit instance used for making network requests.
     * @return The [PostFilterAPI] instance.
     */
    @Provides
    fun providePostFilterAPI(@Named("MainSite") retrofit: Retrofit): PostFilterAPI {
        return retrofit.create(PostFilterAPI::class.java)
    }

    /**
     * Provides the [PostSearchAPI] service to interact with the post search API.
     *
     * @param retrofit The Retrofit instance used for making network requests.
     * @return The [PostSearchAPI] instance.
     */
    @Provides
    fun providePostSearchAPI(@Named("MainSite") retrofit: Retrofit): PostSearchAPI {
        return retrofit.create(PostSearchAPI::class.java)
    }

    @Provides
    fun providePostCategoryAPI(@Named("MainSite") retrofit: Retrofit): NewsAPI {
        return retrofit.create(NewsAPI::class.java)
    }

    /**
     * Provides a [Retrofit] instance configured with the base URL for the main site.
     *
     * @param okHttpClient The OkHttpClient instance used for making network requests.
     * @param moshiConverterFactory The Moshi converter used to convert JSON responses.
     * @return The [Retrofit] instance configured with the base URL and client.
     */
    @Provides
    @Singleton
    @Named("MainSite")
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder().addConverterFactory(moshiConverterFactory)
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    /**
     * Provides a [Retrofit] instance configured with the base URL for user-related API requests.
     *
     * @param okHttpClient The OkHttpClient instance used for making network requests.
     * @param moshiConverterFactory The Moshi converter used to convert JSON responses.
     * @return The [Retrofit] instance configured with the user-related API base URL.
     */
    @Provides
    @Singleton
    @Named("UserAPI")
    fun provideUserAPIRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .baseUrl(BuildConfig.URL_USER)
            .client(okHttpClient)
            .build()
    }

    /**
     * Provides the [UserAPI] service to interact with the user-related API.
     *
     * @param retrofit The Retrofit instance used for making network requests.
     * @return The [UserAPI] instance.
     */
    @Provides
    fun provideUserAPI(@Named("UserAPI") retrofit: Retrofit): UserAPI {
        return retrofit.create(UserAPI::class.java)
    }

    /**
     * Provides a [Retrofit] instance configured with the base URL for payment-related API requests.
     *
     * @param okHttpClient The OkHttpClient instance used for making network requests.
     * @param moshiConverterFactory The Moshi converter used to convert JSON responses.
     * @return The [Retrofit] instance configured with the user-related API base URL.
     */
    @Provides
    @Singleton
    @Named("PaymentAPI")
    fun providePaymentAPIRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .baseUrl(BuildConfig.PAYMENT)
            .client(okHttpClient)
            .build()
    }

    /**
     * Provides the [PaymentAPI] service to interact with the payment-related API.
     *
     * @param retrofit The Retrofit instance used for making network requests.
     * @return The [PaymentAPI] instance.
     */
    @Provides
    fun providePaymentAPI(@Named("PaymentAPI") retrofit: Retrofit): PaymentAPI {
        return retrofit.create(PaymentAPI::class.java)
    }

    /**
     * Provides the [HttpLoggingInterceptor] to log network requests and responses for debugging.
     *
     * @return The [HttpLoggingInterceptor] instance.
     */
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    /**
     * Provides the [MoshiConverterFactory] to convert JSON data into Kotlin objects using Moshi.
     *
     * @return The [MoshiConverterFactory] instance configured with Moshi.
     */
    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): MoshiConverterFactory {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return MoshiConverterFactory.create(moshi)
    }

    /**
     * Provides the [OkHttpClient] with interceptors for logging and adding the Authorization token to requests.
     *
     * @param httpLoggingInterceptor The logging interceptor used to log network requests.
     * @param appSharePreference The shared preferences instance used to retrieve the stored token.
     * @return The [OkHttpClient] instance with added interceptors.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        appSharePreference: AppSharePreference // Inject AppSharePreference to retrieve token
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()

        // Add an interceptor to add the Authorization token to every request
        builder.addInterceptor { chain ->
            val token = appSharePreference.getToken() // Get the token from SharedPreferences
            val request = chain.request().newBuilder()
                .apply {
                    if (!token.isNullOrEmpty()) {
                        addHeader("Authorization", "Bearer $token")
                        Log.d("AuthToken", "Token is: $token")// Add token to the header
                    }
                }
                .build()
            chain.proceed(request)
        }

        // Add the HTTP logging interceptor
        builder.addInterceptor(httpLoggingInterceptor)

        return builder.build()
    }

}
