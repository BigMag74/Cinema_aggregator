package com.example.cinemaaggregator.searchScreen.di

import androidx.lifecycle.ViewModel
import com.example.cinemaaggregator.common.di.ScreenScope
import com.example.cinemaaggregator.common.di.ViewModelKey
import com.example.cinemaaggregator.searchScreen.data.SearchScreenRepositoryImpl
import com.example.cinemaaggregator.searchScreen.domain.SearchScreenRepository
import com.example.cinemaaggregator.searchScreen.domain.useCases.GetCountriesUseCase
import com.example.cinemaaggregator.searchScreen.domain.useCases.GetGenresUseCase
import com.example.cinemaaggregator.searchScreen.domain.useCases.SearchMoviesUseCase
import com.example.cinemaaggregator.searchScreen.domain.useCases.impl.GetCountriesUseCaseImpl
import com.example.cinemaaggregator.searchScreen.domain.useCases.impl.GetGenresUseCaseImpl
import com.example.cinemaaggregator.searchScreen.domain.useCases.impl.SearchMoviesUseCaseImpl
import com.example.cinemaaggregator.searchScreen.presentation.viewModel.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SearchScreenModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun provideViewModel(impl: SearchViewModel): ViewModel

    @Binds
    @ScreenScope
    fun provideSearchScreenRepository(impl: SearchScreenRepositoryImpl): SearchScreenRepository

    @Binds
    @ScreenScope
    fun provideSearchMoviesUseCase(impl: SearchMoviesUseCaseImpl): SearchMoviesUseCase

    @Binds
    @ScreenScope
    fun provideGetCountiesUseCase(impl: GetCountriesUseCaseImpl): GetCountriesUseCase

    @Binds
    @ScreenScope
    fun provideGetGenresUseCase(impl: GetGenresUseCaseImpl): GetGenresUseCase
}