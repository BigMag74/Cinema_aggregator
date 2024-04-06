package com.example.cinemaaggregator.search.di

import androidx.lifecycle.ViewModel
import com.example.cinemaaggregator.common.di.ViewModelKey
import com.example.cinemaaggregator.search.data.SearchScreenRepositoryImpl
import com.example.cinemaaggregator.search.domain.SearchScreenRepository
import com.example.cinemaaggregator.search.domain.useCases.SearchFilmsUseCase
import com.example.cinemaaggregator.search.domain.useCases.impl.SearchFilmsUseCaseImpl
import com.example.cinemaaggregator.search.presentation.viewModel.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SearchModule {

    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    @Binds
    fun provideViewModel(impl: SearchViewModel): ViewModel

    @Binds
    fun provideSearchScreenRepository(impl: SearchScreenRepositoryImpl): SearchScreenRepository

    @Binds
    fun provideSearchFilmsUseCase(impl: SearchFilmsUseCaseImpl): SearchFilmsUseCase
}