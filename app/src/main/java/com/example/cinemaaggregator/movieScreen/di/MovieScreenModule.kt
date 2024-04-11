package com.example.cinemaaggregator.movieScreen.di

import androidx.lifecycle.ViewModel
import com.example.cinemaaggregator.common.di.ScreenScope
import com.example.cinemaaggregator.common.di.ViewModelKey
import com.example.cinemaaggregator.movieScreen.data.MovieScreenRepositoryImpl
import com.example.cinemaaggregator.movieScreen.domain.MovieScreenRepository
import com.example.cinemaaggregator.movieScreen.domain.useCases.GetMovieByIdUseCase
import com.example.cinemaaggregator.movieScreen.domain.useCases.impl.GetMovieByIdUseCaseImpl
import com.example.cinemaaggregator.movieScreen.presentation.viewModel.MovieScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MovieScreenModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieScreenViewModel::class)
    fun provideMovieScreenViewModel(impl: MovieScreenViewModel): ViewModel

    @Binds
    @ScreenScope
    fun provideMovieScreenRepository(impl: MovieScreenRepositoryImpl): MovieScreenRepository

    @Binds
    @ScreenScope
    fun provideGetMovieByIdUseCase(idUseCase: GetMovieByIdUseCaseImpl): GetMovieByIdUseCase
}