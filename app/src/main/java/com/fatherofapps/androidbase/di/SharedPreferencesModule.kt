package com.fatherofapps.androidbase.di

import android.content.Context
import android.content.SharedPreferences
import com.fatherofapps.androidbase.common.AppSharePreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    fun provideSharedPreferences(appSharePreference: AppSharePreference): SharedPreferences {
        return appSharePreference.getSharedPreferences()
    }

    @Provides
    fun provideAppSharePreference(context: Context): AppSharePreference {
        return AppSharePreference(context)
    }
}
