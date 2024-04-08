package com.example.cinemaaggregator.common.di

import com.example.cinemaaggregator.common.network.NetworkClient
import com.example.cinemaaggregator.common.network.NetworkClientImpl
import dagger.Binds
import dagger.Module

@Module
interface NetworkClientModule {

    @Binds
    fun provideNetworkClient(impl: NetworkClientImpl): NetworkClient
}