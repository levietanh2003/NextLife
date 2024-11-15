package com.fatherofapps.androidbase.common

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)  // Cung cấp trong phạm vi ứng dụng
object AppModule {

    @Provides
    fun provideContext(application: android.app.Application): Context {
        return application.applicationContext
    }
}