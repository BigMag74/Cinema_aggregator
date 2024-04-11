package com.example.cinemaaggregator.movieScreen.di

import com.example.cinemaaggregator.common.DI
import com.example.cinemaaggregator.common.di.ScreenScope
import com.example.cinemaaggregator.common.di.ViewModelFactory
import com.example.cinemaaggregator.common.network.NetworkClient
import dagger.BindsInstance
import dagger.Component

@Component(modules = [MovieScreenModule::class])
@ScreenScope
interface MovieScreenComponent {

    fun viewModelFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun kinopoiskApiService(
            networkClient: NetworkClient,
        ): Builder

        fun build(): MovieScreenComponent
    }

    companion object{
        fun create() = with(DI.appComponent){
            DaggerMovieScreenComponent
                .builder()
                .kinopoiskApiService(networkClient())
                .build()
        }
    }
}