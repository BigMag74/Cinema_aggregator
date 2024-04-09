package com.example.cinemaaggregator.searchScreen.di

import com.example.cinemaaggregator.common.DI
import com.example.cinemaaggregator.common.di.ScreenScope
import com.example.cinemaaggregator.common.di.ViewModelFactory
import com.example.cinemaaggregator.common.network.NetworkClient
import dagger.BindsInstance
import dagger.Component

@Component(modules = [SearchScreenModule::class])
@ScreenScope
interface SearchScreenComponent {

    fun viewModelFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun kinopoiskApiService(
            networkClient: NetworkClient
        ): Builder

        fun build(): SearchScreenComponent
    }

    companion object {

        fun create() = with(DI.appComponent) {
            DaggerSearchScreenComponent.builder().kinopoiskApiService(networkClient()).build()
        }
    }
}