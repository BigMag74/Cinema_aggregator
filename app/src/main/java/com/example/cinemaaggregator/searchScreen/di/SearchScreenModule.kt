package com.example.cinemaaggregator.searchScreen.di

import androidx.lifecycle.ViewModel
import com.example.cinemaaggregator.common.di.ScreenScope
import com.example.cinemaaggregator.common.di.ViewModelKey
import com.example.cinemaaggregator.searchScreen.data.SearchScreenRepositoryImpl
import com.example.cinemaaggregator.searchScreen.data.dataSource.SearchHistoryDataSource
import com.example.cinemaaggregator.searchScreen.data.dataSource.SearchHistoryDataSourceImpl
import com.example.cinemaaggregator.searchScreen.domain.SearchScreenRepository
import com.example.cinemaaggregator.searchScreen.domain.useCases.AddFieldToSearchHistoryUseCase
import com.example.cinemaaggregator.searchScreen.domain.useCases.GetCountriesUseCase
import com.example.cinemaaggregator.searchScreen.domain.useCases.GetGenresUseCase
import com.example.cinemaaggregator.searchScreen.domain.useCases.GetSearchHistoryUseCase
import com.example.cinemaaggregator.searchScreen.domain.useCases.SearchMoviesByFiltersUseCase
import com.example.cinemaaggregator.searchScreen.domain.useCases.SearchMoviesByNameUseCase
import com.example.cinemaaggregator.searchScreen.domain.useCases.impl.AddFieldToSearchHistoryUseCaseImpl
import com.example.cinemaaggregator.searchScreen.domain.useCases.impl.GetCountriesUseCaseImpl
import com.example.cinemaaggregator.searchScreen.domain.useCases.impl.GetGenresUseCaseImpl
import com.example.cinemaaggregator.searchScreen.domain.useCases.impl.GetSearchHistoryUseCaseImpl
import com.example.cinemaaggregator.searchScreen.domain.useCases.impl.SearchMoviesByFiltersUseCaseImpl
import com.example.cinemaaggregator.searchScreen.domain.useCases.impl.SearchMoviesByNameUseCaseImpl
import com.example.cinemaaggregator.searchScreen.presentation.viewModel.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SearchScreenModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun provideSearchScreenViewModel(impl: SearchViewModel): ViewModel

    @Binds
    @ScreenScope
    fun provideSearchScreenRepository(impl: SearchScreenRepositoryImpl): SearchScreenRepository

    @Binds
    @ScreenScope
    fun provideSearchHistoryDataSource(impl: SearchHistoryDataSourceImpl): SearchHistoryDataSource

    @Binds
    @ScreenScope
    fun provideSearchMoviesByNameUseCase(impl: SearchMoviesByNameUseCaseImpl): SearchMoviesByNameUseCase

    @Binds
    @ScreenScope
    fun provideSearchMoviesByFiltersUseCase(impl: SearchMoviesByFiltersUseCaseImpl): SearchMoviesByFiltersUseCase

    @Binds
    @ScreenScope
    fun provideGetCountiesUseCase(impl: GetCountriesUseCaseImpl): GetCountriesUseCase

    @Binds
    @ScreenScope
    fun provideGetGenresUseCase(impl: GetGenresUseCaseImpl): GetGenresUseCase

    @Binds
    @ScreenScope
    fun provideGetSearchHistoryUseCase(impl: GetSearchHistoryUseCaseImpl): GetSearchHistoryUseCase

    @Binds
    @ScreenScope
    fun provideAddFieldToSearchHistoryUseCase(impl: AddFieldToSearchHistoryUseCaseImpl): AddFieldToSearchHistoryUseCase
}