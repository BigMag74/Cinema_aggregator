package com.example.cinemaaggregator.search.di

import androidx.lifecycle.ViewModel
import com.example.cinemaaggregator.common.di.ScreenScope
import com.example.cinemaaggregator.common.di.ViewModelKey
import com.example.cinemaaggregator.search.data.SearchScreenRepositoryImpl
import com.example.cinemaaggregator.search.domain.SearchScreenRepository
import com.example.cinemaaggregator.search.domain.useCases.SearchMoviesUseCase
import com.example.cinemaaggregator.search.domain.useCases.impl.SearchMoviesUseCaseImpl
import com.example.cinemaaggregator.search.presentation.viewModel.SearchViewModel
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
}