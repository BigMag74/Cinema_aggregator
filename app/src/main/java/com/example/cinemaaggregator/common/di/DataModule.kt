package com.example.cinemaaggregator.common.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    companion object {

        const val SHARED_PREFERENCES = "shared_preferences"
    }
}