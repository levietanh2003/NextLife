package com.fatherofapps.androidbase.di

import android.content.Context
import android.util.Log
import com.fatherofapps.androidbase.BuildConfig
import com.fatherofapps.androidbase.data.apis.*
import com.fatherofapps.androidbase.data.database.websocket.ChatWebSocketListener

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

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    // provides PostPromotionalAPI
    @Provides
    fun providePostPromotionalAPI(@Named("MainSite") retrofit: Retrofit): PostPromotionalAPI {
        return retrofit.create(PostPromotionalAPI::class.java)
    }

    @Provides
    fun providePromotionalPostDetailAPI(@Named("MainSite") retrofit: Retrofit): PromotionalPostDetailAPI {
        return retrofit.create(PromotionalPostDetailAPI::class.java)
    }

    @Provides
    fun providePostFeaturedAPI(@Named("MainSite") retrofit: Retrofit): PostFeaturedAPI {
        return retrofit.create(PostFeaturedAPI::class.java)
    }

    @Provides
    fun providePostFilterAPI(@Named("MainSite") retrofit: Retrofit): PostFilterAPI {
        return retrofit.create(PostFilterAPI::class.java)
    }

    @Provides
    fun providePostSearchAPI(@Named("MainSite") retrofit: Retrofit): PostSearchAPI {
        return retrofit.create(PostSearchAPI::class.java)
    }

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


    @Provides
    fun provideUserAPI(@Named("UserAPI") retrofit: Retrofit): UserAPI{
        return retrofit.create(UserAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): MoshiConverterFactory {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return MoshiConverterFactory.create(moshi)
    }

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