package com.fatherofapps.androidbase.di

import android.content.Context
import android.content.SharedPreferences
import com.fatherofapps.androidbase.common.AppSharePreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(appSharePreference: AppSharePreference): SharedPreferences {
        return appSharePreference.getSharedPreferences()
    }

    @Provides
    @Singleton
    fun provideAppSharePreference(context: Context): AppSharePreference {
        return AppSharePreference(context)
    }
}
