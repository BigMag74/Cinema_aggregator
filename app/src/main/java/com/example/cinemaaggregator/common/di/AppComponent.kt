package com.example.cinemaaggregator.common.di

import android.content.Context
import com.example.cinemaaggregator.common.network.NetworkClient
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, NetworkClientModule::class])
@Singleton
interface AppComponent {

    fun networkClient(): NetworkClient

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): AppComponent
    }
}