package com.fatherofapps.androidbase.di

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

//    @Provides
//    fun provideUserAPI(@Named("UserAPI") retrofit: Retrofit): RegisterAPI {
//        return retrofit.create(RegisterAPI::class.java)
//    }
//
//    @Provides
//    fun provideLoginAPI(@Named("UserAPI") retrofit: Retrofit):  LoginAPI{
//        return retrofit.create(LoginAPI::class.java)
//    }

    @Provides
    fun provideUserAPI(@Named("UserAPI") retrofit: Retrofit): UserAPI{
        return retrofit.create(UserAPI::class.java)
    }


//    @Provides
//    @Singleton
//    @Named("FatherOfApps")
//    fun provideRetrofitNewYorkTime(
//        okHttpClient: OkHttpClient,
//        moshiConverterFactory: MoshiConverterFactory
//    ): Retrofit {
//        return Retrofit.Builder().addConverterFactory(moshiConverterFactory)
//            .baseUrl(BuildConfig.BASE_URL)
//            .client(okHttpClient)
//            .build()
//    }

    @Provides
    @Singleton
    fun provideOKHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.interceptors().add(httpLoggingInterceptor)
        return builder.build()
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

}